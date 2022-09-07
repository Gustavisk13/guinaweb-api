package br.com.guinarangers.guinaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAutoConfiguration
@EnableSpringDataWebSupport
@EnableCaching

@EnableWebMvc
public class GuinaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuinaApiApplication.class, args);
	}

}
