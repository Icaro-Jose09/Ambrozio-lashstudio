package com.ambrozio.backend.repositories;

import com.ambrozio.backend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
    // Método mágico do Spring Data! Ele cria a query SQL sozinho apenas lendo o nome do método.
    Optional<User> findByEmail(String email);
    
}
