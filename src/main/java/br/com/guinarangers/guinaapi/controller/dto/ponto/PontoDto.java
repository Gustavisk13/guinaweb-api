package br.com.guinarangers.guinaapi.controller.dto.ponto;

import org.springframework.data.domain.Page;

import br.com.guinarangers.guinaapi.model.Ponto;

public class PontoDto {
    
    private Long id_usuario;
    private Long valor;

    public PontoDto(Ponto ponto) {
        this.id_usuario = ponto.getUsuario().getId();
        this.valor = ponto.getValor();
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    Page<PontoDto> convertToPage(Page<Ponto> pontos) {
        return pontos.map(PontoDto::new);
    }
    
}
