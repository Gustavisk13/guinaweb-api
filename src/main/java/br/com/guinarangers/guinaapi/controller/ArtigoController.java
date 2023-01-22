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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.guinarangers.guinaapi.controller.dto.DefaultErrorDto;
import br.com.guinarangers.guinaapi.controller.dto.DefaultInfoDto;
import br.com.guinarangers.guinaapi.controller.dto.artigo.ArtigoDto;
import br.com.guinarangers.guinaapi.controller.form.artigo.ArtigoForm;
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

    @PostMapping
    @Transactional
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

            imageUrl = manageArticleImages(form.getImage(), form.getTitle(), StorageFileTypeEnum.ARTICLE);

            thumbnailUrl = manageArticleImages(form.getThumbnail(), form.getTitle(), StorageFileTypeEnum.THUMBNAIL);

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

    public String manageArticleImages(String imageData, String title, StorageFileTypeEnum fileType) {
        String imageUrl;

        if (!Helpers.isBase64(imageData)) {
            logger.info("Image is not base64");
            return "";
        }

        if (title.equals(null)) {
            logger.info("Title is null");
            return "";
        }

        imageUrl = storageService.uploadFile(imageData.split(",")[0], generateFileName(title, fileType));

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
