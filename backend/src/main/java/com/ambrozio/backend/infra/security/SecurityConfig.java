package com.ambrozio.backend.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Trazendo o nosso segurança para dentro da configuração
    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()                    .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                    // ABRINDO A PORTA PARA O CADASTRO:
                    .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                    .anyRequest().authenticated()
            )
                // A MÁGICA: Coloca o nosso filtro de JWT ANTES do filtro padrão de senha do Spring!
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    // Expõe o AuthenticationManager para podermos usá-lo no nosso Controller de Login
    @Bean
    public org.springframework.security.authentication.AuthenticationManager authenticationManager(
         org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
    }
}