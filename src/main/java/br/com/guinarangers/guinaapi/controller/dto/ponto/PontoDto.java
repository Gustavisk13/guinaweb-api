package br.com.guinarangers.guinaapi.controller.dto.ponto;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.guinarangers.guinaapi.model.Ponto;

public class PontoDto {
    
    private String usuario;
    private Long value;

    public PontoDto(Ponto ponto) {
        this.usuario = ponto.getUsuario().getNome();
        this.value = ponto.getValor();
    }

    @JsonProperty("user")
    public String getUsuario() {
        return usuario;
    }
    public Long getValue() {
        return value;
    }


    
    public static Page<PontoDto> convertToPage(Page<Ponto> pontos) {
        return pontos.map(PontoDto::new);
    }
}
