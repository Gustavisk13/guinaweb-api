package br.com.guinarangers.guinaapi.controller.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.guinarangers.guinaapi.model.Usuario;
import br.com.guinarangers.guinaapi.repository.UsuarioRepository;

public class AtualizarUsuarioForm {
    
    @NotNull @NotEmpty @Length(min = 3)
    private String nome;
    @NotNull @NotEmpty @Email
    private String email;
    @NotNull @NotEmpty
    private String foto;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFoto() {
        return foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Usuario update(Long id, UsuarioRepository usuarioRepository){
        Usuario usuario = usuarioRepository.findById(id).get();
        usuario.setNome(this.nome);
        usuario.setEmail(this.email);
        usuario.setFoto(this.foto);

        return usuario;
    }

}
