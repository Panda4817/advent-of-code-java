package dev.kmunton.year2021.day9;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day9Test {

  private static Day9 day;

  @Test
  void part1_success() {
    List<String> input = """
        2199943210
        3987894921
        9856789892
        8767896789
        9899965678""".lines().toList();
    day = new Day9(input);
    assertEquals(15, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        2199943210
        3987894921
        9856789892
        8767896789
        9899965678""".lines().toList();
    day = new Day9(input);
    assertEquals(1134, day.part2());
  }

}
