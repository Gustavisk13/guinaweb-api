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

import br.com.guinarangers.guinaapi.config.validacao.EmailJaCadastradoDto;
import br.com.guinarangers.guinaapi.config.validacao.NomeJaCadastradoDto;
import br.com.guinarangers.guinaapi.controller.dto.usuario.DetalheUsuarioDto;
import br.com.guinarangers.guinaapi.controller.dto.usuario.UsuarioDto;
import br.com.guinarangers.guinaapi.controller.form.usuario.AtualizarUsuarioForm;
import br.com.guinarangers.guinaapi.controller.form.usuario.UsuarioForm;
import br.com.guinarangers.guinaapi.model.Usuario;
import br.com.guinarangers.guinaapi.repository.PerfilRepository;
import br.com.guinarangers.guinaapi.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PerfilRepository perfilRepository;

    @GetMapping("/teste")
    public String teste() {
        return "Ola";
    }

    @GetMapping
    @Cacheable(value = "allUsers")
    public Page<UsuarioDto> listAll(@RequestParam(required = false) String usuarioEmail,
            @PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {

        Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
        return UsuarioDto.convertToPage(usuarios);

    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalheUsuarioDto> show(@PathVariable("id") Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(new DetalheUsuarioDto(usuario.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "allUsers", allEntries = true)
    public ResponseEntity<Object> store(@RequestBody @Valid UsuarioForm usuarioForm, UriComponentsBuilder uriBuilder) {

        if (usuarioRepository.findByEmail(usuarioForm.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(new EmailJaCadastradoDto("Email já cadastrado",
                    "O email já foi cadastrado, verificar informações sendo enviadas"));
        }
        
        if (!usuarioRepository.findByNome(usuarioForm.getNome()).isEmpty()) {
            return ResponseEntity.badRequest().body(new NomeJaCadastradoDto("Nome já cadastrado", "Este nome já existe na base"));
        }

        Usuario usuario = usuarioForm.profileConverter(perfilRepository);

        usuarioRepository.save(usuario);

        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(uri).body(new UsuarioDto(usuario));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody @Valid AtualizarUsuarioForm atualizarUsuarioForm){
        Optional<Usuario> uOptional = usuarioRepository.findById(id);
        if (uOptional.isPresent()) {
            Usuario usuario = atualizarUsuarioForm.update(id, usuarioRepository);
            return ResponseEntity.ok(new UsuarioDto(usuario));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "allUsers", allEntries = true)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id){
        Optional<Usuario> uOptional = usuarioRepository.findById(id);
        if (uOptional.isPresent()) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    //TODO criar atualizacao de senha
   /*  @PostMapping("/atualizarSenha")
    @Transactional
    @PreAuthorize("hasRole('ROLE_ALUNO')")
    public ResponseEntity<Object> updatePassworld(Locale locale, @RequestBody String password, String oldPassword){
        Optional<Usuario> usuario = usuarioRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println(usuario.toString());
        return null;
    } */

}
