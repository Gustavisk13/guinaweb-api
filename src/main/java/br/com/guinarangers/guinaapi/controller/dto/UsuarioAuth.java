package br.com.guinarangers.guinaapi.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsuarioAuth {

    private UserDetalhesAuthDto user;
    private String token;

    public UsuarioAuth(UserDetalhesAuthDto user, String token) {
        this.user = user;
        this.token = token;
    }

    @JsonProperty("user")
    public UserDetalhesAuthDto getUser() {
        return user;
    }

    @JsonProperty("token")
    public String getTokenDto() {
        return token;
    }

}
