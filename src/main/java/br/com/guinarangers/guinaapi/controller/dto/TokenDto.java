package br.com.guinarangers.guinaapi.controller.dto;

public class TokenDto {


    private String accessToken;
    private String type;

    public TokenDto(String accessToken, String type) {
        this.accessToken = accessToken;
        this.type = type;
      
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String gettype() {
        return type;
    }

   

    

}
