package dev.kmunton.days.day14;


import dev.kmunton.days.Day;
import dev.kmunton.utils.geometry.GridPoint;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day14 implements Day<Long, Long> {

  private static final int X_TILES = 101;
  private static final int Y_TILES = 103;
  private final Set<Robot> robots = new HashSet<>();

  public Day14(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    input.forEach(line -> {
      String[] split = line.split(" ");
      String[] split2 = split[0].split("=")[1].split(",");
      GridPoint currentPoint = new GridPoint(Integer.parseInt(split2[0]), Integer.parseInt(split2[1]));
      String[] split3 = split[1].split("=")[1].split(",");
      int x = Integer.parseInt(split3[0]);
      int y = Integer.parseInt(split3[1]);
      robots.add(new Robot(currentPoint, x, y));
    });
  }

  @Override
  public Long part1() {
    Set<Robot> movedRobots = simulateMovement(100, X_TILES, Y_TILES);
    List<Long> quadrantCounts = getRobotsInEachQuadrant(movedRobots, X_TILES, Y_TILES);
    return getSafetyFactor(quadrantCounts);
  }

  public Long part1Test() {
    int xTiles = 11;
    int yTiles = 7;
    Set<Robot> movedRobots = simulateMovement(100, xTiles, yTiles);
    List<Long> quadrantCounts = getRobotsInEachQuadrant(movedRobots, xTiles, yTiles);
    return getSafetyFactor(quadrantCounts);
  }


  @Override
  public Long part2() {
    Set<Robot> robotsCopy = robots.stream().map(Robot::copy).collect(Collectors.toSet());

    long seconds = 0;
    while (!isChristmasTree(robotsCopy)) {
      seconds += 1;
      robotsCopy.forEach(r -> r.move(X_TILES, Y_TILES));
    }
    log.info("SECOND: {}", seconds);
    printRobots(robotsCopy);

    return seconds;

  }

  private boolean isChristmasTree(Set<Robot> robotsSet) {
    for (Robot robot : robotsSet) {
      List<GridPoint> neighbors = robot.getCurrent().getAllNeighbors();
      Set<GridPoint> surroundingRobots = robotsSet.stream().map(Robot::getCurrent).filter(neighbors::contains).collect(Collectors.toSet());
      if (surroundingRobots.size() == neighbors.size()) {
        return true;
      }
    }
    return false;
  }

  private Set<Robot> simulateMovement(int times, int xTiles, int yTiles) {
    Set<Robot> robotsCopy = robots.stream().map(Robot::copy).collect(Collectors.toSet());

    for (int second = 0; second < times; second++) {
      robotsCopy.forEach(r -> r.move(xTiles, yTiles));
    }
    return robotsCopy;
  }

  private void printRobots(Set<Robot> robotsSet) {
    for (int y = 0; y < Y_TILES; y++) {
      StringBuilder line = new StringBuilder();
      for (int x = 0; x < X_TILES; x++) {
        final GridPoint currentPoint = new GridPoint(x, y);
        if (robotsSet.stream().anyMatch(r -> r.getCurrent().equals(currentPoint))) {
          line.append("X");
          continue;
        }
        line.append(".");
      }
      log.info(line.toString());
    }
    log.info("");
  }

  private List<Long> getRobotsInEachQuadrant(Set<Robot> robots, int xTiles, int yTiles) {
    int xToIgnore = (xTiles - 1) / 2;
    int yToIgnore = (yTiles - 1) / 2;
    List<List<Integer>> quadrants = new ArrayList<>();
    quadrants.add(List.of(0, xToIgnore, 0, yToIgnore));
    quadrants.add(List.of(xToIgnore + 1, xTiles, 0, yToIgnore));
    quadrants.add(List.of(0, xToIgnore, yToIgnore + 1, yTiles));
    quadrants.add(List.of(xToIgnore + 1, xTiles, yToIgnore + 1, yTiles));

    List<Long> counts = new ArrayList<>();
    for (List<Integer> quadrant : quadrants) {
      long robotCount = robots.stream().filter(r -> r.isInQuadrant(quadrant)).count();
      counts.add(robotCount);
    }
    return counts;
  }

  private long getSafetyFactor(List<Long> countsInEachQuadrant) {
    return countsInEachQuadrant.stream().reduce(1L, (a, b) -> a * b);
  }

  @Data
  @AllArgsConstructor
  static class Robot {

    private GridPoint current;
    private int xVelocity;
    private int yVelocity;

    public void move(int xTiles, int yTiles) {
      GridPoint newPoint = new GridPoint(current.x() + xVelocity, current.y() + yVelocity);
      if (newPoint.x() < 0) {
        newPoint = new GridPoint(newPoint.x() + xTiles, newPoint.y());
      }
      if (newPoint.x() >= xTiles) {
        newPoint = new GridPoint(newPoint.x() - xTiles, newPoint.y());
      }
      if (newPoint.y() < 0) {
        newPoint = new GridPoint(newPoint.x(), newPoint.y() + yTiles);
      }
      if (newPoint.y() >= yTiles) {
        newPoint = new GridPoint(newPoint.x(), newPoint.y() - yTiles);
      }
      this.setCurrent(newPoint);
    }

    public Robot copy() {
      return new Robot(current, xVelocity, yVelocity);
    }

    public boolean isInQuadrant(List<Integer> quadrant) {
      int x = this.getCurrent().x();
      int y = this.getCurrent().y();
      int qx = quadrant.get(0);
      int notX = quadrant.get(1);
      int qy = quadrant.get(2);
      int notY = quadrant.get(3);
      return x >= qx && x < notX && y >= qy && y < notY;
    }

    @Override
    public String toString() {
      return "Robot(current=" + current + ", xVel=" + xVelocity + ", yVel=" + yVelocity + ")";
    }
  }

}
