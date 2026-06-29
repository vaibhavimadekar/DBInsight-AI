package com.dbinsightai.dbinsight_ai.ai.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeminiResponse {

    private String optimizedQuery;

    private String explanation;

}