package com.ambrozio.backend.repositories;

import com.ambrozio.backend.domain.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
}