package br.com.guinarangers.guinaapi.controller;

import java.net.URI;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.guinarangers.guinaapi.controller.dto.tag.DetalheTagDto;
import br.com.guinarangers.guinaapi.controller.dto.tag.TagDto;
import br.com.guinarangers.guinaapi.controller.form.tag.TagForm;
import br.com.guinarangers.guinaapi.model.Tag;
import br.com.guinarangers.guinaapi.repository.TagRepository;
import br.com.guinarangers.guinaapi.util.ErrorMessageDto;

@RestController
@RequestMapping("/tags")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TagController {

    @Autowired
    TagRepository tagRepository;

    private static final Pattern HEXADECIMAL_PATTERN = Pattern.compile("\\p{XDigit}+");

    @GetMapping
    @Cacheable("allTags")
    public Page<TagDto> allTags(@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao){

        Page<Tag> tags = tagRepository.findAll(paginacao);

        return TagDto.convertToPage(tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalheTagDto> show(@PathVariable("id") Long id){
        Optional<Tag> tag = tagRepository.findById(id);

        if (!tag.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DetalheTagDto(tag.get()));
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "allTags", allEntries = true)
    public ResponseEntity<Object> create(@RequestBody @Valid TagForm form, UriComponentsBuilder uriBuilder) {

        if (form.getName() == "" || form.getColor() == "") {
            return ResponseEntity.badRequest().body(new ErrorMessageDto("Nome e cor são obrigatórios"));
        }
            
        if (tagRepository.existsByNome(form.getName())) {
            return ResponseEntity.badRequest().body(new ErrorMessageDto("Tag já cadastrada"));
        }
        
        if (!isHexadecimal(form.getColor())) {
            return ResponseEntity.badRequest().body(new ErrorMessageDto("Cor precisa ser em hexadecimal!"));
        }
            

        Tag tag = form.convertToTag();
        
        tagRepository.save(tag);

        URI uri = uriBuilder.path("/tags/{id}").buildAndExpand(tag.getId()).toUri();

        return ResponseEntity.created(uri).body(new TagDto(tag));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> update(@RequestBody @Valid TagForm form, @PathVariable("id") Long id){
        if (!tagRepository.findById(id).isPresent())  {
            return ResponseEntity.notFound().build();
        }

        if (!isHexadecimal(form.getColor())) {
            return ResponseEntity.badRequest().body(new ErrorMessageDto("Cor precisa ser em hexadecimal!"));
        }
        
        Tag tag = form.update(tagRepository.findById(id).get());

        return ResponseEntity.ok(new TagDto(tag));
        
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "allTags", allEntries = true)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id){
        if (!tagRepository.findById(id).isPresent())  {
            return ResponseEntity.notFound().build();
        }

        tagRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    private boolean isHexadecimal(String input) {
        final Matcher matcher = HEXADECIMAL_PATTERN.matcher(input);
        return matcher.matches();
    }
    
}
