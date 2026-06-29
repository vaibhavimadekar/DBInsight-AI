package com.dbinsightai.dbinsight_ai.controller;


import com.dbinsightai.dbinsight_ai.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {

        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .message("Application is running successfully")
                .data("Welcome to DBInsight AI 🚀")
                .build();

        return ResponseEntity.ok(response);
    }

}
