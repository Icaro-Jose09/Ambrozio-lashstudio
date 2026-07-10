package com.ambrozio.backend.controllers;

import com.ambrozio.backend.domain.service.StudioService;
import com.ambrozio.backend.domain.service.dtos.StudioServiceRequestDTO;
import com.ambrozio.backend.repositories.StudioServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class StudioServiceController {

    @Autowired
    private StudioServiceRepository repository;

    // Rota para CRIAR um novo serviço (POST)
    @PostMapping
    public ResponseEntity<StudioService> createService(@RequestBody StudioServiceRequestDTO data) {
        StudioService newService = new StudioService(data.name(), data.description(), data.price(), data.durationMinutes());
        repository.save(newService);
        return ResponseEntity.ok(newService);
    }

    // Rota para LISTAR todos os serviços (GET)
    @GetMapping
    public ResponseEntity<List<StudioService>> getAllServices() {
        List<StudioService> services = repository.findAll();
        return ResponseEntity.ok(services);
    }
}