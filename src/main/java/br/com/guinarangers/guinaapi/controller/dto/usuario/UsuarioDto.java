package br.com.guinarangers.guinaapi.controller.dto.usuario;

import org.springframework.data.domain.Page;

import br.com.guinarangers.guinaapi.model.Usuario;

public class UsuarioDto {

    private Long id;
    private String email;
    private String nome;
    private String foto;

    public UsuarioDto(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.nome = usuario.getNome();
        this.foto = usuario.getFoto();

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

    public static Page<UsuarioDto> convertToPage(Page<Usuario> usuarios) {
        return usuarios.map(UsuarioDto::new);
    }

}
