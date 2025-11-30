package dev.kmunton.year2025.day4;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4Test {

  private static Day4 day;

  @Test
  void part1_success() {
    List<String> input = """
        fill with test data""".lines().toList();
    day = new Day4(input);
    assertEquals(-1L, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        fill with test data""".lines().toList();
    day = new Day4(input);
    assertEquals(-1L, day.part2());
  }
}
