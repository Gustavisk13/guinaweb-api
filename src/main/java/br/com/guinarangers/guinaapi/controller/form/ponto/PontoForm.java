package br.com.guinarangers.guinaapi.controller.form.ponto;

import javax.validation.constraints.NotNull;

import br.com.guinarangers.guinaapi.model.Ponto;
import br.com.guinarangers.guinaapi.repository.UsuarioRepository;

public class PontoForm {

    @NotNull
    private Long valor;
    @NotNull
    private Long usuario;

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }

    public Ponto converter(UsuarioRepository usuarioRepository) {
        Ponto ponto = new Ponto();
        ponto.setValor(valor);
        ponto.setUsuario(usuarioRepository.findById(this.usuario).get());
        return ponto;
        
    }

}
