package com.ambrozio.backend.repositories;

import com.ambrozio.backend.domain.service.StudioService;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface StudioServiceRepository extends JpaRepository<StudioService, UUID> {
}