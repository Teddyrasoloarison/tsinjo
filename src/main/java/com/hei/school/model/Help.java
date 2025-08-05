package com.hei.school.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Help {
  private String id;
  private Beneficiary beneficiary;
  private Payment payment;
  private String accidentDescription;
  private Instant createdAt;
}
