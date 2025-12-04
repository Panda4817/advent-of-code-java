package dev.kmunton.year2025.day4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4Test {

  private static Day4 day;

  @BeforeEach
  void setUp() {
      List<String> input = """
            ..@@.@@@@.
            @@@.@.@.@@
            @@@@@.@.@@
            @.@@@@..@.
            @@.@@@@.@@
            .@@@@@@@.@
            .@.@.@.@@@
            @.@@@.@@@@
            .@@@@@@@@.
            @.@.@@@.@.""".lines().toList();
      day = new Day4(input);
  }

  @Test
  void part1_success() {
    assertEquals(13L, day.part1());
  }

  @Test
  void part2_success() {
    assertEquals(43L, day.part2());
  }
}
