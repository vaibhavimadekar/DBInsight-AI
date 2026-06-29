package com.dbinsightai.dbinsight_ai.controller;
import com.dbinsightai.dbinsight_ai.dto.response.ConnectionTestResponse;
import com.dbinsightai.dbinsight_ai.dto.request.ConnectionRequest;
import com.dbinsightai.dbinsight_ai.dto.response.ApiResponse;
import com.dbinsightai.dbinsight_ai.dto.response.ConnectionResponse;
import com.dbinsightai.dbinsight_ai.service.ConnectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.dbinsightai.dbinsight_ai.dto.request.ExecuteQueryRequest;
import com.dbinsightai.dbinsight_ai.dto.response.ExecuteQueryResponse;
@RestController
@RequestMapping("/api/connections")
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionService connectionService;

    @PostMapping
    public ApiResponse<ConnectionResponse> saveConnection(
            @Valid @RequestBody ConnectionRequest request) {

        ConnectionResponse response =
                connectionService.saveConnection(request);

        return ApiResponse.<ConnectionResponse>builder()
                .success(true)
                .message("Database connection saved successfully")
                .data(response)
                .build();
    }
    @GetMapping
    public ApiResponse<List<ConnectionResponse>> getAllConnections() {

        return ApiResponse.<List<ConnectionResponse>>builder()
                .success(true)
                .message("Connections fetched successfully")
                .data(connectionService.getAllConnections())
                .build();
    }
    @GetMapping("/{id}")
    public ApiResponse<ConnectionResponse> getConnection(
            @PathVariable Long id) {

        return ApiResponse.<ConnectionResponse>builder()
                .success(true)
                .message("Connection fetched successfully")
                .data(connectionService.getConnection(id))
                .build();
    }
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteConnection(
            @PathVariable Long id) {

        connectionService.deleteConnection(id);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Connection deleted successfully")
                .data("Deleted")
                .build();
    }

    @PostMapping("/execute")
    public ApiResponse<ExecuteQueryResponse> executeQuery(
            @Valid @RequestBody ExecuteQueryRequest request) {

        ExecuteQueryResponse response =
                connectionService.executeQuery(request);

        return ApiResponse.<ExecuteQueryResponse>builder()
                .success(response.isSuccess())
                .message(response.getMessage())
                .data(response)
                .build();
    }
}