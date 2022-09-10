package br.com.guinarangers.guinaapi.controller.dto.premio;

import org.springframework.data.domain.Page;

import br.com.guinarangers.guinaapi.model.Premio;

public class PremioDto {

    private String nome;

    public PremioDto(Premio premio) {
        this.nome = premio.getNome();

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static Page<PremioDto> convertToPage(Page<Premio> premios) {
        return premios.map(PremioDto::new);
    }

}
