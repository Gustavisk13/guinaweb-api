package br.com.guinarangers.guinaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.base.Optional;

import br.com.guinarangers.guinaapi.model.Premio;

public interface PremioRepository extends JpaRepository<Premio, Long> {
    Optional<Premio> findByNome(String nome);


}
