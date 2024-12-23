package dev.kmunton.year2023.day11;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day11Test {

  private static Day11 day;
  private static final List<String> INPUT = """
      ...#......
      .......#..
      #.........
      ..........
      ......#...
      .#........
      .........#
      ..........
      .......#..
      #...#.....
      """.lines().toList();

  @BeforeAll
  static void beforeAll() {
    day = new Day11(INPUT);
  }

  @Test
  void part1() {
    assertEquals(374, day.part1());

  }

  @Test
  void part2() {
    assertEquals(1030, day.getTotalSteps(9));
    assertEquals(8410, day.getTotalSteps(99));
  }
}
