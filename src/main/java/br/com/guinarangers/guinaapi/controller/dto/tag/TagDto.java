package br.com.guinarangers.guinaapi.controller.dto.tag;

import org.springframework.data.domain.Page;

import br.com.guinarangers.guinaapi.model.Tag;

public class TagDto {
    private String nome;
    private String cor;

    public TagDto(Tag tag) {
        this.nome = tag.getNome();
        this.cor = tag.getCor();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public static Page<TagDto> convertToPage(Page<Tag> tags) {
        return tags.map(TagDto::new);
    }

}
