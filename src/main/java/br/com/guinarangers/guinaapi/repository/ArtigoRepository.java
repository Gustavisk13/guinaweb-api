package br.com.guinarangers.guinaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.guinarangers.guinaapi.model.Artigo;

public interface ArtigoRepository extends JpaRepository<Artigo, Long> {
    
}
