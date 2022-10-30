package br.com.guinarangers.guinaapi.controller.dto.ponto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.guinarangers.guinaapi.model.Ponto;

public class DetalhePontoDto {

    private Long pointId;
    private String user;
    private Long value;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime alteredAt;

    public DetalhePontoDto(Ponto ponto) {
        this.pointId = ponto.getId();
        this.user = ponto.getUsuario().getNome();
        this.value = ponto.getValor();
        this.createdAt = ponto.getCreatedAt();
        this.alteredAt = ponto.getUpdatedAt();
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getAlteredAt() {
        return alteredAt;
    }

    public void setAlteredAt(LocalDateTime alteredAt) {
        this.alteredAt = alteredAt;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }



    public Long getPointId() {
        return pointId;
    }



    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }

    

    
    

}
