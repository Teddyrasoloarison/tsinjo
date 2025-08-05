package com.hei.school.model;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Payment {
  private String id;
  private String externalId;
  private PaymentState state;
  private BigDecimal amount;
  private PaymentMethodes paymentMethod;
  private Instant paymentDate;

  public Payment(String paymentId) {}
}
