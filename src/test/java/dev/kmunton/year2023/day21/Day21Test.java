package dev.kmunton.year2023.day21;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day21Test {

  private static Day21 day;
  private static final List<String> INPUT = """
      ...........
      .....###.#.
      .###.##..#.
      ..#.#...#..
      ....#.#....
      .##..S####.
      .##..#...#.
      .......##..
      .##.#.####.
      .##..##.##.
      ...........
      """.lines().toList();

  @BeforeAll
  static void beforeAll() {
    day = new Day21(INPUT);
  }

  @Test
  void part1() {
    assertEquals(16, day.calculateGardenPlotsBySteps(6));
  }

  @Test
  void part2() {
    assertEquals(16, day.calculateGardenPlotsByStepsForInfiniteGarden(6));
    assertEquals(50, day.calculateGardenPlotsByStepsForInfiniteGarden(10));
    assertEquals(1594, day.calculateGardenPlotsByStepsForInfiniteGarden(50));
  }
}
