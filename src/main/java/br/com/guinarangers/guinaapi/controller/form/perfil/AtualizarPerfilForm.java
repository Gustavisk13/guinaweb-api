package br.com.guinarangers.guinaapi.controller.form.perfil;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.guinarangers.guinaapi.model.Perfil;
import br.com.guinarangers.guinaapi.repository.PerfilRepository;

public class AtualizarPerfilForm {

    @NotNull
    @NotEmpty
    @Length(min = 3)
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Perfil update(Long id, PerfilRepository perfilRepository) {

        String formattedNome = this.nome.toUpperCase();

        Perfil perfil = perfilRepository.findById(id).get();
        perfil.setNome("ROLE_" + formattedNome);

        return perfil;
    }

}
