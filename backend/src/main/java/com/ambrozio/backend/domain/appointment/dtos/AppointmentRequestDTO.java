package com.ambrozio.backend.domain.appointment.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentRequestDTO(UUID clientId, UUID serviceId, LocalDateTime appointmentDate) {
}