package dev.kmunton.year2021.day20;


import dev.kmunton.utils.days.Day;
import dev.kmunton.year2021.day5.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day20 implements Day<Long, Long> {

  private final Map<Integer, Integer> output = new HashMap<>();
  private final Map<Point, Integer> map = new HashMap<>();
  private final int[] dx = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
  private final int[] dy = {-1, -1, -1, 0, 0, 0, 1, 1, 1};
  private int background = 0;

  public Day20(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    boolean isOutput = true;
    int y = 0;
    for (String s : input) {
      if (Objects.equals(s, "")) {
        isOutput = false;
        continue;
      }

      if (isOutput) {
        int i = 0;
        for (Character c : s.toCharArray()) {
          output.put(i, c == '#' ? 1 : 0);
          i += 1;
        }
        continue;
      }

      if (!isOutput) {
        int x = 0;
        for (Character c : s.toCharArray()) {
          Point point = new Point(x, y);
          map.put(point, c == '#' ? 1 : 0);
          x += 1;
        }

        y += 1;
      }
    }
  }

  private int getBinToDecimal(String s) {
    int dec = Integer.parseInt(s, 2);
    return dec;
  }

  private String findNeighborsAndCreateBinaryString(Point point, Map<Point, Integer> copy, int background) {

    StringBuilder binary = new StringBuilder();
    for (int i = 0; i < 9; i++) {
      Point p = new Point(point.getX() + dx[i], point.getY() + dy[i]);
      int num = copy.getOrDefault(p, background);
      binary.append(num);
    }
    return binary.toString();
  }

  private void addEdgePixels(int background) {
    Map<Point, Integer> copyMap = new HashMap<>(map);
    for (Point key : copyMap.keySet()) {
      for (int i = 0; i < 9; i++) {
        Point p = new Point(key.getX() + dx[i], key.getY() + dy[i]);
        if (!map.containsKey(p)) {
          map.put(p, background);

        }
      }
    }
  }


  private void printImage() {
    int minx = map.keySet().stream().map(Point::getX).mapToInt(i -> i).min().getAsInt();
    int maxx = map.keySet().stream().map(Point::getX).mapToInt(i -> i).max().getAsInt();
    int miny = map.keySet().stream().map(Point::getY).mapToInt(i -> i).min().getAsInt();
    int maxy = map.keySet().stream().map(Point::getY).mapToInt(i -> i).max().getAsInt();

    for (int y = miny; y < maxy + 1; y++) {
      for (int x = minx; x < maxx + 1; x++) {
        System.out.print(map.get(new Point(x, y)) == 1 ? '#' : '.');
      }
      System.out.println();
    }
  }


  @Override
  public Long part1() {
    for (int i = 0; i < 2; i++) {
      int newBackground = (background == 0) ? output.get(0) : output.get(511);
      addEdgePixels(newBackground);
      addEdgePixels(newBackground);
      Map<Point, Integer> copyMap = new HashMap<>(map);
      for (Point key : copyMap.keySet()) {
        String binary = findNeighborsAndCreateBinaryString(key, copyMap, background);
        int dec = getBinToDecimal(binary);
        map.put(key, output.get(dec));
      }
      background = newBackground;
    }
    printImage();
    return map.values().stream().filter(v -> v == 1).count();
  }

  @Override
  public Long part2() {
    for (int i = 0; i < 50; i++) {
      int newBackground = (background == 0) ? output.get(0) : output.get(511);
      addEdgePixels(newBackground);
      addEdgePixels(newBackground);
      Map<Point, Integer> copyMap = new HashMap<>(map);
      for (Point key : copyMap.keySet()) {
        String binary = findNeighborsAndCreateBinaryString(key, copyMap, background);
        int dec = getBinToDecimal(binary);
        map.put(key, output.get(dec));
      }
      background = newBackground;
    }
    printImage();
    return map.values().stream().filter(v -> v == 1).count();
  }
}
