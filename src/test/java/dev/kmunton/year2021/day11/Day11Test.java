package dev.kmunton.year2021.day11;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day11Test {

  private static Day11 day;

  @Test
  void part1_success() {
    List<String> input = """
        5483143223
        2745854711
        5264556173
        6141336146
        6357385478
        4167524645
        2176841721
        6882881134
        4846848554
        5283751526""".lines().toList();
    day = new Day11(input);
    assertEquals(1656, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        5483143223
        2745854711
        5264556173
        6141336146
        6357385478
        4167524645
        2176841721
        6882881134
        4846848554
        5283751526""".lines().toList();
    day = new Day11(input);
    assertEquals(195, day.part2Test());
  }

}
