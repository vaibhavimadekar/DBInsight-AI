package com.dbinsightai.dbinsight_ai.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConnectionResponse {

    private Long id;

    private String connectionName;

    private String host;

    private Integer port;

    private String databaseName;

    private String username;

    private LocalDateTime createdAt;
}