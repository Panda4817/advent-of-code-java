package dev.kmunton.year2021.day19;

import dev.kmunton.utils.days.Day;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day19 implements Day<Integer, Long> {

  private Queue<Scanner> queue;
  private List<Scanner> scanners;
  private int totalScanners;
  private final int minCount = 12;
  private Map<Integer, Vector> scannerPositions;

  public Day19(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    queue = new ArrayDeque<>();
    List<Vector> beacons = new ArrayList<>();
    scanners = new ArrayList<>();
    scannerPositions = new HashMap<>();
    int scannerNumber = 0;
    for (String s : input) {
      if (Objects.equals(s, "")) {
        Scanner scanner = new Scanner(scannerNumber, beacons);
        queue.add(scanner);
        scanners.add(new Scanner(scanner));
        beacons = new ArrayList<>();
        scannerNumber += 1;
        continue;
      }
      if (s.contains("--- scanner ")) {
        continue;
      }

      List<Long> threeNumbers = Stream.of(s.split(",")).map(Long::valueOf).collect(Collectors.toList());
      Vector vector = new Vector(threeNumbers.get(0), threeNumbers.get(1), threeNumbers.get(2));
      beacons.add(vector);
    }
    Scanner scanner = new Scanner(scannerNumber, beacons);
    queue.add(scanner);
    scanners.add(new Scanner(scanner));
    scannerNumber += 1;
    totalScanners = scannerNumber;
  }

  private Vector getDiff(Vector v1, Vector v2) {
    long x = v1.getX() - v2.getX();
    long y = v1.getY() - v2.getY();
    long z = v1.getZ() - v2.getZ();
    return new Vector(x, y, z);
  }

  private Map<Vector, Integer> checkScanners(Scanner scanner0, Scanner scanner) {
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 4; k++) {
          // Check S0 and S1
          Map<Vector, Integer> overlappingCount = new HashMap<>();
          for (Vector zero : scanner0.getBeacons()) {
            for (Vector v : scanner.getBeacons()) {
              Vector key = getDiff(zero, v);
              int count = overlappingCount.getOrDefault(key, 0);
              overlappingCount.put(key, count + 1);
            }
          }

          // If match break/return
          long maxCount = overlappingCount.values().stream().filter(v -> v >= minCount).count();
          if (maxCount > 0) {
            return overlappingCount;
          }

          // Rotate S1 around Z
          for (Vector v : scanner.getBeacons()) {
            v.rotate90ByZ();
          }
        }
        // Rotate S1 around Y
        for (Vector v : scanner.getBeacons()) {
          v.rotate90ByY();
        }
      }
      // Rotate S1 around X
      for (Vector v : scanner.getBeacons()) {
        v.rotate90ByX();
      }
    }
    return new HashMap<>();
  }

  @Override
  public Integer part1() {

    Scanner scanner0 = queue.poll();
    while (!queue.isEmpty()) {
      Scanner scanner = queue.poll();

      Map<Vector, Integer> map = checkScanners(scanner0, scanner);

      if (map.keySet().size() == 0) {
        queue.add(scanner);
        continue;
      }

      Vector scannerPos = map.keySet().stream().filter(k -> map.get(k) >= 12).collect(Collectors.toList()).get(0);
      scannerPositions.put(scanner.getNumber(), scannerPos);
      for (Vector v : scanner.getBeacons()) {
        long x = v.getX() + scannerPos.getX();
        long y = v.getY() + scannerPos.getY();
        long z = v.getZ() + scannerPos.getZ();
        Vector toAdd = new Vector(x, y, z);
        if (scanner0.getBeacons().contains(toAdd)) {
          continue;
        }
        scanner0.getBeacons().add(toAdd);
      }

    }

    return scanner0.getBeacons().size();
  }

  private long manhattenDistance(Vector v1, Vector v2) {

    long x = v2.getX() - v1.getX();
    long y = v2.getY() - v1.getY();
    long z = v2.getZ() - v1.getZ();
    return Math.abs(x) + Math.abs(y) + Math.abs(z);
  }

  @Override
  public Long part2() {
    part1();
    long largestDistance = 0L;

    for (Map.Entry<Integer, Vector> entry : scannerPositions.entrySet()) {
      for (Map.Entry<Integer, Vector> compEntry : scannerPositions.entrySet()) {
        if (Objects.equals(entry.getKey(), compEntry.getKey())) {
          continue;
        }
        long mh = manhattenDistance(entry.getValue(), compEntry.getValue());
        if (mh > largestDistance) {
          largestDistance = mh;
        }
      }
    }
    return largestDistance;
  }
}
