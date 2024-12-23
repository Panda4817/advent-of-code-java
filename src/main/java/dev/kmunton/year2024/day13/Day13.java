package dev.kmunton.year2024.day13;


import static dev.kmunton.utils.algorithms.GraphSearchUtils.aStarSearch;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.GridPoint;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day13 implements Day<Long, Long> {

  private final Set<Claw> claws = new HashSet<>();
  private static final String BUTTON_A = "A";
  private static final String BUTTON_B = "B";

  public Day13(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    Map<String, GridPoint> buttons = new HashMap<>();
    for (String line : input) {
      if (line.startsWith("Prize")) {
        String[] parts = line.split(": ")[1].split(", ");
        int x = Integer.parseInt(parts[0].split("=")[1]);
        int y = Integer.parseInt(parts[1].split("=")[1]);
        claws.add(new Claw(buttons, new GridPoint(x, y), new GridPoint(0, 0), 0, 0, 0));
        buttons = new HashMap<>();
      }
      if (line.startsWith("Button")) {
        String[] xyPart = line.split(": ")[1].split(", ");
        String letter = line.split(": ")[0].split(" ")[1];
        int x = Integer.parseInt(xyPart[0].split("\\+")[1]);
        int y = Integer.parseInt(xyPart[1].split("\\+")[1]);
        buttons.put(letter, new GridPoint(x, y));
      }

    }
  }

  @Override
  public Long part1() {
    return claws.stream().map(claw -> {
      GridPoint end = claw.goal();
      List<Claw> path = aStarSearch(
          claw,
          c -> end.equals(c.current()),
          Claw::getNeighbors,
          Claw::heuristic
      );
      List<Claw> last = path.stream().filter(c -> c.goal().equals(c.current())).toList();
      if (last.size() == 1) {
        return ((last.getFirst().aButtonPresses() * 3) + (last.getFirst().bButtonPresses()));
      } else {
        return 0;
      }

    }).mapToLong(Long::valueOf).sum();
  }

  @Override
  public Long part2() {
    long unitIncreaseFactor = 10000000000000L;
    return claws.stream().map(claw -> {
      long py = unitIncreaseFactor + claw.goal().y();
      long px = unitIncreaseFactor + claw.goal().x();
      long ax = claw.buttons().get(BUTTON_A).x();
      long ay = claw.buttons().get(BUTTON_A).y();
      long bx = claw.buttons().get(BUTTON_B).x();
      long by = claw.buttons().get(BUTTON_B).y();
      
      long bTop = (py * ax) - (px * ay);
      long bBottom = (ax * by) - (ay * bx);
      if (bTop % bBottom != 0) {
        return 0L;
      }
      long bPresses = bTop / bBottom;
      
      long aTop = (py * bx) - (px * by);
      long aBottom = (ay * bx) - (ax * by);
      if (aTop % aBottom != 0) {
        return 0L;
      }
      long aPresses = aTop / aBottom;
      
      if (aPresses < 0 || bPresses < 0) {
        return 0L;
      }
      
      return (aPresses * 3) + (bPresses);

    }).mapToLong(l -> l).sum();
  }


  record Claw(Map<String, GridPoint> buttons, GridPoint goal, GridPoint current, Integer tokens, Integer aButtonPresses, Integer bButtonPresses) {

    public Map<Claw, Integer> getNeighbors() {
      GridPoint a = new GridPoint(current().x() + buttons.get(BUTTON_A).x(), current().y() + buttons.get(BUTTON_A).y());
      GridPoint b = new GridPoint(current().x() + buttons.get(BUTTON_B).x(), current().y() + buttons.get(BUTTON_B).y());
      Map<Claw, Integer> neighbors = new HashMap<>();
      if (aButtonPresses() + 1 <= 100) {
        neighbors.put(new Claw(buttons, goal, a, 3, aButtonPresses() + 1, bButtonPresses()), 3);
      }
      if (bButtonPresses() + 1 <= 100) {
        neighbors.put(new Claw(buttons, goal, b, 1, aButtonPresses(), bButtonPresses() + 1), 1);
      }
      return neighbors;
    }

    public Integer heuristic() {
      return goal().manhattanDistance(current()) + tokens;
    }
  }

}
