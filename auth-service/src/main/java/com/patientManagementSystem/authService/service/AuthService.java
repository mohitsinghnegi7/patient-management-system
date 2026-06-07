package com.patientManagementSystem.authService.service;

import com.patientManagementSystem.authService.dto.LoginRequestDTO;
import com.patientManagementSystem.authService.dto.LoginResponseDTO;
import com.patientManagementSystem.authService.model.User;
import com.patientManagementSystem.authService.repository.UserRepository;
import com.patientManagementSystem.authService.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO){
        Optional<String> token  = userService.findByEmail(loginRequestDTO.getEmail())
                .filter(u -> passwordEncoder.matches(loginRequestDTO.getPassword(), u.getPassword()))
                .map(u->jwtUtil.generateToken(u.getEmail(), u.getRole()));

        return token;
    }

    public Boolean validateToken(String token){
        try{
            jwtUtil.validateToken(token);
            return true;
        }
        catch (JwtException e){
            return false;
        }
    }
}
