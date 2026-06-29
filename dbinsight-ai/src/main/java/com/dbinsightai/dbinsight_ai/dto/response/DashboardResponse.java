package com.dbinsightai.dbinsight_ai.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private Long totalQueries;

    private Double averageScore;

    private Long unsafeQueries;

    private Long totalConnections;

}