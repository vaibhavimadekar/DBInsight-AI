package com.dbinsightai.dbinsight_ai.analysis.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryAnalysisRequest {

    @NotBlank
    private String sqlQuery;

}