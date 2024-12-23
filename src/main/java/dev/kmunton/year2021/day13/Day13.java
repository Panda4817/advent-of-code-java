package dev.kmunton.year2021.day13;

import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day13 implements Day<Integer, Integer> {

  Map<Dot, Integer> data;
  List<FoldInstruction> folds;

  public Day13(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    data = new HashMap<>();
    folds = new ArrayList<>();
    boolean isCoordinate = true;
    for (String s : input) {
      if (s == "") {
        isCoordinate = false;
        continue;
      }
      if (isCoordinate) {
        String[] coords = s.split(",");
        Dot dot = new Dot(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
        int count = data.getOrDefault(dot, 0);
        data.put(dot, count + 1);
      } else {
        String[] parts = s.split("=");
        if (parts[0].contains("y")) {
          folds.add(new FoldInstruction(0, Integer.parseInt(parts[1])));
        } else {
          folds.add(new FoldInstruction(Integer.parseInt(parts[1]), 0));
        }
      }
    }
  }

  private int fold(FoldInstruction fold) {
    int x = fold.getX();
    int y = fold.getY();
    int dotCount = 0;
    Map<Dot, Integer> map = new HashMap<>();
    for (Dot dot : data.keySet()) {
      if (x != 0 && dot.getX() > x) {
        int newX = x - (dot.getX() - x);
        Dot newDot = new Dot(newX, dot.getY());
        int count = data.getOrDefault(newDot, 0);
        map.put(newDot, count + 1);
        if (count == 0) {
          dotCount += 1;
        }
      } else if (x != 0 && dot.getX() < x) {
        map.put(dot, data.get(dot));
        dotCount += 1;
      } else if (y != 0 && dot.getY() > y) {
        int newY = y - (dot.getY() - y);
        Dot newDot = new Dot(dot.getX(), newY);
        int count = data.getOrDefault(newDot, 0);
        map.put(newDot, count + 1);
        if (count == 0) {
          dotCount += 1;
        }
      } else if (y != 0 && dot.getY() < y) {
        map.put(dot, data.get(dot));
        dotCount += 1;
      }
    }
    data = new HashMap<>(map);
    System.out.println(fold);
    System.out.println(dotCount);
    System.out.println();
    return dotCount;
  }

  @Override
  public Integer part1() {
    int dotCount = 0;
    for (FoldInstruction fold : folds) {
      dotCount = fold(fold);
      break;
    }
    return dotCount;
  }

  @Override
  public Integer part2() {
    for (FoldInstruction fold : folds) {
      fold(fold);
    }

    Set<Dot> dots = data.keySet();
    int maxX = dots.stream().map(d -> d.getX()).mapToInt(i -> i).max().getAsInt();
    int maxY = dots.stream().map(d -> d.getY()).mapToInt(i -> i).max().getAsInt();
    int minX = dots.stream().map(d -> d.getX()).mapToInt(i -> i).min().getAsInt();
    int minY = dots.stream().map(d -> d.getY()).mapToInt(i -> i).min().getAsInt();
    for (int y = minY; y < maxY + 1; y++) {
      for (int x = minX; x < maxX + 1; x++) {
        Dot possibleDot = new Dot(x, y);
        if (dots.contains(possibleDot)) {
          System.out.print("#");
        } else {
          System.out.print(" ");
        }
      }
      System.out.println();
    }

    return 0;
  }
}
