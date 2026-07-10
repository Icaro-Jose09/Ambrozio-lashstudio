package com.ambrozio.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/studio")
public class StudioController {

    // Essa rota não está na lista de permissões do SecurityConfig. Portanto, exige Token!
    @GetMapping("/boas-vindas")
    public ResponseEntity<String> secretMessage() {
        return ResponseEntity.ok("Bem-vindo ao backend privado do Ambrozio Lash Studio! O seu Token JWT é válido.");
    }
}