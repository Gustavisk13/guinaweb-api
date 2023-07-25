package br.com.guinarangers.guinaapi.controller.dto;

public class DefaultErrorDto {

    private String message;
    private String error;

    public DefaultErrorDto(String message, String error) {
        this.message = message;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    
}
