package dev.kmunton.year2021.day19;

import java.util.ArrayList;
import java.util.List;

public class Scanner {

  private int number;
  private List<Vector> beacons;

  public Scanner(int number, List<Vector> beacons) {
    this.number = number;
    this.beacons = beacons;
  }

  public Scanner(Scanner that) {
    this.number = that.getNumber();
    this.beacons = new ArrayList<>();
    for (Vector v : that.getBeacons()) {
      this.getBeacons().add(new Vector(v));
    }
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public List<Vector> getBeacons() {
    return beacons;
  }

  public void setBeacons(List<Vector> beacons) {
    this.beacons = beacons;
  }

  @Override
  public String toString() {
    return "Scanner{" +
        "number=" + number +
        ", beacons=" + beacons +
        '}';
  }
}
