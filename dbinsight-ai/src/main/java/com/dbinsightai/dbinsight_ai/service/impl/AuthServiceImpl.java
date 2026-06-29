package com.dbinsightai.dbinsight_ai.service.impl;

import com.dbinsightai.dbinsight_ai.dto.request.LoginRequest;
import com.dbinsightai.dbinsight_ai.dto.request.RegisterRequest;
import com.dbinsightai.dbinsight_ai.dto.response.ApiResponse;
import com.dbinsightai.dbinsight_ai.dto.response.AuthResponse;
import com.dbinsightai.dbinsight_ai.entity.Role;
import com.dbinsightai.dbinsight_ai.entity.User;
import com.dbinsightai.dbinsight_ai.repository.RoleRepository;
import com.dbinsightai.dbinsight_ai.repository.UserRepository;
import com.dbinsightai.dbinsight_ai.security.jwt.JwtService;
import com.dbinsightai.dbinsight_ai.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public ApiResponse<String> register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return ApiResponse.<String>builder()
                    .success(false)
                    .message("Email already registered")
                    .build();
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("USER role not found"));

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return ApiResponse.<String>builder()
                .success(true)
                .message("User registered successfully")
                .data(user.getEmail())
                .build();
    }

    @Override
    public ApiResponse<AuthResponse> login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User dbUser = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.builder()
                        .username(dbUser.getEmail())
                        .password(dbUser.getPassword())
                        .roles(dbUser.getRole().getName())
                        .build();

        String token = jwtService.generateToken(userDetails);

        AuthResponse response = AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .email(dbUser.getEmail())
                .role(dbUser.getRole().getName())
                .build();

        return ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Login Successful")
                .data(response)
                .build();
    }
}