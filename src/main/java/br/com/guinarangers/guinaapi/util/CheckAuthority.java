package br.com.guinarangers.guinaapi.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.guinarangers.guinaapi.model.Usuario;
import br.com.guinarangers.guinaapi.repository.UsuarioRepository;

@Component
public class CheckAuthority {

    @Autowired
    UsuarioRepository usuarioRepository;

    public String getUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    public Boolean hasAuthority(String authority) {
        Optional<Usuario> usuario = usuarioRepository.findById(Long.parseLong(getUserId()));
        String perfil = usuario.get().getPerfis().iterator().next().getNome();

        if (perfil.equals(authority)) {
            return true;
        }
        return false;
    }

    public Usuario getCurrentUser(Long id){
        Usuario usuario = usuarioRepository.findById(id).get();
        return usuario;
    }

}
