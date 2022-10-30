package br.com.guinarangers.guinaapi.controller;

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

import br.com.guinarangers.guinaapi.controller.dto.ponto.DetalhePontoDto;
import br.com.guinarangers.guinaapi.controller.dto.ponto.PontoDto;
import br.com.guinarangers.guinaapi.controller.form.ponto.AtualizarPontoForm;
import br.com.guinarangers.guinaapi.controller.form.ponto.PontoForm;
import br.com.guinarangers.guinaapi.model.Ponto;
import br.com.guinarangers.guinaapi.repository.PontoRepository;
import br.com.guinarangers.guinaapi.repository.UsuarioRepository;
import br.com.guinarangers.guinaapi.util.ErrorMessageDto;

@RestController
@RequestMapping("/pontos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PontoController{

    @Autowired
    PontoRepository pontoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping
    @Cacheable("allPoints")
    public Page<PontoDto> allPoints(@PageableDefault(sort = "usuario", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao){

        Page<Ponto> pontos = pontoRepository.findAll(paginacao);

        return PontoDto.convertToPage(pontos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhePontoDto> show(@PathVariable("id") Long id){
        Optional<Ponto> ponto = pontoRepository.findByusuario_id(id);

        if (!ponto.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DetalhePontoDto(ponto.get()));
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "allPoints", allEntries = true)
    public ResponseEntity<Object> create(@RequestBody @Valid PontoForm form) {

        if (pontoRepository.findByUsuario(usuarioRepository.findById(form.getUser()).get()).isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorMessageDto("Ponto para este usuário já cadastrado!"));
        }

        if (!usuarioRepository.existsById(form.getUser())) {
            return ResponseEntity.badRequest().body(new ErrorMessageDto("Usuario não encontrado"));
        }

        Ponto ponto = form.converter(usuarioRepository);

        pontoRepository.save(ponto);

        return ResponseEntity.ok().body(new PontoDto(ponto));

    }

    @PutMapping("/{id}")
    @Transactional
    // @CacheEvict(value = "allPoints", allEntries = true);
    public ResponseEntity<Object> update(@RequestBody @Valid AtualizarPontoForm form, @PathVariable("id") Long id,
            @RequestParam Boolean adicionar, @RequestParam Boolean remover) {

        System.out.println("Entrou no update");
        if (remover == null || adicionar == null) {
            return ResponseEntity.badRequest().body(new ErrorMessageDto("Informe uma ação para atualizar o ponto!!"));
        }

        if (remover && adicionar ) {
            return ResponseEntity.badRequest().body(new ErrorMessageDto("Apenas uma ação permitida!!"));
        }
        Optional<Ponto> pOptional = pontoRepository.findByusuario_id(id);
        System.out.println(pOptional);
        if (adicionar && pOptional.isPresent()) {
            Ponto ponto = form.adicionar(id, pontoRepository);
            return ResponseEntity.ok(new PontoDto(ponto));
        }
        if (remover && pOptional.isPresent()) {
            Ponto ponto = form.remover(id, pontoRepository);
            if (ponto == null) {
                return ResponseEntity.badRequest().body(new ErrorMessageDto("Ponto insuficiente para realizar a operação!!"));
            }
            return ResponseEntity.ok(new PontoDto(ponto));
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id){
        Optional<Ponto> ponto = pontoRepository.findById(id);

        if (!ponto.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DetalhePontoDto(ponto.get()));
    }

}
