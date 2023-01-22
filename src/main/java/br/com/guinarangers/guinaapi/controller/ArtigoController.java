package br.com.guinarangers.guinaapi.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.guinarangers.guinaapi.controller.dto.DefaultErrorDto;
import br.com.guinarangers.guinaapi.controller.dto.DefaultInfoDto;
import br.com.guinarangers.guinaapi.controller.dto.artigo.ArtigoDetalhesDto;
import br.com.guinarangers.guinaapi.controller.dto.artigo.ArtigoDto;
import br.com.guinarangers.guinaapi.controller.form.artigo.ArtigoForm;
import br.com.guinarangers.guinaapi.controller.form.artigo.AtualizarArtigoForm;
import br.com.guinarangers.guinaapi.enums.StorageFileTypeEnum;
import br.com.guinarangers.guinaapi.model.Artigo;
import br.com.guinarangers.guinaapi.model.Comentario;
import br.com.guinarangers.guinaapi.model.Tag;
import br.com.guinarangers.guinaapi.model.Usuario;
import br.com.guinarangers.guinaapi.repository.ArtigoRepository;
import br.com.guinarangers.guinaapi.repository.TagRepository;
import br.com.guinarangers.guinaapi.repository.UsuarioRepository;
import br.com.guinarangers.guinaapi.service.StorageService;
import br.com.guinarangers.guinaapi.util.Helpers;

@RestController
@RequestMapping("/artigos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ArtigoController {

    Logger logger = LoggerFactory.getLogger(ArtigoController.class);

    @Autowired
    StorageService storageService;

    @Autowired
    ArtigoRepository artigoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TagRepository tagRepository;

    @GetMapping
    @Cacheable(value = "allArticles")
    public Page<ArtigoDto> listAll(@RequestParam(required = false) String usuarioEmail,
            @PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
        Page<Artigo> artigos = artigoRepository.findAll(paginacao);
        return ArtigoDto.convertToPage(artigos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> show(@PathVariable("id") Long id) {
        Optional<Artigo> artigo = artigoRepository.findById(id);
        if (artigo.isPresent()) {
            return ResponseEntity.ok(new ArtigoDetalhesDto(artigo.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DefaultInfoDto("Artigo não encontrado"));
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "allArticles", allEntries = true)
    public ResponseEntity<Object> store(@RequestBody @Valid ArtigoForm form, UriComponentsBuilder uriBuilder) {
        Artigo artigo;
        List<Tag> tags = new ArrayList<Tag>();
        List<Comentario> comments = new ArrayList<Comentario>();
        String imageUrl = "";
        String thumbnailUrl = "";
        try {

            if (artigoRepository.findByTitulo(form.getTitle()).isPresent()) {
                return ResponseEntity.badRequest().body(new DefaultInfoDto("Já existe um artigo com esse título"));
            }

            Optional<Usuario> author = usuarioRepository.findById(form.getAuthor());

            if (!author.isPresent()) {
                return ResponseEntity.badRequest().body(new DefaultInfoDto("Autor não encontrado"));
            }

            for (Long tagId : form.getTags()) {
                if (!tagRepository.findById(tagId).isPresent()) {
                    return ResponseEntity.badRequest().body(new DefaultInfoDto("Tag não encontrada: " + tagId + ""));
                }
                tags.add(tagRepository.findById(tagId).get());
            }

            imageUrl = manageArticleImages(form.getImage(), form.getTitle(), StorageFileTypeEnum.ARTICLE, false);

            thumbnailUrl = manageArticleImages(form.getThumbnail(), form.getTitle(), StorageFileTypeEnum.THUMBNAIL,
                    false);

            if (thumbnailUrl.equals("") || imageUrl.equals("")) {
                return ResponseEntity.status(510).body(new DefaultInfoDto("AWS S3 error"));
            }

            artigo = new Artigo(form.getTitle(), form.getContent(), imageUrl, thumbnailUrl, author.get(), tags,
                    comments);

            artigoRepository.save(artigo);

            URI uri = uriBuilder.path("/artigos/{id}").buildAndExpand(artigo.getId()).toUri();

            return ResponseEntity.created(uri).body(new ArtigoDto(artigo));

        } catch (Exception e) {

            storageService.deleteFile(imageUrl.split("/")[imageUrl.split("/").length - 1]);
            storageService.deleteFile(thumbnailUrl.split("/")[thumbnailUrl.split("/").length - 1]);

            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DefaultErrorDto("Ocorreu um erro ao salvar o artigo", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = "allArticles", allEntries = true)
    public ResponseEntity<Object> update(@PathVariable("id") Long id,
            @RequestBody @Valid AtualizarArtigoForm atualizarArtigoForm) {
        Optional<Artigo> aOptional = artigoRepository.findById(id);
        if (aOptional.isPresent()) {
            String imageUrl = "";
            String thumbnailUrl = "";
            try {

                imageUrl = manageArticleImages(atualizarArtigoForm.getImage(), atualizarArtigoForm.getTitle(),
                        StorageFileTypeEnum.ARTICLE, true);

                thumbnailUrl = manageArticleImages(atualizarArtigoForm.getThumbnail(), atualizarArtigoForm.getTitle(),
                        StorageFileTypeEnum.THUMBNAIL, true);
                if (thumbnailUrl.equals("") || imageUrl.equals("")) {
                    return ResponseEntity.status(510).body(new DefaultInfoDto("AWS S3 error"));
                }

                Artigo artigo = atualizarArtigoForm.update(id, artigoRepository, tagRepository, imageUrl, thumbnailUrl);
                return ResponseEntity.ok(new ArtigoDto(artigo));

            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new DefaultErrorDto("Ocorreu um erro ao atualizar o artigo", e.getMessage()));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DefaultInfoDto("Artigo não encontrado"));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "allArticles", allEntries = true)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        Optional<Artigo> aOptional = artigoRepository.findById(id);
        if (aOptional.isPresent()) {
            try {
                Artigo artigo = aOptional.get();
                artigoRepository.delete(artigo);
                storageService.deleteFile(artigo.getImagem().split("/")[artigo.getImagem().split("/").length - 1]);
                storageService.deleteFile(artigo.getThumbnail().split("/")[artigo.getThumbnail().split("/").length - 1]);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                logger.error(e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new DefaultErrorDto("Ocorreu um erro ao deletar o artigo", e.getMessage()));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DefaultInfoDto("Artigo não encontrado"));
    }

    public String manageArticleImages(String imageData, String title, StorageFileTypeEnum fileType, Boolean isUpdate) {
        String imageUrl;

        if (!Helpers.isBase64(imageData)) {
            logger.info("Image is not base64");
            return "";
        }

        if (title.equals(null)) {
            logger.info("Title is null");
            return "";
        }

        imageUrl = storageService.uploadFile(imageData.split(",")[0], generateFileName(title, fileType), isUpdate);

        if (imageUrl.equals("")) {
            logger.info("Image url is empty");
            return "";
        }

        return imageUrl;
    }

    public String generateFileName(String title, StorageFileTypeEnum fileType) {
        final LocalDateTime now = LocalDateTime.now();
        String fileTypeString = fileType.toString().toLowerCase();

        final String fileName = title.replaceAll(" ", "-").toLowerCase().trim() + "-" + fileTypeString + "-"
                + now.getYear() + "-" + now.getMonthValue() + "-"
                + now.getDayOfMonth()
                + "-" + now.getHour()
                + "-" + now.getMinute()
                + "-" + now.getSecond();
        return fileName;
    }

}
