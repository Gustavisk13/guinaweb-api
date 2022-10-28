package br.com.guinarangers.guinaapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.guinarangers.guinaapi.config.security.TokenService;
import br.com.guinarangers.guinaapi.controller.dto.TokenDto;
import br.com.guinarangers.guinaapi.controller.form.usuario.LoginForm;
import br.com.guinarangers.guinaapi.model.Usuario;
import br.com.guinarangers.guinaapi.repository.UsuarioRepository;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AutenticacaoController {

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    UsuarioRepository usuarioRepository;
    
    @PostMapping
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form) {

        UsernamePasswordAuthenticationToken dadosLogin = form.converter();
        


        try {
            Authentication authentication = authManager.authenticate(dadosLogin);

            String token = tokenService.gerarToken(authentication);

            Usuario userDetails = usuarioRepository.findByEmail(dadosLogin.getName()).get();

            return ResponseEntity.ok(new TokenDto(token, "Bearer",userDetails.getId(),userDetails.getNome(),userDetails.getEmail()));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }

    }

}
