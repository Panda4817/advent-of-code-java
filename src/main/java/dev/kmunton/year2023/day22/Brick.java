package dev.kmunton.year2023.day22;

import dev.kmunton.utils.geometry.CubePoint;
import java.util.HashSet;
import java.util.Set;

public class Brick {

  private final CubePoint end1;
  private final CubePoint end2;
  private final Set<CubePoint> allCubes = new HashSet<>();

  public Brick(CubePoint end1, CubePoint end2) {
    this.end1 = end1;
    this.end2 = end2;
    for (int x = end1.x(); x <= end2.x(); x++) {
      for (int y = end1.y(); y <= end2.y(); y++) {
        for (int z = end1.z(); z <= end2.z(); z++) {
          allCubes.add(new CubePoint(x, y, z));
        }
      }
    }
  }

  public CubePoint getEnd1() {
    return end1;
  }

  public CubePoint getEnd2() {
    return end2;
  }

  public Set<CubePoint> getAllCubes() {
    return allCubes;
  }

  @Override
  public String toString() {
    return "Brick{" +
        "end1=" + end1 +
        ", end2=" + end2 +
        "\n, allCubes=" + allCubes +
        '}';
  }
}
