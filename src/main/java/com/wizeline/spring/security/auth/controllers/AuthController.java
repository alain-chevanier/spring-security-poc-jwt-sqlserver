package com.wizeline.spring.security.auth.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wizeline.spring.security.auth.models.ERole;
import com.wizeline.spring.security.auth.models.Role;
import com.wizeline.spring.security.auth.models.User;
import com.wizeline.spring.security.auth.payload.request.LoginRequest;
import com.wizeline.spring.security.auth.payload.request.SignupRequest;
import com.wizeline.spring.security.auth.payload.response.JwtResponse;
import com.wizeline.spring.security.auth.payload.response.MessageResponse;
import com.wizeline.spring.security.auth.repository.RoleRepository;
import com.wizeline.spring.security.auth.repository.UserRepository;
import com.wizeline.spring.security.auth.security.jwt.JwtUtils;
import com.wizeline.spring.security.auth.security.services.UserDetailsImpl;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (signUpRequest.getRole() == null || signUpRequest.getRole().isEmpty()) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found " + ERole.ROLE_ADMIN));
			roles.add(userRole);
		} else {
			signUpRequest.getRole().forEach(role -> {
				Role userRole = roleRepository.findByName(ERole.valueOf(role))
			 		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found " + role));
				roles.add(userRole);
		 	});
		}

		user.setRoles(roles);

		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
