package com.dbinsightai.dbinsight_ai.analysis.controller;

import com.dbinsightai.dbinsight_ai.analysis.dto.QueryAnalysisRequest;
import com.dbinsightai.dbinsight_ai.analysis.dto.QueryAnalysisResponse;
import com.dbinsightai.dbinsight_ai.analysis.service.SqlAnalysisService;
import com.dbinsightai.dbinsight_ai.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sql")
@RequiredArgsConstructor
public class SqlAnalysisController {

    private final SqlAnalysisService sqlAnalysisService;

    @PostMapping("/analyze")
    public ApiResponse<QueryAnalysisResponse> analyze(
            @Valid @RequestBody QueryAnalysisRequest request) {

        return ApiResponse.<QueryAnalysisResponse>builder()
                .success(true)
                .message("SQL analyzed successfully")
                .data(sqlAnalysisService.analyze(request))
                .build();
    }
}