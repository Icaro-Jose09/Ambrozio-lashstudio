package com.ambrozio.backend.domain.appointment.dtos;

import com.ambrozio.backend.domain.appointment.Appointment;
import com.ambrozio.backend.domain.appointment.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentResponseDTO(
        UUID id,
        String clientName,
        String serviceName,
        LocalDateTime appointmentDate,
        AppointmentStatus status
) {
    // Construtor mágico: recebe a entidade inteira e extrai só o que é seguro
    public AppointmentResponseDTO(Appointment appointment) {
        this(
                appointment.getId(),
                appointment.getClient().getName(),
                appointment.getService().getName(),
                appointment.getAppointmentDate(),
                appointment.getStatus()
        );
    }
}