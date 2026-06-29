package com.dbinsightai.dbinsight_ai.service;

import com.dbinsightai.dbinsight_ai.dto.response.QueryHistoryResponse;

import java.util.List;

public interface QueryHistoryService {

    List<QueryHistoryResponse> getHistory();

}