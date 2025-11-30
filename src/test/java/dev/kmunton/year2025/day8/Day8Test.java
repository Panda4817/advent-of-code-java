package dev.kmunton.year2025.day8;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8Test {

  private static Day8 day;

  @Test
  void part1_success() {
    List<String> input = """
        fill with test data""".lines().toList();
    day = new Day8(input);
    assertEquals(-1L, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        fill with test data""".lines().toList();
    day = new Day8(input);
    assertEquals(-1L, day.part2());
  }
}
