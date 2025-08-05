package com.hei.school.dao;

import com.hei.school.model.Help;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class HelpDao {
  private final DataSource dataSource;

  public HelpDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void save(Help help) throws SQLException {
    String sql =
        "INSERT INTO help (id, beneficiary_id, payment_id, accident_description, created_at) VALUES"
            + " (?, ?, ?, ?)";
    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, help.getId());
      stmt.setString(2, help.getBeneficiary().getId());
      stmt.setString(3, help.getPayment().getId());
      stmt.setString(4, help.getAccidentDescription());
      stmt.executeUpdate();
    }
  }

  public List<Help> findAll() throws SQLException {
    List<Help> list = new ArrayList<>();
    String sql =
        "SELECT id, beneficiary_id, payment_id, accident_description, created_at FROM help ORDER BY"
            + " created_at DESC";
    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {
      while (rs.next()) {
        list.add(
            new Help(
                rs.getString("id"),
                rs.getString("beneficiary_id"),
                rs.getString("payment_id"),
                rs.getString("accident_description"),
                rs.getTimestamp("created_at").toLocalDateTime()));
      }
    }
    return list;
  }
}
