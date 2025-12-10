package dev.kmunton.year2025.day9;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day9Test {

  private static Day9 day;

  @BeforeEach
  void setUp() {
      List<String> input = """
            7,1
            11,1
            11,7
            9,7
            9,5
            2,5
            2,3
            7,3""".lines().toList();
      day = new Day9(input);
  }

  @Test
  void part1_success() {
    assertEquals(50L, day.part1());
  }

  @Test
  void part2_success() {
    assertEquals(24L, day.part2());
  }
}
