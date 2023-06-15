package com.wizeline.spring.security.auth.payload.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;
}
