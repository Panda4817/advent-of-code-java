package dev.kmunton.year2021.day22;

import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Day22 implements Day<Long, Long> {


  List<Range> allRanges;

  public Day22(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    allRanges = new ArrayList<>();
    for (String s : input) {
      String[] parts = s.split(" ");
      boolean isOn = parts[0].equals("on");

      String[] xyz = parts[1].split(",");
      String[] xparts = xyz[0].split("\\.\\.");
      int minx = Integer.parseInt(xparts[0].substring(2));
      int maxx = Integer.parseInt(xparts[1]);
      String[] yparts = xyz[1].split("\\.\\.");
      int miny = Integer.parseInt(yparts[0].substring(2));
      int maxy = Integer.parseInt(yparts[1]);
      String[] zparts = xyz[2].split("\\.\\.");
      int minz = Integer.parseInt(zparts[0].substring(2));
      int maxz = Integer.parseInt(zparts[1]);

      Cube from = new Cube(minx, miny, minz);
      Cube to = new Cube(maxx, maxy, maxz);
      Range range = new Range(from, to, isOn);
      allRanges.add(range);
    }
  }


  @Override
  public Long part1() {

    List<Range> allCuboids = new ArrayList<>();
    for (Range r : allRanges) {
      int minx = r.getFrom().getX();
      int miny = r.getFrom().getY();
      int minz = r.getFrom().getZ();
      int maxx = r.getTo().getX();
      int maxy = r.getTo().getY();
      int maxz = r.getTo().getZ();
      if (minx >= -50 && miny >= -50 && minz >= -50 && maxx <= 50 && maxy <= 50 && maxz <= 50) {
        List<Range> extraCuboids = new ArrayList<>();
        if (r.getOn()) {
          extraCuboids.add(new Range(r));
        }
        for (Range cuboid : allCuboids) {
          Optional<Range> possibleOverlap = cuboid.getOverlapping(r);
          possibleOverlap.ifPresent(extraCuboids::add);
        }
        allCuboids.addAll(extraCuboids);
      }
    }
    return allCuboids.stream().mapToLong(r -> r.getOn() ? r.numberOfCubes() : -1 * r.numberOfCubes()).sum();
  }

  @Override
  public Long part2() {

    List<Range> allCuboids = new ArrayList<>();
    for (Range r : allRanges) {
      List<Range> extraCuboids = new ArrayList<>();
      if (r.getOn()) {
        extraCuboids.add(new Range(r));
      }
      for (Range cuboid : allCuboids) {
        Optional<Range> possibleOverlap = cuboid.getOverlapping(r);
        possibleOverlap.ifPresent(extraCuboids::add);
      }
      allCuboids.addAll(extraCuboids);
    }

    return allCuboids.stream().mapToLong(r -> r.getOn() ? r.numberOfCubes() : -1 * r.numberOfCubes()).sum();
  }
}
