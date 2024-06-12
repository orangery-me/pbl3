package com.nhom10.pbl.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nhom10.pbl.models.ERole;
import com.nhom10.pbl.models.Role;
import com.nhom10.pbl.models.UserModel;
import com.nhom10.pbl.payload.response.AuthenticationResponse;
import com.nhom10.pbl.payload.response.UserResponse;
import com.nhom10.pbl.payload.request.AuthenticationRequest;
import com.nhom10.pbl.payload.request.RegisterRequest;
import com.nhom10.pbl.repository.RoleRepository;
import com.nhom10.pbl.repository.UserRepository;
import com.nhom10.pbl.security.jwt.JWTService;
import com.nhom10.pbl.services.DoctorServices;
import com.nhom10.pbl.services.PatientService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    @Autowired
    private DoctorServices doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    public Boolean comparePassword(String inputPassword, String encodedPassword) {
        return passwordEncoder.matches(inputPassword, encodedPassword);
    }

    public UserResponse getUserFromCookie(HttpServletRequest request) throws UsernameNotFoundException {
        String username = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accessToken")) {
                    username = jwtService.extractUserName(cookie.getValue());
                }
            }
        }

        if(username == null) return  UserResponse.mapToUserResponse(null);
        
        var user = userRepository.findByUserName(username).orElseThrow();
        return UserResponse.mapToUserResponse(user);
    }

    public AuthenticationResponse register(RegisterRequest request) {
        // get roles from request
        String strRole = request.getRole();
        final Role role;

        if (strRole == null) {
            Role patient = roleRepository.findByName(ERole.PATIENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            role = patient;
        } else {

            switch (strRole) {
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
                .fullname(request.getFullname())
                .birthday(request.getBirthday())
                .gender(request.getGender())
                .enabled(true)
                .role(role)
                .build();

        userRepository.save(user);
        if (role.getName().equals(ERole.PATIENT)) {
            patientService.createNewPatient(user);
        } else if (role.getName().equals(ERole.DOCTOR)) {
            doctorService.createNewDoctor(user);
        }

        var token = jwtService.generateToken(CustomUserDetails.build(user));

        return AuthenticationResponse.builder().token(token).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        if (authentication.isAuthenticated()) {
            // user is authenticated
            var user = userRepository.findByUserName(request.getUsername()).orElseThrow();
            var token = jwtService.generateToken(CustomUserDetails.build(user));
            ResponseCookie cookie = ResponseCookie.from("accessToken", token).httpOnly(true).maxAge(3600).path("/")
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return AuthenticationResponse.builder().token(token).build();
        } else {
            throw new UsernameNotFoundException("Authentication failed");
        }
    }
}
