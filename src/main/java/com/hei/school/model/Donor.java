package com.hei.school.model;

import lombok.Getter;

@Getter
public class Donor {
  private String donorId;
  private String fullName;
  private String email;

  public Donor(String donorId, String fullName, String email) {
    this.donorId = donorId;
    this.fullName = fullName;
    this.email = email;
  }

  public Donor(String donorId) {}
}
