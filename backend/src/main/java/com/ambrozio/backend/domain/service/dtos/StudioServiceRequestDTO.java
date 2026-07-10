package com.ambrozio.backend.domain.service.dtos;

import java.math.BigDecimal;

public record StudioServiceRequestDTO(String name, String description, BigDecimal price, Integer durationMinutes) {
}