package dev.kmunton.year2021.day17;

import dev.kmunton.utils.days.Day;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day17 implements Day<Integer, Integer> {

  private int startX;
  private int endX;
  private int startY;
  private int endY;

  public Day17(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    String[] parts = input.get(0).split(": ")[1].split(", ");
    List<Integer> xVals = Arrays.stream(parts[0].split("=")[1].split("\\.\\.")).map(Integer::parseInt).toList();
    List<Integer> yVals = Arrays.stream(parts[1].split("=")[1].split("\\.\\.")).map(Integer::parseInt).toList();
    startX = xVals.get(0);
    startY = yVals.get(1);
    endX = xVals.get(1);
    endY = yVals.get(0);
    log.info("startX: {}, endX: {}, startY: {}, endY: {}", startX, endX, startY, endY);
  }

  private boolean inTargetXY(int x, int y) {
    int sx = 0;
    int sy = 0;
    while (true) {
      sx += x;
      sy += y;
      if (sx >= startX && sx <= endX && sy <= startY && sy >= endY) {
        return true;
      } else if (sx > endX || sy < endY) {
        break;
      }
      if (x > 0) {
        x -= 1;
      }
      y -= 1;

    }
    return false;
  }

  @Override
  public Integer part1() {
    int maxY = Math.abs(endY) - 1;
    int start = 0;
    while (start > endY) {
      start += maxY;
      if (maxY > 0) {
        maxY -= 1;
      } else {
        break;
      }
    }
    return start;
  }

  @Override
  public Integer part2() {
    int minY = endY;
    int maxY = Math.abs(endY) - 1;

    int maxX = endX;
    int minX = 1;

    int count = 0;

    for (int x = minX; x < maxX + 1; x++) {
      for (int y = minY; y < maxY + 1; y++) {
        if (inTargetXY(x, y)) {
          count += 1;
        }
      }
    }

    return count;
  }
}
