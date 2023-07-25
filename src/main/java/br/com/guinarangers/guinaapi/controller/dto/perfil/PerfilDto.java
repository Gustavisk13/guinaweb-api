package br.com.guinarangers.guinaapi.controller.dto.perfil;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.guinarangers.guinaapi.model.Perfil;

public class PerfilDto {

    private Long id;
    private String nome;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataCriacao;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataAlteracao;

    public PerfilDto(Perfil perfil) {
        this.id = perfil.getId();
        this.nome = perfil.getNome();
        this.dataCriacao = perfil.getCreatedAt();
        this.dataAlteracao = perfil.getUpdatedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public static Page<PerfilDto> convertToPage(Page<Perfil> perfis){
        return perfis.map(PerfilDto::new);
    }

    

}
