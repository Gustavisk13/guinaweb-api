package br.com.guinarangers.guinaapi.controller.form.ponto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class PontoForm {

    @NotNull
    private Long valor;
    @NotEmpty
    @NotNull
    @Length(min = 3)
    private String usuario;

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }


}
