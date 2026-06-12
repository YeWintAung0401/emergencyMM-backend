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
                    row.put("isActive",  rs.getObject("isActive"));// My SQL
//                    SQL Server
//                    row.put("isActive",  rs.getInt("isActive"));
                    return row;
                }
        );

        String countSql = "SELECT COUNT(*) FROM Users " + where;
        Integer count = jdbcTemplate.queryForObject(countSql, Integer.class, params.toArray());

        result.put("userList",  list);
        result.put("userCount", count != null ? count : 0);
        return result;
    }



    // ── GET RECOMMENDATIONS ──────────────────────────────────────────────
    public HashMap<String, Object> getRecommendations(HashMap<String, Object> data) {
        HashMap<String, Object> result = new HashMap<>();
        List<Object> params = new ArrayList<>();

        // ── Main query ────────────────────────────────────────────────────
        String sql = "SELECT recommendation_id, recommender_name, position_title, " +
                "company_name, recommendation_text, rating " +
                "FROM recommendations ";

        List<HashMap<String, Object>> list = jdbcTemplate.query(
                sql,
                params.toArray(),
                (rs, rowNum) -> {
                    HashMap<String, Object> row = new HashMap<>();
                    row.put("recommendationId",   rs.getInt("recommendation_id"));
                    row.put("recommenderName",    rs.getString("recommender_name"));
                    row.put("positionTitle",      rs.getString("position_title"));
                    row.put("companyName",        rs.getString("company_name"));
                    row.put("recommendationText", rs.getString("recommendation_text"));
                    row.put("rating",             rs.getInt("rating"));
                    return row;
                }
        );

        // ── Count query — same table and filters ──────────────────────────
        String countSql = "SELECT COUNT(*) FROM recommendations ";
        Integer count = jdbcTemplate.queryForObject(countSql, Integer.class, params.toArray());

        result.put("recommendationList",  list);
        result.put("recommendationCount", count != null ? count : 0);
        return result;
    }

    // ── INSERT RECOMMENDATION ────────────────────────────────────────────
    public HashMap<String, Object> insertRecommendation(HashMap<String, Object> data) {
        HashMap<String, Object> result = new HashMap<>();

        String sql = "INSERT INTO recommendations (recommender_name, position_title, company_name, recommendation_text, rating) " +
                "VALUES (?, ?, ?, ?, ?)";

        int rows = jdbcTemplate.update(sql,
                data.get("recommenderName"),
                data.get("positionTitle"),
                data.get("companyName"),
                data.get("recommendationText"),
                data.get("rating")
        );

        if (rows > 0) {
            result.put("status",  "success");
            result.put("message", "Recommendation inserted successfully");
        } else {
            result.put("status",  "error");
            result.put("message", "Insert failed");
        }
        return result;
    }

    // ── UPDATE RECOMMENDATION ────────────────────────────────────────────
    public HashMap<String, Object> updateRecommendation(HashMap<String, Object> data) {
        HashMap<String, Object> result = new HashMap<>();

        String sql = "UPDATE recommendations SET recommender_name = ?, position_title = ?, " +
                "company_name = ?, recommendation_text = ?, rating = ? " +
                "WHERE recommendation_id = ?";

        int rows = jdbcTemplate.update(sql,
                data.get("recommenderName"),
                data.get("positionTitle"),
                data.get("companyName"),
                data.get("recommendationText"),
                data.get("rating"),
                data.get("recommendationId")
        );

        if (rows > 0) {
            result.put("status",  "success");
            result.put("message", "Recommendation updated successfully");
        } else {
            result.put("status",  "error");
            result.put("message", "Update failed or ID not found");
        }
        return result;
    }

    // ── DELETE RECOMMENDATION ────────────────────────────────────────────
    public HashMap<String, Object> deleteRecommendation(int id) {
        HashMap<String, Object> result = new HashMap<>();

        String sql = "DELETE FROM recommendations WHERE recommendation_id = ?";

        int rows = jdbcTemplate.update(sql, id);

        if (rows > 0) {
            result.put("status",  "success");
            result.put("message", "Recommendation deleted successfully");
        } else {
            result.put("status",  "error");
            result.put("message", "Delete failed or ID not found");
        }
        return result;
    }


}

