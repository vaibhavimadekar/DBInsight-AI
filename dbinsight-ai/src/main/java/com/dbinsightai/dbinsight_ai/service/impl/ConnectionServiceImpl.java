package com.dbinsightai.dbinsight_ai.service.impl;

import com.dbinsightai.dbinsight_ai.dto.request.ConnectionRequest;
import com.dbinsightai.dbinsight_ai.dto.response.ConnectionResponse;
import com.dbinsightai.dbinsight_ai.entity.DbConnection;
import com.dbinsightai.dbinsight_ai.entity.User;
import com.dbinsightai.dbinsight_ai.repository.DbConnectionRepository;
import com.dbinsightai.dbinsight_ai.service.ConnectionService;
import com.dbinsightai.dbinsight_ai.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.dbinsightai.dbinsight_ai.dto.response.ConnectionTestResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.util.List;
import com.dbinsightai.dbinsight_ai.dto.request.ExecuteQueryRequest;
import com.dbinsightai.dbinsight_ai.dto.response.ExecuteQueryResponse;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {

    private final DbConnectionRepository dbConnectionRepository;
    private final SecurityUtil securityUtil;

    @Override
    public ConnectionResponse saveConnection(ConnectionRequest request) {

        User currentUser = securityUtil.getCurrentUser();

        // Create connection entity (not saved yet)
        DbConnection dbConnection = DbConnection.builder()
                .connectionName(request.getConnectionName())
                .host(request.getHost())
                .port(request.getPort())
                .databaseName(request.getDatabaseName())
                .username(request.getUsername())
                .encryptedPassword(request.getPassword()) // TODO: Encrypt later
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(currentUser)
                .build();

        // Build JDBC URL
        String url = "jdbc:postgresql://"
                + request.getHost()
                + ":"
                + request.getPort()
                + "/"
                + request.getDatabaseName();

        try (Connection jdbcConnection = DriverManager.getConnection(
                url,
                request.getUsername(),
                request.getPassword())) {

            // JDBC connection successful -> Save in application database
            DbConnection savedConnection = dbConnectionRepository.save(dbConnection);

            return ConnectionResponse.builder()
                    .id(savedConnection.getId())
                    .connectionName(savedConnection.getConnectionName())
                    .host(savedConnection.getHost())
                    .port(savedConnection.getPort())
                    .databaseName(savedConnection.getDatabaseName())
                    .username(savedConnection.getUsername())
                    .createdAt(savedConnection.getCreatedAt())
                    .build();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to connect to database: " + e.getMessage()
            );
        }
    }
    @Override
    public List<ConnectionResponse> getAllConnections() {

        User currentUser = securityUtil.getCurrentUser();

        return dbConnectionRepository.findByUser(currentUser)
                .stream()
                .map(connection -> ConnectionResponse.builder()
                        .id(connection.getId())
                        .connectionName(connection.getConnectionName())
                        .host(connection.getHost())
                        .port(connection.getPort())
                        .databaseName(connection.getDatabaseName())
                        .username(connection.getUsername())
                        .createdAt(connection.getCreatedAt())
                        .build())
                .toList();
    }
    @Override
    public ConnectionResponse getConnection(Long id) {

        User currentUser = securityUtil.getCurrentUser();

        DbConnection connection = dbConnectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Connection not found"));

        if (!connection.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Access Denied");
        }

        return ConnectionResponse.builder()
                .id(connection.getId())
                .connectionName(connection.getConnectionName())
                .host(connection.getHost())
                .port(connection.getPort())
                .databaseName(connection.getDatabaseName())
                .username(connection.getUsername())
                .createdAt(connection.getCreatedAt())
                .build();

    }
    @Override
    public void deleteConnection(Long id) {

        User currentUser = securityUtil.getCurrentUser();

        DbConnection connection = dbConnectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Connection not found"));

        if (!connection.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Access Denied");
        }

        dbConnectionRepository.delete(connection);
    }

    @Override
    public ExecuteQueryResponse executeQuery(ExecuteQueryRequest request) {

        User currentUser = securityUtil.getCurrentUser();

        DbConnection dbConnection = dbConnectionRepository.findById(request.getConnectionId())
                .orElseThrow(() -> new RuntimeException("Connection not found"));

        if (!dbConnection.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Access Denied");
        }

        String url = "jdbc:postgresql://"
                + dbConnection.getHost()
                + ":"
                + dbConnection.getPort()
                + "/"
                + dbConnection.getDatabaseName();

        try (Connection connection = DriverManager.getConnection(
                url,
                dbConnection.getUsername(),
                dbConnection.getEncryptedPassword())) {

            Statement statement = connection.createStatement();

            boolean hasResult = statement.execute(request.getSql());

            if (hasResult) {

                ResultSet rs = statement.getResultSet();

                ResultSetMetaData meta = rs.getMetaData();

                int columnCount = meta.getColumnCount();

                List<Map<String, Object>> rows = new ArrayList<>();

                while (rs.next()) {

                    Map<String, Object> row = new HashMap<>();

                    for (int i = 1; i <= columnCount; i++) {
                        row.put(meta.getColumnName(i), rs.getObject(i));
                    }

                    rows.add(row);
                }

                return ExecuteQueryResponse.builder()
                        .success(true)
                        .message("Query executed successfully")
                        .rows(rows)
                        .build();

            } else {

                int affected = statement.getUpdateCount();

                return ExecuteQueryResponse.builder()
                        .success(true)
                        .message("Query executed successfully")
                        .affectedRows(affected)
                        .build();
            }

        } catch (Exception e) {

            return ExecuteQueryResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
        }
    }
}