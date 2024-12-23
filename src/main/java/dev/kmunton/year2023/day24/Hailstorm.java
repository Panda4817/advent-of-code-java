package dev.kmunton.year2023.day24;

public class Hailstorm {

  private final long px;
  private final long py;
  private final long pz;
  private final double vx;
  private final double vy;
  private final double vz;
  private double m;
  private double b;

  public Hailstorm(long px, long py, long pz, double vx, double vy, double vz) {
    this.px = px;
    this.py = py;
    this.pz = pz;
    this.vx = vx;
    this.vy = vy;
    this.vz = vz;
    calculateM();
    calculateB();


  }

  // calculate m in y = mx + b
  public void calculateM() {
    m = vy / vx;
  }

  // calculate b in y = mx + b
  public void calculateB() {
    b = py - (m * px);
  }

  // 3D equation of straight line in cartesian form
  // (x - x1) / vx = (y - y1) / vy = (z - z1) / vz

  public long getPx() {
    return px;
  }

  public long getPy() {
    return py;
  }

  public long getPz() {
    return pz;
  }

  public double getVx() {
    return vx;
  }

  public double getVy() {
    return vy;
  }

  public double getVz() {
    return vz;
  }

  public double getM() {
    return m;
  }

  public double getB() {
    return b;
  }

  @Override
  public String toString() {
    return "Hailstorm{" +
        "px=" + px +
        ", py=" + py +
        ", pz=" + pz +
        ", vx=" + vx +
        ", vy=" + vy +
        ", vz=" + vz +
        ", m=" + m +
        ", b=" + b +
        '}';
  }
}
