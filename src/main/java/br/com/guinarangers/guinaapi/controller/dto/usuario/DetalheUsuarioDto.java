package br.com.guinarangers.guinaapi.controller.dto.usuario;

import java.time.LocalDateTime;

import br.com.guinarangers.guinaapi.model.Usuario;

public class DetalheUsuarioDto {
    private Long id;
    private String email;
    private String nome;
    private String foto;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAlteracao;

    public DetalheUsuarioDto(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.nome = usuario.getNome();
        this.foto = usuario.getFoto();
        this.dataCriacao = usuario.getDataCriacao();
        this.dataAlteracao = usuario.getDataAlteracao();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getFoto() {
        return foto;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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
}
