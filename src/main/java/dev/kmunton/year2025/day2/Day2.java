package dev.kmunton.year2025.day2;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.Range;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class Day2 implements Day<Long, Long> {

    private final List<Range> ranges = new ArrayList<>();

  public Day2(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
      Arrays.stream(input.getFirst().split(",")).forEach(range -> {
          String[] rangeArray = range.split("-");
          ranges.add(new Range(Long.parseLong(rangeArray[0]), Long.parseLong(rangeArray[1])));
      });
  }

  @Override
  public Long part1() {
      long invalidProductIdsSum = 0;
      for (final Range range : ranges) {
          for (long i = range.min(); i <= range.max(); i++) {
              final String productId = Long.toString(i);
              if (productId.length() % 2 != 0) {
                  continue;
              }
              int mid = productId.length() / 2;
              if (productId.substring(0,mid).equals(productId.substring(mid))) {
                  invalidProductIdsSum += i;
              }
          }
      }
    return invalidProductIdsSum;
  }

  @Override
  public Long part2() {
      long invalidProductIdsSum = 0;
      for (Range range : ranges) {
          for (long i = range.min(); i <= range.max(); i++) {
              final String productId = Long.toString(i);
              if (productId.matches("^(.+?)\\1+$")) {
                  invalidProductIdsSum += i;
              }
          }
      }
      return invalidProductIdsSum;
  }

}
