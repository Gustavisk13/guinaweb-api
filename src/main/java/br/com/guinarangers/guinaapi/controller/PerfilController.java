package br.com.guinarangers.guinaapi.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

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

import br.com.guinarangers.guinaapi.config.validacao.NomeJaCadastradoDto;
import br.com.guinarangers.guinaapi.config.validacao.PerfilSemPermissaoDto;
import br.com.guinarangers.guinaapi.controller.dto.perfil.DetalhePerfilDto;
import br.com.guinarangers.guinaapi.controller.dto.perfil.PerfilDto;
import br.com.guinarangers.guinaapi.controller.form.perfil.AtualizarPerfilForm;
import br.com.guinarangers.guinaapi.controller.form.perfil.PerfilForm;
import br.com.guinarangers.guinaapi.model.Perfil;
import br.com.guinarangers.guinaapi.repository.PerfilRepository;
import br.com.guinarangers.guinaapi.util.CheckAuthority;

@RestController
@RequestMapping("/perfis")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PerfilController {

    @Autowired
    PerfilRepository perfilRepository;

    @Autowired
    CheckAuthority checkAuthority;

    @GetMapping("/test")
    public String teste() {

        return "Ola " + checkAuthority.getCurrentUser(Long.parseLong(checkAuthority.getUserId())).getNome();
    }

    @GetMapping
    @Cacheable(value = "allProfiles")
    public Page<PerfilDto> listAll(@RequestParam(required = false) String nomePerfil,
            @PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
        Page<Perfil> perfis = perfilRepository.findAll(paginacao);
        return PerfilDto.convertToPage(perfis);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhePerfilDto> show(@PathVariable("id") Long id) {
        Optional<Perfil> perfil = perfilRepository.findById(id);
        if (perfil.isPresent()) {
            return ResponseEntity.ok(new DetalhePerfilDto(perfil.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "allProfiles", allEntries = true)
    public ResponseEntity<Object> store(@RequestBody @Valid PerfilForm perfilForm, UriComponentsBuilder uriBuilder) {

        Perfil perfil = perfilForm.formConverter();

        if (perfilRepository.findByNome(perfil.getNome()) != null) {
            return ResponseEntity.badRequest()
                    .body(new NomeJaCadastradoDto("Nome já cadastrado", "Este nome já existe na base"));
        }

        System.out.println(perfil.toString());
        perfilRepository.save(perfil);

        URI uri = uriBuilder.path("/perfis/{id}").buildAndExpand(perfil.getId()).toUri();

        return ResponseEntity.created(uri).body(new PerfilDto(perfil));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> update(@PathVariable("id") Long id,
            @RequestBody @Valid AtualizarPerfilForm atualizarPerfilForm) {
        Optional<Perfil> pOptional = perfilRepository.findById(id);
        if (pOptional.isPresent()) {
            Perfil perfil = atualizarPerfilForm.update(id, perfilRepository);
            return ResponseEntity.ok(new PerfilDto(perfil));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "allProfiles", allEntries = true)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {

        if (!checkAuthority.hasAuthority("ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new PerfilSemPermissaoDto("Procure um administrador", "Este perfil não possui permissão para esta açao"));
        }

        Optional<Perfil> pOptional = perfilRepository.findById(id);

        if (pOptional.isPresent()) {
            perfilRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
