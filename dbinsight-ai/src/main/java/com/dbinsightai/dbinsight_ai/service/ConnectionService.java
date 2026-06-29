package com.dbinsightai.dbinsight_ai.service;

import com.dbinsightai.dbinsight_ai.dto.request.ConnectionRequest;
import com.dbinsightai.dbinsight_ai.dto.response.ConnectionResponse;
import com.dbinsightai.dbinsight_ai.dto.response.ConnectionTestResponse;
import java.util.List;
import com.dbinsightai.dbinsight_ai.dto.request.ExecuteQueryRequest;
import com.dbinsightai.dbinsight_ai.dto.response.ExecuteQueryResponse;
public interface ConnectionService {

    ConnectionResponse saveConnection(ConnectionRequest request);

    ExecuteQueryResponse executeQuery(ExecuteQueryRequest request);

    List<ConnectionResponse> getAllConnections();

    ConnectionResponse getConnection(Long id);

    void deleteConnection(Long id);
}