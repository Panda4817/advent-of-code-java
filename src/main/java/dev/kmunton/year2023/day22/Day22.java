package dev.kmunton.year2023.day22;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.CubePoint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

public class Day22 implements Day<Long, Long> {

  private final Map<Integer, Brick> bricks = new HashMap<>();
  private final Map<Integer, Set<Integer>> supports = new HashMap<>();


  public Day22(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    int id = 1;
    for (var line : input) {
      var split = line.split("~");
      var end1Array = Arrays.stream(split[0].split(",")).map(Integer::parseInt).toArray(Integer[]::new);
      var end2Array = Arrays.stream(split[1].split(",")).map(Integer::parseInt).toArray(Integer[]::new);
      var end1 = new CubePoint(end1Array[0], end1Array[1], end1Array[2]);
      var end2 = new CubePoint(end2Array[0], end2Array[1], end2Array[2]);
      bricks.put(id, new Brick(end1, end2));
      id++;
    }
    makeBricksFall();
    populateSupportsMap();
  }

  public Long part1() {
    var bricksThatCanBeRemovedWithoutOtherBricksFalling = 0;
    for (var id : supports.keySet()) {
      if (supports.get(id).isEmpty()) {
        bricksThatCanBeRemovedWithoutOtherBricksFalling++;
        continue;
      }
      Map<Integer, Set<Integer>> supportsOfSupports = getSupportsOfSupports(id);
      if (supportsOfSupports.values().stream().noneMatch(Set::isEmpty)) {
        bricksThatCanBeRemovedWithoutOtherBricksFalling++;
      }
    }
    return (long) bricksThatCanBeRemovedWithoutOtherBricksFalling;

  }

  private boolean isBrickAtRest(Brick brick, Integer id) {
    int minZ = brick.getAllCubes().stream().map(CubePoint::z).min(Integer::compareTo).orElseThrow();
    if (minZ == 1) {
      return true;
    }
    for (var point : brick.getAllCubes()) {
      if (point.z() != minZ) {
        continue;
      }
      var pointBelow = new CubePoint(point.x(), point.y(), point.z() - 1);
      if (bricks.entrySet().stream()
                .filter(b -> !Objects.equals(b.getKey(), id))
                .map(Entry::getValue).anyMatch(b -> b.getAllCubes().contains(pointBelow))) {
        return true;
      }
    }
    return false;
  }


  private Brick updateBrickEnds(Brick brick, Integer id) {
    while (!isBrickAtRest(brick, id)) {
      var end1 = brick.getEnd1();
      var end2 = brick.getEnd2();
      var newEnd1 = new CubePoint(end1.x(), end1.y(), end1.z() - 1);
      var newEnd2 = new CubePoint(end2.x(), end2.y(), end2.z() - 1);
      brick = new Brick(newEnd1, newEnd2);
    }
    return brick;
  }

  public Long part2() {
    long sum = 0;
    for (int id : supports.keySet()) {
      var idToCheck = new ArrayList<Integer>();
      idToCheck.add(id);
      var goneBlocks = new HashSet<Integer>();
      while (!idToCheck.isEmpty()) {
        var currentId = idToCheck.remove(0);
        var supportsOfSupports = getSupportsOfSupports(currentId);
        var nextIds = supportsOfSupports.entrySet().stream()
                                        .filter(s -> s.getValue().isEmpty() || goneBlocks.containsAll(s.getValue()))
                                        .map(Map.Entry::getKey).toList();
        for (var nextId : nextIds) {
          if (!goneBlocks.contains(nextId)) {
            idToCheck.add(nextId);
            goneBlocks.add(nextId);
          }
        }

      }
      sum += goneBlocks.size();
    }
    return sum;
  }

  private Map<Integer, Set<Integer>> getSupportsOfSupports(int id) {
    Map<Integer, Set<Integer>> supportsOfSupports = new HashMap<>();
    supports.get(id).forEach(s -> {
      var ids = supports.entrySet().stream()
                        .filter(e -> !Objects.equals(e.getKey(), id))
                        .filter(e -> e.getValue().contains(s))
                        .map(Map.Entry::getKey).toList();
      supportsOfSupports.put(s, Set.copyOf(ids));
    });
    return supportsOfSupports;
  }

  private void populateSupportsMap() {
    for (var entry : bricks.entrySet()) {
      var id = entry.getKey();
      var brick = entry.getValue();

      var highestZ = brick.getAllCubes().stream().map(CubePoint::z).max(Integer::compareTo).orElseThrow();
      var pointsAboveBrick = brick.getAllCubes().stream()
                                  .filter(p -> p.z() == highestZ)
                                  .map(p -> new CubePoint(p.x(), p.y(), p.z() + 1)).toList();
      var brickIdsAboveThatMatchPointsAboveBrick = bricks.entrySet().stream()
                                                         .filter(e -> !Objects.equals(e.getKey(), id))
                                                         .filter(e -> e.getValue().getAllCubes().stream().anyMatch(pointsAboveBrick::contains))
                                                         .map(Map.Entry::getKey).toList();
      supports.put(id, Set.copyOf(brickIdsAboveThatMatchPointsAboveBrick));
    }
  }

  private void makeBricksFall() {
    Map<Integer, Boolean> isProcessed = new HashMap<>();
    for (var brick : bricks.entrySet()) {
      isProcessed.put(brick.getKey(), false);
    }
    var currentZ = 1;
    while (isProcessed.values().stream().anyMatch(v -> !v)) {

      for (var entry : bricks.entrySet()) {
        if (isProcessed.get(entry.getKey())) {
          continue;
        }
        var minZ = Math.min(entry.getValue().getEnd1().z(), entry.getValue().getEnd2().z());
        if (minZ == currentZ) {
          var brick = entry.getValue();
          var brickId = entry.getKey();
          var newbrick = updateBrickEnds(brick, brickId);
          isProcessed.put(brickId, true);
          bricks.put(brickId, newbrick);
        }
      }
      currentZ++;
    }
  }
}
