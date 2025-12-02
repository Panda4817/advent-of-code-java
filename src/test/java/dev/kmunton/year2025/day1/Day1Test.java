package dev.kmunton.year2025.day1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {

  private static Day1 day;

  @BeforeEach
  void setup() {
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
  }

  @Test
  void part1_success() {
    assertEquals(3L, day.part1());
  }

  @Test
  void part2_success() {
    assertEquals(6L, day.part2());
  }
}
