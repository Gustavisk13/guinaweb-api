package br.com.guinarangers.guinaapi.config.validacao;

public class PerfilSemPermissaoDto {
    private String title;
    private String error;

    

    public PerfilSemPermissaoDto(String title, String error) {
        this.title = title;
        this.error = error;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }

    
}
