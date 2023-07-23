package com.microservices.jwtauthservice.repository;

import com.microservices.jwtauthservice.model.ERole;
import com.microservices.jwtauthservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
