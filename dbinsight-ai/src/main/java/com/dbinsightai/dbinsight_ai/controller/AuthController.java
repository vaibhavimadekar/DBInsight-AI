package com.dbinsightai.dbinsight_ai.controller;


import com.dbinsightai.dbinsight_ai.dto.request.RegisterRequest;
import com.dbinsightai.dbinsight_ai.dto.response.ApiResponse;
import com.dbinsightai.dbinsight_ai.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.dbinsightai.dbinsight_ai.dto.request.LoginRequest;
import com.dbinsightai.dbinsight_ai.dto.response.AuthResponse;
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<String> register(
            @Valid @RequestBody RegisterRequest request) {

        return authService.register(request);

    }
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return authService.login(request);

    }

}