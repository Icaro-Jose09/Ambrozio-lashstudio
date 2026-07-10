package com.ambrozio.backend.controllers;

import com.ambrozio.backend.domain.user.User;
import com.ambrozio.backend.domain.user.dtos.AuthenticationDTO;
import com.ambrozio.backend.domain.user.dtos.LoginResponseDTO;
import com.ambrozio.backend.domain.user.dtos.RegisterDTO;
import com.ambrozio.backend.infra.security.TokenService;
import com.ambrozio.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        
        // Devolvemos um Map para garantir que o JSON será desenhado corretamente na tela
        return ResponseEntity.ok(java.util.Map.of("token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterDTO data) {
        // Verifica se o e-mail já está cadastrado no banco de dados
        if (this.userRepository.findByEmail(data.email()).isPresent()) {
            return ResponseEntity.badRequest().build(); // Retorna erro 400 se já existir
        }

        // Criptografa a senha usando BCrypt
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        
        // Cria o novo usuário com a senha criptografada
        User newUser = new User(data.name(), data.email(), encryptedPassword, data.role());
        
        // Salva no banco de dados
        this.userRepository.save(newUser);

        // Retorna status 200 (OK) informando que foi criado com sucesso
        return ResponseEntity.ok().build();}  
            // Hack para testes: Listar todos os usuários para copiarmos o ID
    @GetMapping("/users")
    public ResponseEntity<java.util.List<User>> getAllUsers() {
        return ResponseEntity.ok(this.userRepository.findAll());
    }
    
}