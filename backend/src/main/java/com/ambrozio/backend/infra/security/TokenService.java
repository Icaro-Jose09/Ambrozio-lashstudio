package com.ambrozio.backend.infra.security;

import com.ambrozio.backend.domain.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // Puxa o valor que configuramos lá no application.yml
    @Value("${api.security.token.secret}")
    private String secret;

    // Método para fabricar o crachá (gerar token)
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("ambrozio-lashstudio-api") // Quem está emitindo o token
                    .withSubject(user.getEmail()) // Qual é o usuário dono deste token
                    .withExpiresAt(genExpirationDate()) // Quando o token vence
                    .sign(algorithm); // Carimba com a nossa chave secreta
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar o token JWT", exception);
        }
    }

    // Método para ler o crachá (validar token)
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("ambrozio-lashstudio-api")
                    .build()
                    .verify(token)
                    .getSubject(); // Devolve o e-mail se o token for válido
        } catch (JWTVerificationException exception) {
            return ""; // Devolve vazio se o token for falso, expirado ou inválido
        }
    }

    // Regra de expiração: 2 horas a partir do momento do login (Fuso horário de Brasília -03:00)
    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}