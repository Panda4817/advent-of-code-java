package dev.kmunton.year2021.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day1Test {

  private static Day1 day;

  @Test
  void part1_success() {
    List<String> input = """
        199
        200
        208
        210
        200
        207
        240
        269
        260
        263""".lines().toList();
    day = new Day1(input);
    assertEquals(7, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        199
        200
        208
        210
        200
        207
        240
        269
        260
        263""".lines().toList();
    day = new Day1(input);
    assertEquals(5, day.part2());
  }

}
