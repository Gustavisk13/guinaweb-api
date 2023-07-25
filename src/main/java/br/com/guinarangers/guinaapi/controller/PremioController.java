package br.com.guinarangers.guinaapi.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.guinarangers.guinaapi.controller.dto.DefaultInfoDto;
import br.com.guinarangers.guinaapi.controller.dto.premio.DetalhePremioDto;
import br.com.guinarangers.guinaapi.controller.dto.premio.PremioDto;
import br.com.guinarangers.guinaapi.controller.form.premio.PremioForm;
import br.com.guinarangers.guinaapi.model.Premio;
import br.com.guinarangers.guinaapi.repository.PremioRepository;

@RestController
@RequestMapping("/premios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PremioController {

    @Autowired
    PremioRepository premioRepository;

    @GetMapping
    @Cacheable(value = "allPrizes")
    public Page<PremioDto> allPrizes(@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao){

        Page<Premio> premios = premioRepository.findAll(paginacao);

        return PremioDto.convertToPage(premios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhePremioDto> show(@PathVariable("id") Long id){
        Optional<Premio> premio = premioRepository.findById(id);

        if (!premio.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DetalhePremioDto(premio.get()));
    }
    
    @PostMapping
    @Transactional
    public ResponseEntity<Object> create(@RequestBody @Valid PremioForm premioForm, UriComponentsBuilder uriBuilder){

        if (premioRepository.findByNome(premioForm.getNome()).isPresent()) {
            return ResponseEntity.badRequest().body(new DefaultInfoDto("Já existe um prêmio com esse nome"));
        }
         
        Premio premio = premioForm.convertToPremio();

        premioRepository.save(premio);

        URI uri = uriBuilder.path("/premios/{id}").buildAndExpand(premio.getId()).toUri();

        return ResponseEntity.created(uri).body(new PremioDto(premio));

        }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<PremioDto> update(@PathVariable Long id, @RequestBody @Valid PremioForm premioForm) {

        Optional<Premio> optionalPremio = premioRepository.findById(id);

        if (optionalPremio.isPresent()) {
            Premio premio = premioForm.update(optionalPremio.get());
            return ResponseEntity.ok(new PremioDto(premio));
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> delete(@PathVariable Long id) {

        Optional<Premio> optionalPremio = premioRepository.findById(id);

        if (optionalPremio.isPresent()) {
            premioRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();

    }
}
    

