package dev.kmunton.year2021.day5;


import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day5 implements Day<Integer, Integer> {

  List<Line> lines;
  int size;

  public Day5(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    lines = new ArrayList<>();
    input.forEach(s -> {
      String[] parts = s.split(" -> ");
      String[] start = parts[0].split(",");
      String[] end = parts[1].split(",");
      Line line = new Line(Integer.parseInt(start[0]), Integer.parseInt(start[1]), Integer.parseInt(end[0]), Integer.parseInt(end[1]));
      lines.add(line);
    });
    size = input.size();
  }

  @Override
  public Integer part1() {
    Map<Point, Integer> pointsCount = new HashMap<>();
    for (Line l : lines) {
      if (!l.isHorizontal() && !l.isVertical()) {
        continue;
      }

      List<Point> points = l.getAllPoints();

      for (Point p : points) {
        int count = pointsCount.getOrDefault(p, 0);
        pointsCount.put(p, count + 1);
      }
    }

    long intersections = pointsCount.values().stream().filter(v -> v >= 2).count();
    return Math.toIntExact(intersections);
  }

  @Override
  public Integer part2() {
    Map<Point, Integer> pointsCount = new HashMap<>();
    for (Line l : lines) {
      if (l.isVertical() || l.isHorizontal()) {
        List<Point> points = l.getAllPoints();
        for (Point p : points) {
          int count = pointsCount.getOrDefault(p, 0);
          pointsCount.put(p, count + 1);
        }
      } else if (l.isDiagonal()) {
        List<Point> points = l.getAllDiagonalPoints();
        for (Point p : points) {
          int count = pointsCount.getOrDefault(p, 0);
          pointsCount.put(p, count + 1);
        }
      }

    }

    long intersections = pointsCount.values().stream().filter(v -> v >= 2).count();
    return Math.toIntExact(intersections);
  }
}
