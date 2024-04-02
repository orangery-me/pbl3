package com.nhom10.pbl.security.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nhom10.pbl.models.ERole;
import com.nhom10.pbl.models.Role;
import com.nhom10.pbl.models.UserModel;
import com.nhom10.pbl.payload.response.AuthenticationResponse;
import com.nhom10.pbl.payload.resquest.AuthenticationRequest;
import com.nhom10.pbl.payload.resquest.RegisterRequest;
import com.nhom10.pbl.repository.RoleRepository;
import com.nhom10.pbl.repository.UserRepository;
import com.nhom10.pbl.security.jwt.JWTService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticateService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        // get roles from request
        String strRoles = request.getRole();
        final Role role;

        if (strRoles == null) {
            Role patient = roleRepository.findByName(ERole.PATIENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            role = patient;
        } else {

            switch (strRoles) {
                case "ADMIN":
                    Role admin = roleRepository.findByName(ERole.ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    role = admin;

                    break;
                case "DOCTOR":
                    Role doctor = roleRepository.findByName(ERole.DOCTOR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    role = doctor;

                    break;
                default:
                    Role patient = roleRepository.findByName(ERole.PATIENT)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    role = patient;
            }
        }

        UserModel user = UserModel.builder()
                .userName(request.getUsername())
                .passWord(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .telephone(request.getPhone())
                .fullName(request.getFullname())
                .enabled(true)
                .roles(new HashSet<Role>() {
                    {
                        add(role);
                    }

                })
                .build();

        userRepository.save(user);
        var token = jwtService.generateToken(CustomUserDetails.build(user));

        return AuthenticationResponse.builder().token(token).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        // user is authenticated
        var user = userRepository.findByUserName(request.getUsername()).orElseThrow();
        var token = jwtService.generateToken(CustomUserDetails.build(user));

        return AuthenticationResponse.builder().token(token).build();
    }

}
