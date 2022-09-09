package br.com.guinarangers.guinaapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class TesteController {

    @Value("${spring.profiles.active}")
    private String activeProfile;
    
    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("profile", activeProfile.toUpperCase());
        return "teste";
    }

}
