package br.com.guinarangers.guinaapi.controller.dto.artigo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.guinarangers.guinaapi.model.Artigo;

public class ArtigoDto {

    private Long id;
    private String title;
    private String preview;
    private String author;
    private String thumbnail;
    private String tags;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public ArtigoDto(Artigo artigo) {
        this.id = artigo.getId();
        this.title = artigo.getTitulo();
        this.preview = artigo.getConteudo().substring(0, 100);
        this.author = artigo.getAutor().getNome();
        this.thumbnail = artigo.getThumbnail();
        this.tags = artigo.getTags().stream().map(tag -> tag.getNome()).reduce("", (a, b) -> a + ", " + b)
                .replaceFirst(", ", "");
        this.createdAt = artigo.getCreatedAt();
        this.updatedAt = artigo.getUpdatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPreview() {
        return preview;
    }

    public String getAuthor() {
        return author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTags() {
        return tags;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

}
