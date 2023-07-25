package br.com.guinarangers.guinaapi.enums;

public enum StorageFileTypeEnum {
    
    ARTICLE("article"),
    MEME("meme"),
    THUMBNAIL("thumbnail"),
    PROFILE("profile"),
    BANNER("banner"),
    PRIZE("prize");

    private String value;

    StorageFileTypeEnum(String value) {
        this.value = value;
    }
    

}
