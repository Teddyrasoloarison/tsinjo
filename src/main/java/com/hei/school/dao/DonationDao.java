package com.hei.school.dao;

import com.hei.school.model.Donation;
import com.hei.school.model.Donor;
import com.hei.school.model.Payment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class DonationDao {
  private final DataSource dataSource;

  public DonationDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void save(Donation donation) throws SQLException {
    String sql = "INSERT INTO donation (id, donor_id, payment_id, created_at) VALUES (?, ?, ?)";
    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, donation.getId());
      stmt.setString(2, donation.getDonor().getDonorId());
      stmt.setString(3, donation.getPayment().getId());
      stmt.executeUpdate();
    }
  }

  public List<Donation> findAll() throws SQLException {
    List<Donation> list = new ArrayList<>();
    String sql =
        "SELECT id, donor_id, payment_id, created_at FROM donation ORDER BY created_at DESC";
    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {
      while (rs.next()) {
        String id = rs.getString("id");
        String donorId = rs.getString("donor_id");
        String paymentId = rs.getString("payment_id");

        // construire des objets id-only
        Donor donor = new Donor(donorId);
        Payment payment = new Payment(paymentId);

        // récupérer created_at (peut être null)
        java.sql.Timestamp ts = rs.getTimestamp("created_at");
        java.time.LocalDateTime createdAt = ts != null ? ts.toLocalDateTime() : null;

        // construire la Donation : utilise le constructeur qui accepte createdAt
        Donation donation = new Donation(id, donor, payment);

        list.add(donation);
      }
    }
    return list;
  }
}
