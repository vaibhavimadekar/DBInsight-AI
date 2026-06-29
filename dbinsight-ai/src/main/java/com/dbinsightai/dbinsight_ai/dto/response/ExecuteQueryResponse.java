package com.dbinsightai.dbinsight_ai.dto.response;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecuteQueryResponse {

    private boolean success;

    private String message;

    private List<Map<String, Object>> rows;

    private Integer affectedRows;

}