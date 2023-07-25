package br.com.guinarangers.guinaapi.controller.dto.tag;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.guinarangers.guinaapi.model.Tag;

public class DetalheTagDto {

    private Long id;
    private String nome;
    private String cor;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataCriacao;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataAlteracao;

    public DetalheTagDto(Tag tag) {
        this.id = tag.getId();
        this.nome = tag.getNome();
        this.cor = tag.getCor();
        this.dataCriacao = tag.getCreatedAt();
        this.dataAlteracao = tag.getUpdatedAt();
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("name")
    public String getNome() {
        return nome;
    }

    @JsonProperty("color")
    public String getCor() {
        return cor;
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
