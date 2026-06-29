package com.dbinsightai.dbinsight_ai.analysis.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryAnalysisResponse {

    private boolean valid;

    private String queryType;

    private Integer score;

    private List<String> issues;

    private List<String> suggestions;

}