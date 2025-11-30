package dev.kmunton.year2025.day10;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10Test {

  private static Day10 day;

  @Test
  void part1_success() {
    List<String> input = """
        fill with test data""".lines().toList();
    day = new Day10(input);
    assertEquals(-1L, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        fill with test data""".lines().toList();
    day = new Day10(input);
    assertEquals(-1L, day.part2());
  }
}
