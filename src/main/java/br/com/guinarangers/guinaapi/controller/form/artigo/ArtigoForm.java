package br.com.guinarangers.guinaapi.controller.form.artigo;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class ArtigoForm {
    
    @NotNull @Length(min = 5 , max = 100)
    private String title;
    @NotNull @Length(min = 20)
    private String content;
    @NotNull
    private String image;
    @NotNull
    private String thumbnail;
    @NotNull
    private Long author;
    @NotNull
    private List<Long> tags;
   

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getImage() {
        
        return image.split(",")[1];
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getThumbnail() {
        return thumbnail.split(",")[1];
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    public Long getAuthor() {
        return author;
    }
    public void setAuthor(Long author) {
        this.author = author;
    }
    public List<Long> getTags() {
        return tags;
    }
    public void setTags(List<Long> tags) {
        this.tags = tags;
    }
    
}
