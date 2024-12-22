package dev.kmunton.days.day21;


import static dev.kmunton.utils.algorithms.GraphSearchUtils.aStarSearch;

import dev.kmunton.days.Day;
import dev.kmunton.utils.geometry.Direction2D;
import dev.kmunton.utils.geometry.GridPoint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day21 implements Day<Long, Long> {

  private static final Integer A = -1;
  private final Map<String, List<Integer>> codes = new HashMap<>();
  private static final Map<GridPoint, Button> NUMERIC_KEY_PAD = new HashMap<>();
  private static final Map<GridPoint, Button> DIRECTION_KEY_PAD = new HashMap<>();
  private static final Map<String, Map<State, Integer>> neighborCache = new HashMap<>();
  private static final Map<String, Integer> heuristicCache = new HashMap<>();


  static {
    NUMERIC_KEY_PAD.put(new GridPoint(2, 3), new Button(null, A, "A"));
    NUMERIC_KEY_PAD.put(new GridPoint(1, 3), new Button(null, 0, "0"));
    NUMERIC_KEY_PAD.put(new GridPoint(2, 2), new Button(null, 3, "3"));
    NUMERIC_KEY_PAD.put(new GridPoint(1, 2), new Button(null, 2, "2"));
    NUMERIC_KEY_PAD.put(new GridPoint(0, 2), new Button(null, 1, "1"));
    NUMERIC_KEY_PAD.put(new GridPoint(2, 1), new Button(null, 6, "6"));
    NUMERIC_KEY_PAD.put(new GridPoint(1, 1), new Button(null, 5, "5"));
    NUMERIC_KEY_PAD.put(new GridPoint(0, 1), new Button(null, 4, "4"));
    NUMERIC_KEY_PAD.put(new GridPoint(2, 0), new Button(null, 9, "9"));
    NUMERIC_KEY_PAD.put(new GridPoint(1, 0), new Button(null, 8, "8"));
    NUMERIC_KEY_PAD.put(new GridPoint(0, 0), new Button(null, 7, "7"));

    DIRECTION_KEY_PAD.put(new GridPoint(1, 0), new Button(Direction2D.UP, null, "^"));
    DIRECTION_KEY_PAD.put(new GridPoint(0, 1), new Button(Direction2D.LEFT, null, "<"));
    DIRECTION_KEY_PAD.put(new GridPoint(1, 1), new Button(Direction2D.DOWN, null, "v"));
    DIRECTION_KEY_PAD.put(new GridPoint(2, 1), new Button(Direction2D.RIGHT, null, ">"));
    DIRECTION_KEY_PAD.put(new GridPoint(2, 0), new Button(null, A, "A"));
  }

  public Day21(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    input.forEach(line -> {
      List<Integer> numbers = Arrays.stream(line.split("")).map(s -> {
        if (s.equals("A")) {
          return A;
        }
        return Integer.parseInt(s);
      }).toList();
      codes.put(line, numbers);
    });
  }

  @Override
  public Long part1() {
    return codes.keySet().stream()
                .map(code -> fastCompute(code, 2))
                .mapToLong(c -> c).sum();
  }

  @Override
  public Long part2() {
    return codes.keySet().stream()
                .map(code -> fastCompute(code, 25))
                .mapToLong(c -> c).sum();
  }

  private long getComplexity(long pathLength, String code) {
    return (Long.parseLong(code.replaceAll("\\D", ""))) * pathLength;
  }

  private long fastCompute(String code, int directionalRobots) {
    Map<String, Map<String, List<String>>> numericMoves = getMoves(NUMERIC_KEY_PAD, 0, 3);
    Map<String, Map<String, List<String>>> directionMoves = getMoves(DIRECTION_KEY_PAD, 0, 0);

    Map<String, Long> codeMap = new HashMap<>();
    codeMap.put(code, 1L);
    Map<String, Long> moves = parentKeyPresses(numericMoves, codeMap);

    for (int j = 0; j < directionalRobots; j++) {
      moves = parentKeyPresses(directionMoves, moves);
    }
    long sum = 0;
    for (Entry<String, Long> entry : moves.entrySet()) {
      sum += (entry.getKey().length() * entry.getValue());
    }

    return getComplexity(sum, code);

  }

  private Map<String, Long> parentKeyPresses(Map<String, Map<String, List<String>>> keypad, Map<String, Long> moves) {
    Map<String, Long> out = new HashMap<>();
    for (Entry<String, Long> entry : moves.entrySet()) {
      String current = "A";
      for (int i = 0; i < entry.getKey().length(); i++) {
        String next = String.valueOf(entry.getKey().charAt(i));
        String kpm = String.join("", keypad.get(current).get(next));
        out.put(kpm, out.getOrDefault(kpm, 0L) + entry.getValue());
        current = next;
      }
    }
    return out;
  }

  private Map<String, Map<String, List<String>>> getMoves(Map<GridPoint, Button> keypad, int blankX, int blankY) {
    Map<String, Map<String, List<String>>> out = new HashMap<>();
    for (Entry<GridPoint, Button> entry1 : keypad.entrySet()) {
      for (Entry<GridPoint, Button> entry2 : keypad.entrySet()) {
        boolean hitsBlankX = entry1.getKey().y() == blankY && entry2.getKey().x() == blankX;
        boolean hitsBlankY = entry1.getKey().x() == blankX && entry2.getKey().y() == blankY;
        List<String> moves = new ArrayList<>();
        if (entry2.getKey().x() < entry1.getKey().x() && !hitsBlankX) {
          for (int i = 0; i < entry1.getKey().x() - entry2.getKey().x(); i++) {
            moves.add("<");
          }
        }

        if (entry2.getKey().y() > entry1.getKey().y() && !hitsBlankY) {
          for (int i = 0; i < entry2.getKey().y() - entry1.getKey().y(); i++) {
            moves.add("v");
          }
        }

        if (entry2.getKey().y() < entry1.getKey().y() && !hitsBlankY) {
          for (int i = 0; i < entry1.getKey().y() - entry2.getKey().y(); i++) {
            moves.add("^");
          }
        }

        if (entry2.getKey().x() > entry1.getKey().x()) {
          for (int i = 0; i < entry2.getKey().x() - entry1.getKey().x(); i++) {
            moves.add(">");
          }
        }

        if (entry2.getKey().x() < entry1.getKey().x() && hitsBlankX) {
          for (int i = 0; i < entry1.getKey().x() - entry2.getKey().x(); i++) {
            moves.add("<");
          }
        }

        if (entry2.getKey().y() > entry1.getKey().y() && hitsBlankY) {
          for (int i = 0; i < entry2.getKey().y() - entry1.getKey().y(); i++) {
            moves.add("v");
          }
        }

        if (entry2.getKey().y() < entry1.getKey().y() && hitsBlankY) {
          for (int i = 0; i < entry1.getKey().y() - entry2.getKey().y(); i++) {
            moves.add("^");
          }
        }

        moves.add("A");
        Map<String, List<String>> value = out.getOrDefault(entry1.getValue().str(), new HashMap<>());
        value.put(entry2.getValue().str(), moves);
        out.put(entry1.getValue().str(), value);
      }
    }
    return out;
  }

  // Brute force
  private long computeWithAStar(int robotNumber) {
    long sum = 0;
    for (Entry<String, List<Integer>> entry : codes.entrySet()) {
      List<Integer> code = entry.getValue();
      Map<Integer, GridPoint> robots = new HashMap<>();
      for (int i = 1; i < robotNumber; i++) {
        robots.put(i, new GridPoint(2, 0));
      }
      robots.put(robotNumber, new GridPoint(2, 3));
      long pathLength = 0;
      State start = null;
      for (int endNum : code) {
        neighborCache.clear();
        heuristicCache.clear();
        GridPoint goal = NUMERIC_KEY_PAD.entrySet().stream().filter(e -> e.getValue().other() == endNum).map(Entry::getKey).findFirst().orElseThrow();
        if (start == null) {
          start = new State(robots, goal, false);
        } else {
          start = start.copyWithNewGoal(goal);
        }
        List<State> path = aStarSearch(
            start,
            s -> s.isGoal(robotNumber),
            s -> s.getNeighbors(robotNumber),
            s -> s.heuristic(robotNumber)
        );
        pathLength += path.size() - 1;
        start = path.getLast();
      }
      sum += getComplexity(pathLength, entry.getKey());
    }
    return sum;
  }

  record Button(Direction2D direction, Integer other, String str) {

  }

  record State(
      Map<Integer, GridPoint> robots,
      GridPoint goal,
      boolean pushedNumber
  ) {

    public Map<State, Integer> getNeighbors(int mainRobot) {
      return neighborCache.computeIfAbsent(this.key(mainRobot), state ->
          DIRECTION_KEY_PAD.values().stream().map(d -> {
            boolean pushed = false;
            if (Objects.equals(d.other(), A)) {
              int robotNumber = 1;
              while (robotNumber < mainRobot) {
                Button button = DIRECTION_KEY_PAD.get(robots.get(robotNumber));
                if (Objects.equals(button.other(), A)) {
                  robotNumber++;
                  continue;
                }
                GridPoint newNextRobot = robots.get(robotNumber + 1).moveByGivenDirection(button.direction);
                if (robotNumber + 1 == mainRobot) {
                  if (NUMERIC_KEY_PAD.containsKey(newNextRobot)) {
                    Map<Integer, GridPoint> updatedRobots = new HashMap<>(robots);
                    updatedRobots.put(robotNumber + 1, newNextRobot);
                    return new State(updatedRobots, goal, pushed);
                  } else {
                    return null;
                  }
                } else if (DIRECTION_KEY_PAD.containsKey(newNextRobot)) {
                  Map<Integer, GridPoint> updatedRobots = new HashMap<>(robots);
                  updatedRobots.put(robotNumber + 1, newNextRobot);
                  return new State(updatedRobots, goal, pushed);
                } else {
                  return null;
                }
              }
              pushed = true;
              Map<Integer, GridPoint> updatedRobots = new HashMap<>(robots);
              return new State(updatedRobots, goal, pushed);
            }
            GridPoint newOne = robots.get(1).moveByGivenDirection(d.direction());
            if (DIRECTION_KEY_PAD.containsKey(newOne)) {
              Map<Integer, GridPoint> updatedRobots = new HashMap<>(robots);
              updatedRobots.put(1, newOne);
              return new State(updatedRobots, goal, pushed);
            }
            return null;
          }).filter(Objects::nonNull).collect(Collectors.toMap(s -> s, s -> 1)));
    }

    public int heuristic(int mainRobot) {
      return heuristicCache.computeIfAbsent(this.key(mainRobot),
          state -> robots.get(mainRobot).manhattanDistance(goal) + 1);
    }

    public boolean isGoal(int mainRobot) {
      return robots.get(mainRobot).equals(goal) && pushedNumber;
    }

    public State copyWithNewGoal(GridPoint newGoal) {
      return new State(new HashMap<>(robots), new GridPoint(newGoal.x(), newGoal.y()), false);
    }

    public String key(int mainRobot) {
      StringBuilder key = new StringBuilder();
      for (int r = 1; r <= mainRobot; r++) {
        key.append(robots.get(r));
      }
      return key.toString();
    }
  }


}
