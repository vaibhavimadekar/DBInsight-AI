package com.dbinsightai.dbinsight_ai.analysis.service.impl;

import com.dbinsightai.dbinsight_ai.analysis.dto.QueryAnalysisRequest;
import com.dbinsightai.dbinsight_ai.analysis.dto.QueryAnalysisResponse;
import com.dbinsightai.dbinsight_ai.analysis.service.SqlAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.delete.Delete;
import com.dbinsightai.dbinsight_ai.entity.QueryHistory;
import com.dbinsightai.dbinsight_ai.entity.User;
import com.dbinsightai.dbinsight_ai.repository.QueryHistoryRepository;
import com.dbinsightai.dbinsight_ai.util.SecurityUtil;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class SqlAnalysisServiceImpl implements SqlAnalysisService {
    private final QueryHistoryRepository queryHistoryRepository;
    private final SecurityUtil securityUtil;
    @Override
    public QueryAnalysisResponse analyze(QueryAnalysisRequest request) {

        String originalSql = request.getSqlQuery();

        Statement statement;

        try {
            statement = CCJSqlParserUtil.parse(originalSql);
        } catch (Exception e) {

            return QueryAnalysisResponse.builder()
                    .valid(false)
                    .queryType("INVALID")
                    .score(0)
                    .issues(java.util.List.of("Invalid SQL Syntax"))
                    .suggestions(java.util.List.of("Check SQL syntax"))
                    .build();
        }

        String sql = originalSql.toUpperCase();
        QueryAnalysisResponse.QueryAnalysisResponseBuilder response =
                QueryAnalysisResponse.builder()
                        .valid(true)
                        .score(100);

        java.util.List<String> issues = new java.util.ArrayList<>();
        java.util.List<String> suggestions = new java.util.ArrayList<>();

        if (statement instanceof Select) {
            response.queryType("SELECT");
        }
        else if (statement instanceof Update) {
            response.queryType("UPDATE");
        }
        else if (statement instanceof Delete) {
            response.queryType("DELETE");
        }
        else {
            response.queryType(statement.getClass().getSimpleName());
        }
        if (sql.contains("SELECT *")) {
            issues.add("Using SELECT *");
            suggestions.add("Select only required columns");
            response.score(response.build().getScore() - 20);
        }

        if (sql.startsWith("UPDATE") && !sql.contains("WHERE")) {
            issues.add("UPDATE without WHERE");
            suggestions.add("Add WHERE clause");
            response.score(response.build().getScore() - 40);
        }

        if (sql.startsWith("DELETE") && !sql.contains("WHERE")) {
            issues.add("DELETE without WHERE");
            suggestions.add("Add WHERE clause");
            response.score(response.build().getScore() - 50);
        }
        int joinCount = sql.split("JOIN", -1).length - 1;

        if (joinCount > 3) {
            issues.add("Too many JOIN operations");
            suggestions.add("Reduce JOINs or create views");
            response.score(response.build().getScore() - 20);
        }
        if (statement instanceof Select && !sql.contains("LIMIT")) {
            issues.add("LIMIT clause not found");
            suggestions.add("Use LIMIT for large result sets");
            response.score(response.build().getScore() - 10);
        }
        if (sql.contains("(SELECT")) {
            issues.add("Subquery detected");
            suggestions.add("Consider using JOIN if appropriate");
            response.score(response.build().getScore() - 10);
        }
        if (sql.contains("ORDER BY")) {
            issues.add("ORDER BY detected");
            suggestions.add("Ensure ORDER BY columns are indexed");
        }
        if (sql.contains("GROUP BY")) {
            issues.add("GROUP BY detected");
            suggestions.add("Index GROUP BY columns if frequently queried");
        }
        response.issues(issues);
        response.suggestions(suggestions);
        QueryAnalysisResponse result = response.build();

        User currentUser = securityUtil.getCurrentUser();

        QueryHistory history = QueryHistory.builder()
                .originalQuery(request.getSqlQuery())
                .queryType(result.getQueryType())
                .score(result.getScore())
                .issues(String.join(", ", result.getIssues()))
                .suggestions(String.join(", ", result.getSuggestions()))
                .createdAt(LocalDateTime.now())
                .user(currentUser)
                .build();

        queryHistoryRepository.save(history);

        return result;

    }
}