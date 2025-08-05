package com.hei.school.dao;

import com.hei.school.model.Beneficiary;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class BenificiaryDao {
  private final DataSource dataSource;

  public BenificiaryDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void save(Beneficiary beneficiary) throws SQLException {
    String sql = "INSERT INTO beneficiary (id, full_name, email) VALUES (?, ?, ?)";
    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, beneficiary.getId());
      stmt.setString(2, beneficiary.getFullName());
      stmt.setString(3, beneficiary.getEmail());
      stmt.executeUpdate();
    }
  }

  public List<Beneficiary> findAll() throws SQLException {
    List<Beneficiary> list = new ArrayList<>();
    String sql = "SELECT id, full_name, email FROM beneficiary ORDER BY full_name DESC";
    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {
      while (rs.next()) {
        list.add(
            new Beneficiary(rs.getString("id"), rs.getString("full_name"), rs.getString("email")));
      }
    }
    return list;
  }
}
