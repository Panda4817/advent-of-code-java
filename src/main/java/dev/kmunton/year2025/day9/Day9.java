package dev.kmunton.year2025.day9;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.GridPoint;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day9 implements Day<Long, Long> {

    private final List<GridPoint> redTiles = new ArrayList<>();

  public Day9(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
      input.forEach(line -> {
          String[] split = line.split(",");
          redTiles.add(new GridPoint(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
      });
  }

  @Override
  public Long part1() {
    long largestArea = 0L;
      for (int i = 0; i < redTiles.size(); i++) {
          GridPoint point = redTiles.get(i);
          for (int j = i + 1; j < redTiles.size(); j++) {
              GridPoint otherPoint = redTiles.get(j);
            if (point.equals(otherPoint)) {
                continue;
            }
            if (point.x() == otherPoint.x() || point.y() == otherPoint.y()) {
                continue;
            }
            long area = (point.differenceX(otherPoint) + 1L) * (point.differenceY(otherPoint) + 1L);
            if (area > largestArea) {
                largestArea = area;
            }
        }
    }
    return largestArea;
  }

  @Override
  public Long part2() {
      // Get Ede tiles
      Set<GridPoint> edgeTiles = new HashSet<>(redTiles);
      for (int i = 0; i < redTiles.size()-1; i ++) {
          GridPoint point = redTiles.get(i);
          GridPoint otherPoint = redTiles.get(i+1);
          if (point.x() == otherPoint.x()) {
              int minY = Math.min(point.y(), otherPoint.y());
              int maxY = Math.max(point.y(), otherPoint.y());
              for (int y = minY; y <= maxY; y++) {
                  edgeTiles.add(new GridPoint(point.x(), y));
              }
          }
          if (point.y() == otherPoint.y()) {
              int minX = Math.min(point.x(), otherPoint.x());
              int maxX = Math.max(point.x(), otherPoint.x());
              for (int x = minX; x <= maxX; x++) {
                  edgeTiles.add(new GridPoint(x, point.y()));
              }
          }
      }
      GridPoint point = redTiles.getFirst();
      GridPoint otherPoint = redTiles.getLast();
      if (point.x() == otherPoint.x()) {
          int minY = Math.min(point.y(), otherPoint.y());
          int maxY = Math.max(point.y(), otherPoint.y());
          for (int y = minY; y <= maxY; y++) {
              edgeTiles.add(new GridPoint(point.x(), y));
          }
      }
      if (point.y() == otherPoint.y()) {
          int minX = Math.min(point.x(), otherPoint.x());
          int maxX = Math.max(point.x(), otherPoint.x());
          for (int x = minX; x <= maxX; x++) {
              edgeTiles.add(new GridPoint(x, point.y()));
          }
      }
      log.info("Edge tiles: {}", edgeTiles.size());

      // Calculate all areas up-front and order from largest to smallest area
      List<GridPoint> points = new ArrayList<>(redTiles);
      List<PairArea> pairs = new ArrayList<>();
      for (int i = 0; i < points.size(); i++) {
          GridPoint p = points.get(i);
          for (int j = i + 1; j < points.size(); j++) {
              GridPoint otherP = points.get(j);
              if (p.equals(otherP)) {
                  continue;
              }
              long area = (p.differenceX(otherP) + 1L) * (p.differenceY(otherP) + 1L);
              pairs.add(new PairArea(p, otherP, area));
          }
      }
      pairs.sort(Comparator.comparing(PairArea::area));
      pairs = pairs.reversed();
      log.info("Pairs: {}", pairs.size());

      // Return the largest area that has the valid pair of red tile corners
      for (PairArea pair : pairs) {
          int minX = Math.min(pair.redTile1().x(), pair.redTile2().x());
          int minY = Math.min(pair.redTile1().y(), pair.redTile2().y());
          int maxX = Math.max(pair.redTile1().x(), pair.redTile2().x());
          int maxY = Math.max(pair.redTile1().y(), pair.redTile2().y());
          if (edgeTiles.stream().anyMatch(e -> e.x() > minX && e.x() < maxX && e.y() < maxY && e.y() > minY)) {
              continue;
          }
          return  pair.area();
      }
      return -1L;
  }
}
