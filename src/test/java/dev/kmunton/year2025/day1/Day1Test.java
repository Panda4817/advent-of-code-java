package dev.kmunton.year2025.day1;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {

  private static Day1 day;

  @Test
  void part1_success() {
    List<String> input = """
        fill with test data""".lines().toList();
    day = new Day1(input);
    assertEquals(-1L, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        fill with test data""".lines().toList();
    day = new Day1(input);
    assertEquals(-1L, day.part2());
  }
}
