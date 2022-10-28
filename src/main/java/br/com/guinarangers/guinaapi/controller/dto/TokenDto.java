package br.com.guinarangers.guinaapi.controller.dto;

public class TokenDto {

    private Long id;
    private String name;
    private String email;
    private String token;
    private String type;

    public TokenDto(String token, String type, Long id, String name, String email) {
        this.token = token;
        this.type = type;
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public String gettype() {
        return type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void settype(String type) {
        this.type = type;
    }

    

}
