package br.com.guinarangers.guinaapi.controller.form.perfil;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.guinarangers.guinaapi.model.Perfil;

public class PerfilForm {
    
    @NotNull @NotEmpty @Length(min = 3)
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Perfil formConverter(){

        String formattedNome = this.nome.toUpperCase();
        return new Perfil("ROLE_"+formattedNome);
    }

}
