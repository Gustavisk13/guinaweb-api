package br.com.guinarangers.guinaapi.controller.form.tag;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.guinarangers.guinaapi.model.Tag;

public class TagForm {

    @NotNull @NotEmpty @Length(min = 3)
    private String name;

    @NotNull @NotEmpty @Length(min = 3)
    private String color;

    public TagForm(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public TagForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Tag convertToTag() {
        return new Tag(name, "#"+color);
    }

    public Tag update(Tag tag) {
        tag.setNome(name);
        tag.setCor("#"+color);
        return tag;
    }

}
