package com.ambrozio.backend.services;

import com.ambrozio.backend.domain.appointment.Appointment;
import com.ambrozio.backend.domain.appointment.AppointmentStatus;
import com.ambrozio.backend.domain.appointment.dtos.AppointmentRequestDTO;
import com.ambrozio.backend.domain.service.StudioService;
import com.ambrozio.backend.domain.user.User;
import com.ambrozio.backend.repositories.AppointmentRepository;
import com.ambrozio.backend.repositories.StudioServiceRepository;
import com.ambrozio.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudioServiceRepository studioServiceRepository;

    public Appointment create(AppointmentRequestDTO data) {
        if (data.appointmentDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível realizar agendamentos em datas passadas.");
        }

        int hour = data.appointmentDate().getHour();
        if (hour < 9 || hour > 18) {
            throw new IllegalArgumentException("O horário de funcionamento é das 09:00 às 18:00.");
        }

        if (appointmentRepository.existsByAppointmentDate(data.appointmentDate())) {
            throw new IllegalArgumentException("Este horário já está reservado. Por favor, escolha outro.");
        }

        User client = userRepository.findById(data.clientId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        StudioService service = studioServiceRepository.findById(data.serviceId())
                .orElseThrow(() -> new RuntimeException("Serviço do estúdio não encontrado"));

        Appointment newAppointment = new Appointment(client, service, data.appointmentDate());
        return appointmentRepository.save(newAppointment);
    }

    public Appointment updateStatus(UUID id, AppointmentStatus newStatus) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado."));

        appointment.setStatus(newStatus);
        
        return appointmentRepository.save(appointment);
    }
}