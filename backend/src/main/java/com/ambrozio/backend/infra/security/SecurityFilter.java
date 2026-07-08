package com.ambrozio.backend.infra.security;

import com.ambrozio.backend.domain.user.User;
import com.ambrozio.backend.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Tenta pegar o token do cabeçalho da requisição
        var token = this.recoverToken(request);
        
        if (token != null) {
            // 2. Manda para a máquina de crachás ler e devolver o email
            var email = tokenService.validateToken(token);
            
            if (!email.isEmpty()) {
                // 3. Vai no banco de dados e busca quem é o dono desse e-mail
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
                
                // 4. Cria o "passe livre" dizendo para o Spring: "Pode confiar, eu já validei o JWT"
                // (Na próxima sprint, passaremos as Roles/Permissões aqui dentro do Collections)
                var authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                
                // 5. Salva o passe livre no contexto do Spring
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        
        // 6. Continua o fluxo normal da requisição (libera a catraca)
        filterChain.doFilter(request, response);
    }

    // Método auxiliar que limpa a palavra "Bearer " e devolve só o token puro
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}