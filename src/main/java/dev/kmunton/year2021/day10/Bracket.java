package dev.kmunton.year2021.day10;

import java.util.Objects;

public class Bracket {

  private String open;
  private String close;

  public Bracket(String open, String close) {
    this.open = open;
    this.close = close;
  }

  public boolean isPair(String possibleClosing) {
    return possibleClosing == close;
  }

  public String getOpen() {
    return open;
  }

  public void setOpen(String open) {
    this.open = open;
  }

  public String getClose() {
    return close;
  }

  public void setClose(String close) {
    this.close = close;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Bracket bracket = (Bracket) o;
    return Objects.equals(open, bracket.open) && Objects.equals(close, bracket.close);
  }

  @Override
  public int hashCode() {
    return Objects.hash(open, close);
  }


}
