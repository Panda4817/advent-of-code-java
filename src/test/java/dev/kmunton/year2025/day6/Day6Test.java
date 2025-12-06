package dev.kmunton.year2025.day6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day6Test {

  private static Day6 day;

  @BeforeEach
  void setUp() {
      List<String> input = """
            123 328  51 64\s
             45 64  387 23\s
              6 98  215 314
            *   +   *   +\s\s""".lines().toList();
      day = new Day6(input);
  }

  @Test
  void part1_success() {
    assertEquals(4277556L, day.part1());
  }

  @Test
  void part2_success() {
    assertEquals(3263827L, day.part2());
  }
}
