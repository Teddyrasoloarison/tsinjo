package com.hei.school.dao;

import com.hei.school.model.Donor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class DonorDao {
  private final DataSource dataSource;

  public DonorDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void save(Donor donor) throws SQLException {
    String sql = "INSERT INTO donor (id, full_name, email) VALUES (?, ?, ?)";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {

      stmt.setString(1, donor.id());
      stmt.setString(2, donor.fullName());
      stmt.setString(3, donor.email());

      stmt.executeUpdate();
    }
  }

  public List<Donor> findAll() throws SQLException {
    List<Donor> donors = new ArrayList<>();
    String sql = "SELECT id, full_name, email FROM donor ORDER BY full_name";

    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        donors.add(new Donor(rs.getString("id"), rs.getString("full_name"), rs.getString("email")));
      }
    }
    return donors;
  }
}
