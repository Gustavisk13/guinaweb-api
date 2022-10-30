package br.com.guinarangers.guinaapi.controller.form.premio;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.guinarangers.guinaapi.model.Premio;

public class PremioForm {

    @NotNull
    @NotEmpty
    @Length(min = 3)
    private String nome;

    @NotNull
    @NotEmpty
    @Length(min = 3)
    private String imagem;

    public PremioForm() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Premio convertToPremio() {
        return new Premio(nome, imagem);
    }

    public Premio update(Premio premio) {
        premio.setNome(nome);
        premio.setImagem(imagem);
        return premio;
    }

}
