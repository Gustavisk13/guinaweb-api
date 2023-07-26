package br.com.guinarangers.guinaapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class TesteController {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.name}")
    private String appName;

    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("profile", activeProfile.toUpperCase());
        model.addAttribute("version", appVersion);
        model.addAttribute("name", appName);
        return "teste";
    }

}
