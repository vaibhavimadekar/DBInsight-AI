package com.dbinsightai.dbinsight_ai.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryHistoryResponse {

    private Long id;

    private String originalQuery;

    private String queryType;

    private Integer score;

    private String issues;

    private String suggestions;

    private LocalDateTime createdAt;

}