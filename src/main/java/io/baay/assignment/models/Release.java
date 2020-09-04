package io.baay.assignment.models;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
public class Release {
  private String announceDate;
  private int priceEur;

  public String getAnnounceDate() {
    return announceDate;
  }

  public void setAnnounceDate(String announceDate) {
    this.announceDate = announceDate;
  }

  public int getPriceEur() {
    return priceEur;
  }

  public void setPriceEur(int priceEur) {
    this.priceEur = priceEur;
  }

  @Override
  public String toString() {
    return "Release{"
        + "announceDate='"
        + announceDate
        + '\''
        + ", priceEur='"
        + priceEur
        + '\''
        + '}';
  }
}
