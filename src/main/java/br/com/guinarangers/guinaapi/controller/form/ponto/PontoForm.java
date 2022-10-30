package br.com.guinarangers.guinaapi.controller.form.ponto;

import javax.validation.constraints.NotNull;

import br.com.guinarangers.guinaapi.model.Ponto;
import br.com.guinarangers.guinaapi.repository.UsuarioRepository;

public class PontoForm {

    @NotNull
    private Long valor;
    @NotNull
    private Long user;

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Ponto converter(UsuarioRepository usuarioRepository) {
        Ponto ponto = new Ponto();
        ponto.setValor(valor);
        ponto.setUsuario(usuarioRepository.findById(this.user).get());
        return ponto;
        
    }

}
