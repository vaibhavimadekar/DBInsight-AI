package com.dbinsightai.dbinsight_ai.ai.controller;

import com.dbinsightai.dbinsight_ai.ai.dto.GeminiRequest;
import com.dbinsightai.dbinsight_ai.ai.dto.GeminiResponse;
import com.dbinsightai.dbinsight_ai.ai.service.GeminiService;
import com.dbinsightai.dbinsight_ai.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class GeminiController {

    private final GeminiService geminiService;

    @PostMapping("/optimize")
    public ApiResponse<GeminiResponse> optimize(
            @RequestBody GeminiRequest request) {

        return ApiResponse.<GeminiResponse>builder()
                .success(true)
                .message("Optimization completed")
                .data(geminiService.optimize(request))
                .build();
    }
}