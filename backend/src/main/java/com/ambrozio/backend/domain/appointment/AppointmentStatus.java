package com.ambrozio.backend.domain.appointment;

public enum AppointmentStatus {
    PENDING,    // Aguardando confirmação do estúdio
    CONFIRMED,  // Confirmado pelo estúdio
    COMPLETED,  // Serviço realizado
    CANCELED    // Cancelado pelo cliente ou estúdio
}