package br.com.guinarangers.guinaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.guinarangers.guinaapi.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    
}
