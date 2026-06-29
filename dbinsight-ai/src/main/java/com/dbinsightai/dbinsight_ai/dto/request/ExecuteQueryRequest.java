package com.dbinsightai.dbinsight_ai.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecuteQueryRequest {

    private Long connectionId;

    @NotBlank
    private String sql;

}