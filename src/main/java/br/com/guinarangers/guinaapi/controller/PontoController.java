package br.com.guinarangers.guinaapi.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.guinarangers.guinaapi.controller.form.ponto.PontoForm;
import br.com.guinarangers.guinaapi.model.Usuario;
import br.com.guinarangers.guinaapi.repository.PontoRepository;
import br.com.guinarangers.guinaapi.repository.UsuarioRepository;

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
    public String create(@RequestBody @Valid PontoForm form) {

        List<Usuario> usuarios = usuarioRepository.findByNomeContainingIgnoreCase(form.getUsuario());

        if (usuarios.size() > 1) {
            return "Erro: Mais de um usuario encontrado";
        } 
        return "Sucesso";
        

    }
}
