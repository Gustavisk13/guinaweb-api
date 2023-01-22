package br.com.guinarangers.guinaapi.controller.form.artigo;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.guinarangers.guinaapi.model.Artigo;
import br.com.guinarangers.guinaapi.model.Tag;
import br.com.guinarangers.guinaapi.repository.ArtigoRepository;
import br.com.guinarangers.guinaapi.repository.TagRepository;

public class AtualizarArtigoForm {

    @NotNull
    @Length(min = 3)
    private String title;
    @NotNull @Length(min = 20)
    private String content;
    @NotNull
    private String image;
    @NotNull
    private String thumbnail;
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

    public List<Long> getTags() {
        return tags;
    }

    public void setTags(List<Long> tags) {
        this.tags = tags;
    }

    public Artigo update(Long id, ArtigoRepository artigoRepository, TagRepository tagRepository, String image,
            String thumbnail) {
        Artigo artigo = artigoRepository.findById(id).get();
        List<Tag> oTags = new ArrayList<Tag>();

        for (Long idTag : this.tags) {
            oTags.add(tagRepository.findById(idTag).get());
        }

        artigo.setTitulo(this.title);
        artigo.setConteudo(this.content);
        artigo.setImagem(image);
        artigo.setThumbnail(thumbnail);
        artigo.setTags(oTags);

        return artigo;
    }

}
