package dev.kmunton.year2021.day19;

import java.util.Objects;

public class Vector {

  private long x;
  private long y;
  private long z;

  public Vector(long x, long y, long z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Vector(Vector that) {
    this(that.getX(), that.getY(), that.getZ());
  }

  public void rotate90ByX() {
    long temp = getY();
    setY(getZ() * -1);
    setZ(temp);
  }

  public void rotate90ByY() {
    long temp = getZ();
    setZ(getX() * -1);
    setX(temp);
  }

  public void rotate90ByZ() {
    long temp = getX();
    setX(getY() * -1);
    setY(temp);
  }


  @Override
  public String toString() {
    return "Vector{" +
        "x=" + x +
        ", y=" + y +
        ", z=" + z +
        '}';
  }

  public long getX() {
    return x;
  }

  public void setX(long x) {
    this.x = x;
  }

  public long getY() {
    return y;
  }

  public void setY(long y) {
    this.y = y;
  }

  public long getZ() {
    return z;
  }

  public void setZ(long z) {
    this.z = z;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vector vector = (Vector) o;
    return x == vector.x && y == vector.y && z == vector.z;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, z);
  }
}
