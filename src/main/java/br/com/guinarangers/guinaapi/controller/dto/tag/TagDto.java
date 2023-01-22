package br.com.guinarangers.guinaapi.controller.dto.tag;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.guinarangers.guinaapi.model.Tag;

public class TagDto {

    private Long id;
    private String nome;
    private String cor;

    public TagDto(Tag tag) {
        this.id = tag.getId();
        this.nome = tag.getNome();
        this.cor = tag.getCor();
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("name")
    public String getNome() {
        return nome;
    }

    @JsonProperty("color")
    public String getCor() {
        return cor;
    }

    public static Page<TagDto> convertToPage(Page<Tag> tags) {
        return tags.map(TagDto::new);
    }

    public static List<TagDto> convertToDto(List<Tag> tags) {
        List<TagDto> tagsDto = new ArrayList<>();
        for (Tag tag : tags) {
            tagsDto.add(new TagDto(tag));
        }
        return tagsDto;

    }

}
