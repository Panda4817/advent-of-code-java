package dev.kmunton.year2025.day5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day5Test {

  private static Day5 day;

  @BeforeEach
  void setUp() {
      List<String> input = """
            3-5
            10-14
            16-20
            12-18
            
            1
            5
            8
            11
            17
            32""".lines().toList();
      day = new Day5(input);
  }

  @Test
  void part1_success() {
    assertEquals(3L, day.part1());
  }

  @Test
  void part2_success() {
    assertEquals(14L, day.part2());
  }
}
