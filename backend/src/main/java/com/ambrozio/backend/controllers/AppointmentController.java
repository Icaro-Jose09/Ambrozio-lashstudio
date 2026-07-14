package com.ambrozio.backend.controllers;

import com.ambrozio.backend.domain.appointment.Appointment;
import com.ambrozio.backend.domain.appointment.dtos.AppointmentRequestDTO;
import com.ambrozio.backend.domain.appointment.dtos.AppointmentResponseDTO;
import com.ambrozio.backend.repositories.AppointmentRepository;
import com.ambrozio.backend.services.AppointmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService; // Injetamos o nosso cérebro aqui

    @Autowired
    private AppointmentRepository appointmentRepository;

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentRequestDTO data) {
        try {
            // Tentamos realizar o agendamento via Service
            Appointment newAppointment = appointmentService.create(data);
            return ResponseEntity.ok(new AppointmentResponseDTO(newAppointment));
        } catch (IllegalArgumentException e) {
            // Se o Service barrar alguma regra, devolvemos erro 400 (Bad Request) com a mensagem
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointments() {
        List<AppointmentResponseDTO> appointments = appointmentRepository.findAll().stream()
                .map(AppointmentResponseDTO::new)
                .toList();
                
         return ResponseEntity.ok(appointments);
        
    }

}

