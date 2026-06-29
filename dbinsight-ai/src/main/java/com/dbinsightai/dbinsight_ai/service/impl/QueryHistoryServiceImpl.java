package com.dbinsightai.dbinsight_ai.service.impl;

import com.dbinsightai.dbinsight_ai.dto.response.QueryHistoryResponse;
import com.dbinsightai.dbinsight_ai.entity.User;
import com.dbinsightai.dbinsight_ai.repository.QueryHistoryRepository;
import com.dbinsightai.dbinsight_ai.service.QueryHistoryService;
import com.dbinsightai.dbinsight_ai.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryHistoryServiceImpl implements QueryHistoryService {

    private final QueryHistoryRepository repository;

    private final SecurityUtil securityUtil;

    @Override
    public List<QueryHistoryResponse> getHistory() {

        User user = securityUtil.getCurrentUser();

        return repository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(history -> QueryHistoryResponse.builder()
                        .id(history.getId())
                        .originalQuery(history.getOriginalQuery())
                        .queryType(history.getQueryType())
                        .score(history.getScore())
                        .issues(history.getIssues())
                        .suggestions(history.getSuggestions())
                        .createdAt(history.getCreatedAt())
                        .build())
                .toList();
    }
}