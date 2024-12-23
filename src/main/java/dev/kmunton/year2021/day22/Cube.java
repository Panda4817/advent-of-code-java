package dev.kmunton.year2021.day22;


import java.util.Objects;

public class Cube {

  private int x;
  private int y;
  private int z;

  public Cube(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Cube(Cube cube) {
    this.x = cube.getX();
    this.y = cube.getY();
    this.z = cube.getZ();
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getZ() {
    return z;
  }

  public void setZ(int z) {
    this.z = z;
  }

  @Override
  public String toString() {
    return "Cube{" +
        "x=" + x +
        ", y=" + y +
        ", z=" + z +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cube cube = (Cube) o;
    return x == cube.x && y == cube.y && z == cube.z;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, z);
  }
}
