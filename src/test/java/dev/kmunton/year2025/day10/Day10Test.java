package dev.kmunton.year2025.day10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10Test {

  private static Day10 day;

  @BeforeEach
  void setUp() {
      List<String> input = """
              [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
              [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
              [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}""".lines().toList();
      day = new Day10(input);
  }

  @Test
  void part1_success() {
    assertEquals(7L, day.part1());
  }

  @Test
  void part2_success() {
    assertEquals(33L, day.part2());
  }
}
