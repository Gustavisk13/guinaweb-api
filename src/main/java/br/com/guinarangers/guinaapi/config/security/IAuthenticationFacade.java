package br.com.guinarangers.guinaapi.config.security;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();

}


