package br.com.guinarangers.guinaapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.guinarangers.guinaapi.model.Ponto;
import br.com.guinarangers.guinaapi.model.Usuario;

public interface PontoRepository extends JpaRepository<Ponto, Long> {
    Optional<Ponto> findByUsuario(Usuario usuario);
}
