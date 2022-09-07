package br.com.guinarangers.guinaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.guinarangers.guinaapi.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long>{
    
    Perfil findByNome(String perfil);

}
