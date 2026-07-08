package com.ambrozio.backend.domain.user.dtos;

import com.ambrozio.backend.domain.user.UserRole;

public record RegisterDTO(String name, String email, String password, UserRole role) {
}