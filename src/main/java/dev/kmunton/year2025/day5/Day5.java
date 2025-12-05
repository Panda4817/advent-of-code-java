package dev.kmunton.year2025.day5;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.Range;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day5 implements Day<Long, Long> {

    private final Set<Range> idRanges = new HashSet<>();
    private final List<Long> ids = new ArrayList<>();

  public Day5(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
      boolean atIdList = false;
      for  (String line : input) {
          if (line.isEmpty()) {
              atIdList = true;
              continue;
          }
          if (atIdList) {
              ids.add(Long.parseLong(line));
              continue;
          }
          final String[] split = line.split("-");
          final Range range = new Range(Long.parseLong(split[0]), Long.parseLong(split[1]));
          idRanges.add(range);
      }
  }

  @Override
  public Long part1() {
      long freshIds = 0;
      for  (final Long id : ids) {
          if (isFresh(id)) {
              freshIds++;
          }
      }
    return freshIds;
  }

  private boolean isFresh(final long id) {
      for  (final Range range : idRanges) {
          if (range.contains(id)) {
            return true;
          }
      }
      return false;
  }

  @Override
  public Long part2() {
      long freshIds = 0;
      final Set<Range> rangesDone = new HashSet<>();
      for (final Range range : idRanges) {
          if (rangesDone.contains(range)) {
              continue;
          }
          rangesDone.add(range);
          Range unionedRange = range;
          boolean foundOverlap = false;
          do {
              foundOverlap = false;
              for (final Range secondRange : idRanges) {
                  if (rangesDone.contains(secondRange)) {
                      continue;
                  }
                  if (unionedRange.overlaps(secondRange)) {
                      unionedRange = unionedRange.union(secondRange);
                      rangesDone.add(secondRange);
                      foundOverlap = true;
                  }
              }
          } while (foundOverlap);
          freshIds += (unionedRange.length() + 1);
      }

    return freshIds;
  }

}
