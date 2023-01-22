package br.com.guinarangers.guinaapi.util;

import java.util.Base64;

import br.com.guinarangers.guinaapi.enums.StorageFileTypeEnum;

public class Helpers {

    public static Boolean isBase64(String base64) {
        try {
            Base64.getDecoder().decode(base64.split(",")[0]);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static String generateRandomImageLink(StorageFileTypeEnum type) {
        switch (type) {
            case ARTICLE:
                return "https://picsum.photos/1920/1080";
            case THUMBNAIL:
                return "https://picsum.photos/500/500";
            default:
                return "https://picsum.photos/1920/1080";
        }

    }

    public static String generateMd5HashString(String string) {
        return org.apache.commons.codec.digest.DigestUtils.md5Hex(string);
    }
        
    

}
