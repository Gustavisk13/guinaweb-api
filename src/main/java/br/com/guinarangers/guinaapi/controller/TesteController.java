package br.com.guinarangers.guinaapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TesteController {
    
    @RequestMapping
    public String test() {
        return "teste";
    }

}
