package dev.kmunton.year2023.day13;

import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.List;

public class Day13 implements Day<Long, Long> {

  private final List<Pattern> patterns = new ArrayList<>();

  public Day13(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    var rows = new ArrayList<String>();
    for (var line : input) {
      if (line.trim().isEmpty()) {
        patterns.add(new Pattern(rows));
        rows = new ArrayList<>();
        continue;
      }
      rows.add(line);
    }
    patterns.add(new Pattern(rows));
  }

  public Long part1() {
    var diff = 0;
    return (long) getSum(diff);

  }

  public Long part2() {
    var diff = 1;
    return (long) getSum(diff);
  }

  private int getSum(int allowedDiff) {
    var sum = 0;
    for (var pattern : patterns) {
      var rows = pattern.getRows();
      var horizontalReflection = getReflection(rows, allowedDiff);
      if (horizontalReflection >= 0) {
        sum += (100 * horizontalReflection);
        continue;
      }
      var cols = pattern.getCols();
      var verticalReflection = getReflection(cols, allowedDiff);
      if (verticalReflection >= 0) {
        sum += verticalReflection;
      }
    }

    return sum;
  }

  private int getReflection(List<String> lines, int allowedDiff) {
    var m = lines.size();
    for (var i = 0; i < m - 1; i++) {
      var diff = 0;
      var len = lines.get(i).length();

      var r1 = i;
      var r2 = i + 1;
      while (r1 >= 0 && r2 < m) {
        for (var j = 0; j < len; j++) {
          if (lines.get(r1).charAt(j) != lines.get(r2).charAt(j)) {
            diff++;
          }
          if (diff > allowedDiff) {
            break;
          }
        }
        if (diff > allowedDiff) {
          break;
        }
        r1--;
        r2++;

      }
      if (diff != allowedDiff) {
        continue;
      }

      return i + 1;

    }
    return -1;
  }
}


