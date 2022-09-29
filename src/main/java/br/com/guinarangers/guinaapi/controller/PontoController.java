package br.com.guinarangers.guinaapi.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.guinarangers.guinaapi.controller.dto.ponto.PontoDto;
import br.com.guinarangers.guinaapi.controller.form.ponto.PontoForm;
import br.com.guinarangers.guinaapi.model.Ponto;
import br.com.guinarangers.guinaapi.repository.PontoRepository;
import br.com.guinarangers.guinaapi.repository.UsuarioRepository;
import br.com.guinarangers.guinaapi.util.ErrorMessageDto;

@RestController
@RequestMapping("/pontos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PontoController {

    @Autowired
    PontoRepository pontoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    // @CacheEvict(value = "allPoints", allEntries = true);
    public ResponseEntity<Object> create(@RequestBody @Valid PontoForm form) {

        if (pontoRepository.findByUsuario(usuarioRepository.findById(form.getUsuario()).get()).isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorMessageDto("Ponto para este usuário já cadastrado!"));
        }

        if (!usuarioRepository.existsById(form.getUsuario())) {
            return ResponseEntity.badRequest().body(new ErrorMessageDto("Usuario não encontrado"));
        }

        Ponto ponto = form.converter(usuarioRepository);

        pontoRepository.save(ponto);

        return ResponseEntity.ok().body(new PontoDto(ponto));

    }

}
