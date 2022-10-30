package br.com.guinarangers.guinaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.base.Optional;

import br.com.guinarangers.guinaapi.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByNome(String nome);
    Boolean existsByNome(String nome);
}
