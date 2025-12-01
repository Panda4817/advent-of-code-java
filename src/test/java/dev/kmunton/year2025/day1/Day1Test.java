package dev.kmunton.year2025.day1;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {

  private static Day1 day;

  @Test
  void part1_success() {
    List<String> input = """
            L68
            L30
            R48
            L5
            R60
            L55
            L1
            L99
            R14
            L82""".lines().toList();
    day = new Day1(input);
    assertEquals(3L, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
            L68
            L30
            R48
            L5
            R60
            L55
            L1
            L99
            R14
            L82""".lines().toList();
    day = new Day1(input);
    assertEquals(6L, day.part2());
  }
}
