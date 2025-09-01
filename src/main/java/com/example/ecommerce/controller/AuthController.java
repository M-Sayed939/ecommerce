package com.example.ecommerce.controller;

import com.example.ecommerce.dto.JwtAuthenticationResponse;
import com.example.ecommerce.dto.LogInRequest;
import com.example.ecommerce.dto.SignUpRequest;
import com.example.ecommerce.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        authenticationService.signup( signUpRequest );
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LogInRequest logInRequest) {
        String token = authenticationService.login( logInRequest );
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

}
