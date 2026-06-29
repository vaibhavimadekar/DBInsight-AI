package com.dbinsightai.dbinsight_ai.controller;

import com.dbinsightai.dbinsight_ai.dto.response.ApiResponse;
import com.dbinsightai.dbinsight_ai.dto.response.QueryHistoryResponse;
import com.dbinsightai.dbinsight_ai.service.QueryHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class QueryHistoryController {

    private final QueryHistoryService service;

    @GetMapping
    public ApiResponse<List<QueryHistoryResponse>> getHistory() {

        return ApiResponse.<List<QueryHistoryResponse>>builder()
                .success(true)
                .message("History fetched successfully")
                .data(service.getHistory())
                .build();
    }
}