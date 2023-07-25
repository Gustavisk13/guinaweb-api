package br.com.guinarangers.guinaapi.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import br.com.guinarangers.guinaapi.controller.dto.tag.TagDto;

@Entity
public class Artigo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @Column(columnDefinition = "TEXT")
    private String conteudo;
    private String imagem;
    private String thumbnail;

    @ManyToOne
    private Usuario autor;
    
    @ManyToMany
    private List<Tag> tags;

    @ManyToMany
    private List<Comentario> comentarios;

    public Artigo() {
    }

    public Artigo(String titulo, String conteudo, String imagem, String thumbnail, Usuario autor,
            List<Tag> tags, List<Comentario> comentarios) {
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.imagem = imagem;
        this.thumbnail = thumbnail;
        this.autor = autor;
        this.tags = tags;
        this.comentarios = comentarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public List<TagDto> getTagsDto() {
        return TagDto.convertToDto(this.tags);
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
