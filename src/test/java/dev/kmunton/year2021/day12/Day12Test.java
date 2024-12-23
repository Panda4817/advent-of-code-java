package dev.kmunton.year2021.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day12Test {

  private static Day12 day;

  @Test
  void part1_success() {
    List<String> input = """
        start-A
        start-b
        A-c
        A-b
        b-d
        A-end
        b-end""".lines().toList();
    day = new Day12(input);
    assertEquals(10, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        start-A
        start-b
        A-c
        A-b
        b-d
        A-end
        b-end""".lines().toList();
    day = new Day12(input);
    assertEquals(36, day.part2());
  }

}
