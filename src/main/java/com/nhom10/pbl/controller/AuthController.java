package com.nhom10.pbl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom10.pbl.payload.response.AuthenticationResponse;
import com.nhom10.pbl.payload.resquest.AuthenticationRequest;
import com.nhom10.pbl.payload.resquest.RegisterRequest;
import com.nhom10.pbl.security.service.AuthenticateService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticateService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        try {
            return ResponseEntity.ok(authenticationService.authenticate(request, response));
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }

}
