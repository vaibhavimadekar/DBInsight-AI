package com.dbinsightai.dbinsight_ai.service;


import com.dbinsightai.dbinsight_ai.dto.request.LoginRequest;
import com.dbinsightai.dbinsight_ai.dto.request.RegisterRequest;
import com.dbinsightai.dbinsight_ai.dto.response.ApiResponse;
import com.dbinsightai.dbinsight_ai.dto.response.AuthResponse;

public interface AuthService {

    ApiResponse<String> register(RegisterRequest request);

    ApiResponse<AuthResponse> login(LoginRequest request);

}
