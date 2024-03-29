package com.wizeline.spring.security.auth.payload.request;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

import javax.validation.constraints.*;

@Data
@Builder
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
