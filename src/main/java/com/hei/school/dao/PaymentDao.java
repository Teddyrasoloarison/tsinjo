package com.hei.school.dao;

import com.hei.school.model.Payment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class PaymentDao {
  private final DataSource dataSource;

  public PaymentDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void save(Payment payment) throws SQLException {
    String sql =
        "INSERT INTO payment (id, external_id, state, amount, paymentMethod, paymentDate) VALUES"
            + " (?, ?, ?, ?, ?, ?)";
    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, payment.getId());
      stmt.setString(2, payment.getExternalId());
      stmt.setString(3, payment.getState().name());
      stmt.setBigDecimal(4, payment.getAmount());
      stmt.setString(5, payment.getPaymentMethod().name());
      stmt.setTimestamp(6, Timestamp.valueOf(String.valueOf(payment.getPaymentDate())));
      stmt.executeUpdate();
    }
  }

  public List<Payment> findAll() throws SQLException {
    List<Payment> list = new ArrayList<>();
    String sql =
        "SELECT id, external_id, state, amount, paymentMethod, paymentDate FROM payment ORDER BY"
            + " paymentDate DESC";
    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        String id = rs.getString("id");
        String externalId = rs.getString("external_id");

        // Récupérer enums depuis la colonne texte
        String stateStr = rs.getString("state");
        com.hei.school.model.PaymentState state =
            com.hei.school.model.PaymentState.valueOf(stateStr);

        java.math.BigDecimal amount = rs.getBigDecimal("amount");

        String methodStr = rs.getString("paymentMethod");
        com.hei.school.model.PaymentMethodes method =
            com.hei.school.model.PaymentMethodes.valueOf(methodStr);

        // Convertir SQL Timestamp -> Instant
        java.sql.Timestamp ts = rs.getTimestamp("paymentDate");
        java.time.Instant paymentInstant = ts != null ? ts.toInstant() : null;

        // Construire l'objet Payment (ici on passe un Instant)
        list.add(new Payment(id, externalId, state, amount, method, paymentInstant));
      }
    }
    return list;
  }
}
