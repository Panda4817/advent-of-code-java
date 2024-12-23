package dev.kmunton.year2021.day17;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day17Test {

  private static Day17 day;

  @Test
  void part1_success() {
    List<String> input = """
        target area: x=20..30, y=-10..-5""".lines().toList();
    day = new Day17(input);
    assertEquals(45, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        target area: x=20..30, y=-10..-5""".lines().toList();
    day = new Day17(input);
    assertEquals(112, day.part2());
  }

}
