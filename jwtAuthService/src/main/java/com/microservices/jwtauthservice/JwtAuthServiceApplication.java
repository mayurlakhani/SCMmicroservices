package com.microservices.jwtauthservice;


import com.microservices.jwtauthservice.model.ERole;
import com.microservices.jwtauthservice.model.Role;
import com.microservices.jwtauthservice.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JwtAuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(JwtAuthServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(RoleRepository repository) {
        return (args) -> {
            repository.save(new Role(1L, ERole.USER));
            repository.save(new Role(2L, ERole.ADMIN));
            repository.save(new Role(3L, ERole.MODERATOR));
        };
    }
}
