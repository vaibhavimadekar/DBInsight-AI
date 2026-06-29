package com.dbinsightai.dbinsight_ai.ai.service;

import com.dbinsightai.dbinsight_ai.ai.dto.GeminiRequest;
import com.dbinsightai.dbinsight_ai.ai.dto.GeminiResponse;

public interface GeminiService {

    GeminiResponse optimize(GeminiRequest request);

}