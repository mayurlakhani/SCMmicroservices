package com.microservices.userAuthService.repository;



import com.microservices.userAuthService.model.Role;
import com.microservices.userAuthService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
