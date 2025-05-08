package com.devsuperior.demo.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder; //Esse PasswordEncoder eh uma interface em que pode ser uma implementacao do BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Liberando o h2
    @Bean
    @Profile("test") //esse filtro vai servir somente par ao perfil de teste
    @Order(1)
    public SecurityFilterChain h2SecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher(PathRequest.toH2Console()) // Aplica apenas para o H2 Console
                .csrf(csrf -> csrf.disable())               // Desativa CSRF
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())) // Permite o uso de iframes (necessário pro H2 Console)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); // Libera acesso ao console
        return http.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()); //desabilitando contra ataques csrf pois api rest nao grava estados em sessao
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); //confiugura a permissao para os meus endpoint, no caso qualquer caminho eu vou permitir -> controle de acesso global, no caso geral liberado e eu configuro nas rotas que precisarem de restricao de acesso
        return http.build();
    }


}
