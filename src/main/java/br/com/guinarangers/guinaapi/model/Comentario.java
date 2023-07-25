package br.com.guinarangers.guinaapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Comentario extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String conteudo;
    private Long likes;
    private Long deslikes;

    @ManyToOne
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comentario comentarioPai;

    @OneToMany(mappedBy = "comentarioPai")
    private List<Comentario> subComentarios = new ArrayList<>();

    public Comentario(String conteudo, Usuario autor) {
        this.conteudo = conteudo;
        this.autor = autor;
    }

    public Comentario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getDeslikes() {
        return deslikes;
    }

    public void setDeslikes(Long deslikes) {
        this.deslikes = deslikes;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Comentario getComentarioPai() {
        return comentarioPai;
    }

    public void setComentarioPai(Comentario comentarioPai) {
        this.comentarioPai = comentarioPai;
    }

    public List<Comentario> getSubComentarios() {
        return subComentarios;
    }

    public void setSubComentarios(List<Comentario> subComentarios) {
        this.subComentarios = subComentarios;
    }

}
