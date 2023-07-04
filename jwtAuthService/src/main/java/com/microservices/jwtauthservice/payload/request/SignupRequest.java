package com.microservices.jwtauthservice.dto;
import lombok.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignupRequest {
    private String username;
    private String password;
    private  String email;
    private Set<String> role;


}
