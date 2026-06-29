package com.dbinsightai.dbinsight_ai.repository;

import com.dbinsightai.dbinsight_ai.entity.DbConnection;
import com.dbinsightai.dbinsight_ai.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DbConnectionRepository extends JpaRepository<DbConnection, Long> {

    List<DbConnection> findByUser(User user);

}