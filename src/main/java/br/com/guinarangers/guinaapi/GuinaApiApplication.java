package br.com.guinarangers.guinaapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.com.guinarangers.guinaapi.config.security.VaultConfiguration;

@SpringBootApplication
@EnableAutoConfiguration
@EnableSpringDataWebSupport
@EnableCaching
@EnableJpaAuditing
@EnableWebMvc
public class GuinaApiApplication {

	private static Logger logger = LoggerFactory.getLogger(GuinaApiApplication.class);

	public static void main(String[] args) {

		ConfigurableApplicationContext context =  SpringApplication.run(GuinaApiApplication.class, args);

		VaultConfiguration vault =  context.getBean(VaultConfiguration.class);

		logger.info("Login: " + vault.getLogin());
		logger.info("Password: " + vault.getPassword());
	}

}
