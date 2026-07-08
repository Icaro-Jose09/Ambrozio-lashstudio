package com.ambrozio.backend.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Desativa a proteção CSRF, pois em APIs REST usamos Tokens, não Cookies de Sessão
                .csrf(csrf -> csrf.disable())
                // Configura a API para ser Stateless (não guardar sessão do usuário)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configuração das rotas
                .authorizeHttpRequests(authorize -> authorize
                        // Libera totalmente o acesso à documentação do Swagger
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        // Libera a futura rota de Login para que visitantes possam se autenticar
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        // Bloqueia qualquer outra rota (exigindo token)
                        .anyRequest().authenticated()
                )
                .build();
    }

    // Ensina o Spring Security a usar o BCrypt para verificar e codificar as senhas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}