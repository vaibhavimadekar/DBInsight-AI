package com.dbinsightai.dbinsight_ai.repository;

import com.dbinsightai.dbinsight_ai.entity.QueryHistory;
import com.dbinsightai.dbinsight_ai.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QueryHistoryRepository extends JpaRepository<QueryHistory, Long> {

    List<QueryHistory> findByUserOrderByCreatedAtDesc(User user);

}