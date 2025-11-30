package dev.kmunton.year2025.day7;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7Test {

  private static Day7 day;

  @Test
  void part1_success() {
    List<String> input = """
        fill with test data""".lines().toList();
    day = new Day7(input);
    assertEquals(-1L, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        fill with test data""".lines().toList();
    day = new Day7(input);
    assertEquals(-1L, day.part2());
  }
}
