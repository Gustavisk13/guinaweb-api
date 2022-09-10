package br.com.guinarangers.guinaapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.guinarangers.guinaapi.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByNome(String nome);
}
