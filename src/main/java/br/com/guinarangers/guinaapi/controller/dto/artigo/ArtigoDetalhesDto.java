package br.com.guinarangers.guinaapi.controller.dto.artigo;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.guinarangers.guinaapi.controller.dto.tag.TagDto;
import br.com.guinarangers.guinaapi.controller.dto.usuario.UsuarioDto;
import br.com.guinarangers.guinaapi.model.Artigo;

public class ArtigoDetalhesDto {

    private Long id;
    private String title;
    private String content;
    private String thumbnail;
    private UsuarioDto author;
    private List<TagDto> tags;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public ArtigoDetalhesDto(Artigo artigo) {
        this.id = artigo.getId();
        this.title = artigo.getTitulo();
        this.content = artigo.getConteudo();
        this.thumbnail = artigo.getThumbnail();
        this.author = new UsuarioDto(artigo.getAutor());
        this.tags = artigo.getTagsDto();
        this.createdAt = artigo.getCreatedAt();
        this.updatedAt = artigo.getUpdatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public UsuarioDto getAuthor() {
        return author;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

 

}
