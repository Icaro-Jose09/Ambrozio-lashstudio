package com.ambrozio.backend.controllers;

import com.ambrozio.backend.domain.appointment.Appointment;
import com.ambrozio.backend.domain.appointment.dtos.AppointmentRequestDTO;
import com.ambrozio.backend.domain.appointment.dtos.AppointmentResponseDTO;
import com.ambrozio.backend.domain.service.StudioService;
import com.ambrozio.backend.domain.user.User;
import com.ambrozio.backend.repositories.AppointmentRepository;
import com.ambrozio.backend.repositories.StudioServiceRepository;
import com.ambrozio.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudioServiceRepository serviceRepository;

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@RequestBody AppointmentRequestDTO data) {
        User client = userRepository.findById(data.clientId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        StudioService service = serviceRepository.findById(data.serviceId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        Appointment newAppointment = new Appointment(client, service, data.appointmentDate());
        appointmentRepository.save(newAppointment);
        
        // Retorna apenas a máscara limpa e segura
        return ResponseEntity.ok(new AppointmentResponseDTO(newAppointment));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointments() {
        // Converte a lista de entidades na lista de DTOs seguros
        List<AppointmentResponseDTO> appointments = appointmentRepository.findAll().stream()
                .map(AppointmentResponseDTO::new)
                .toList();
                
        return ResponseEntity.ok(appointments);
    }
}