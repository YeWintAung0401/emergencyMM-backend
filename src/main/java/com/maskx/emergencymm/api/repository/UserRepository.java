package com.maskx.emergencymm.api.repository;

import com.maskx.emergencymm.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class UserRepository {


    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ── GET ALL USERS ────────────────────────────────────────────────────
    public HashMap<String, Object> getUser(HashMap<String, Object> data) {
        HashMap<String, Object> result = new HashMap<>();
        List<Object> params = new ArrayList<>();

        StringBuilder where = new StringBuilder("WHERE 1=1 ");

        if (data.get("searchtxt") != null && !data.get("searchtxt").toString().isEmpty()) {
            where.append("AND username LIKE ? ");
            params.add("%" + data.get("searchtxt") + "%");
        }

        if (data.get("isActive") != null && !data.get("isActive").toString().isEmpty()) {
            where.append("AND isActive = ? ");
            params.add(data.get("isActive"));
        }

        if (data.get("age") != null && !data.get("age").toString().isEmpty()) {
            where.append("AND age = ? ");
            params.add(data.get("age"));
        }

        String sql = "SELECT userId, username, email, phone, age, createdAt, isActive " +
                "FROM Users " + where;

        List<HashMap<String, Object>> list = jdbcTemplate.query(
                sql,
                params.toArray(),
                (rs, rowNum) -> {
                    HashMap<String, Object> row = new HashMap<>();
                    row.put("userId",    rs.getInt("userId"));
                    row.put("username",  rs.getString("username"));
                    row.put("email",     rs.getString("email"));
                    row.put("phone",     rs.getString("phone"));
                    row.put("age",       rs.getInt("age"));
                    row.put("createdAt", rs.getString("createdAt"));
                    row.put("isActive",  rs.getObject("isActive"));
                    return row;
                }
        );

        String countSql = "SELECT COUNT(*) FROM Users " + where;
        Integer count = jdbcTemplate.queryForObject(countSql, Integer.class, params.toArray());

        result.put("userList",  list);
        result.put("userCount", count != null ? count : 0);
        return result;
    }


}

