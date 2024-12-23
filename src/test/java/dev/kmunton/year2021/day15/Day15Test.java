package dev.kmunton.year2021.day15;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day15Test {

  private static Day15 day;

  @Test
  void part1_success() {
    List<String> input = """
        1163751742
        1381373672
        2136511328
        3694931569
        7463417111
        1319128137
        1359912421
        3125421639
        1293138521
        2311944581""".lines().toList();
    day = new Day15(input);
    assertEquals(40, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        1163751742
        1381373672
        2136511328
        3694931569
        7463417111
        1319128137
        1359912421
        3125421639
        1293138521
        2311944581""".lines().toList();
    day = new Day15(input);
    assertEquals(315, day.part2());
  }

}
