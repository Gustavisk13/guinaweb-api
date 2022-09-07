package br.com.guinarangers.guinaapi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.guinarangers.guinaapi.controller.dto.usuario.DetalheUsuarioDto;
import br.com.guinarangers.guinaapi.controller.dto.usuario.UsuarioDto;
import br.com.guinarangers.guinaapi.model.Usuario;
import br.com.guinarangers.guinaapi.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/teste")
    public String teste() {
        return "Ola";
    }

    @GetMapping
    @Cacheable(value="allUsers")
    public Page<UsuarioDto> listAll(@RequestParam(required = false) String usuarioEmail, @PageableDefault(sort="id",direction = Direction.ASC, page = 0, size = 10) Pageable paginacao){
    
            Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
            return UsuarioDto.convertToPage(usuarios);
       
    }

    @GetMapping("/{id}")
    public ResponseEntity <DetalheUsuarioDto> show(@PathVariable("id") Long id){
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(new DetalheUsuarioDto(usuario.get()));
        }
        return ResponseEntity.notFound().build();
    }

    
}
