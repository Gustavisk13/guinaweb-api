package br.com.guinarangers.guinaapi.controller.dto.premio;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.guinarangers.guinaapi.model.Premio;

public class DetalhePremioDto {
    
    private Long id;
    private String nome;
    private String imagem;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataCriacao;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataAlteracao;

    public DetalhePremioDto(Premio premio) {
        this.id = premio.getId();
        this.nome = premio.getNome();
        this.imagem = premio.getImagem();
        this.dataCriacao = premio.getCreatedAt();
        this.dataAlteracao = premio.getUpdatedAt();
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("name")
    public String getNome() {
        return nome;
    }

    @JsonProperty("image")
    public String getImagem() {
        return imagem;
    }

    @JsonProperty("created_at")
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    @JsonProperty("updated_at")
    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    

}
