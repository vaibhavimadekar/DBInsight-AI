package com.dbinsightai.dbinsight_ai.analysis.service;

import com.dbinsightai.dbinsight_ai.analysis.dto.QueryAnalysisRequest;
import com.dbinsightai.dbinsight_ai.analysis.dto.QueryAnalysisResponse;

public interface SqlAnalysisService {

    QueryAnalysisResponse analyze(QueryAnalysisRequest request);

}