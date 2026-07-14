package com.ambrozio.backend.repositories;

import com.ambrozio.backend.domain.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    
    // O Spring cria o SQL automaticamente só de lermos o nome desse método!
    boolean existsByAppointmentDate(LocalDateTime appointmentDate);
}