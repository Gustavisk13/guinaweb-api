package br.com.guinarangers.guinaapi.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/api/**")
            .allowedOrigins("http://127.0.0.1:3000/")
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(true).maxAge(3600);

        // Add more mappings...
    }
    
}
