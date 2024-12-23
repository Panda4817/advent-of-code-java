package dev.kmunton.year2023.day19;

public class Part {

  private final long x;
  private final long m;
  private final long a;
  private final long s;
  private boolean accepted = false;
  private boolean rejected = false;

  public Part(long x, long m, long a, long s) {
    this.x = x;
    this.m = m;
    this.a = a;
    this.s = s;
  }

  public long getX() {
    return x;
  }

  public long getM() {
    return m;
  }

  public long getA() {
    return a;
  }

  public long getS() {
    return s;
  }

  public boolean isAccepted() {
    return accepted;
  }

  public boolean isRejected() {
    return rejected;
  }

  public void setAccepted(boolean accepted) {
    this.accepted = accepted;
  }

  public void setRejected(boolean rejected) {
    this.rejected = rejected;
  }

  public Long getValueGivenletter(String letter) {
    switch (letter) {
      case "x":
        return x;
      case "m":
        return m;
      case "a":
        return a;
      case "s":
        return s;
      default:
        return null;
    }
  }

  public long getRating() {
    return x + a + m + s;
  }
}
