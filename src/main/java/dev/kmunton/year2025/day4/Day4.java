package dev.kmunton.year2025.day4;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.GridPoint;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class Day4 implements Day<Long, Long> {

    private final Set<GridPoint> rollsOfPaper =  new HashSet<>();

  public Day4(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
      int x = -1;
      int y = -1;
      for  (final String s : input) {
          y++;
          for (final char c : s.toCharArray()) {
              x++;
              if (c == '@') {
                  final GridPoint gridPoint = new GridPoint(x, y);
                  rollsOfPaper.add(gridPoint);
              }
          }
          x = -1;
      }
  }

  @Override
  public Long part1() {
      return (long) getAccessibleRolls(rollsOfPaper).size();
  }

  @Override
  public Long part2() {
    final Set<GridPoint> currentRolls = new HashSet<>(rollsOfPaper);
    Set<GridPoint> accessibleRolls = getAccessibleRolls(currentRolls);
    long totalRollsRemoved = 0;
    while (!accessibleRolls.isEmpty()) {
        totalRollsRemoved += accessibleRolls.size();
        currentRolls.removeAll(accessibleRolls);
        accessibleRolls = getAccessibleRolls(currentRolls);
    }
    return totalRollsRemoved;
  }

  private Set<GridPoint> getAccessibleRolls(final Set<GridPoint> currentRolls) {
      final Set<GridPoint> accessibleRolls = new HashSet<>();
      for (final GridPoint gridPoint : currentRolls) {
          final List<GridPoint> neighbors = gridPoint.getAllNeighbors();
          int adjacentRolls = 0;
          for (final GridPoint neighbor : neighbors) {
              if (currentRolls.contains(neighbor)) {
                  adjacentRolls++;
              }
          }
          if (adjacentRolls < 4) {
              accessibleRolls.add(gridPoint);
          }
      }
      return accessibleRolls;
  }

}
