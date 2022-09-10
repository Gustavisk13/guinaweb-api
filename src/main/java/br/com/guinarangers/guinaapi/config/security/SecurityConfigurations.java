package br.com.guinarangers.guinaapi.config.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import br.com.guinarangers.guinaapi.repository.UsuarioRepository;

@EnableWebSecurity
@Configuration

public class SecurityConfigurations {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and()

                .csrf().disable()
                .authorizeHttpRequests((authz) -> {
                    try {
                        authz
                                .antMatchers(HttpMethod.GET, "/test").permitAll()
                                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                                .antMatchers(HttpMethod.GET, "/usuarios").permitAll()
                                .antMatchers(HttpMethod.GET, "/usuarios/**").permitAll()
                                .antMatchers(HttpMethod.GET, "/perfis/**").hasRole("ADMIN")

                                .anyRequest().authenticated()
                                .and()

                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, repository),
                                        UsernamePasswordAuthenticationFilter.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                )

                .httpBasic(withDefaults())

                .headers().addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*"));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(autenticacaoService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**",
                "/swagger-resources/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
