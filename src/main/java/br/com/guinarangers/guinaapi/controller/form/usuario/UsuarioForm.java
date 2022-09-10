package br.com.guinarangers.guinaapi.controller.form.usuario;

import java.util.Arrays;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCrypt;

import br.com.guinarangers.guinaapi.config.security.ValidPassword;
import br.com.guinarangers.guinaapi.model.Perfil;
import br.com.guinarangers.guinaapi.model.Usuario;
import br.com.guinarangers.guinaapi.repository.PerfilRepository;

public class UsuarioForm {

    @NotNull @NotEmpty @Length(min = 3)
    private String nome;
    @NotNull @NotEmpty @Email
    private String email;

    private String foto;

    @NotNull @NotEmpty
    @ValidPassword
    private String senha;
    private String perfil;

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

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    private String hashSenha(){
        return BCrypt.hashpw(this.senha, BCrypt.gensalt());
    }

    public Usuario profileConverter(PerfilRepository perfilRepository) {
        Perfil perfil = perfilRepository.findByNome(this.perfil);   
        
        return new Usuario(email, foto, nome, hashSenha(), Arrays.asList(perfil));
    }

}
