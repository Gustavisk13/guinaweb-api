package br.com.guinarangers.guinaapi.util;

import java.util.Base64;

public class Helpers {

    public static Boolean isBase64(String base64) {
        try {
            Base64.getDecoder().decode(base64);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
}
