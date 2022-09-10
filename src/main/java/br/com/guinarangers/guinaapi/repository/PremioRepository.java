package br.com.guinarangers.guinaapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.guinarangers.guinaapi.model.Premio;

public interface PremioRepository extends JpaRepository<Premio, Long> {
    List<Premio> findByNome(String nome);

}
