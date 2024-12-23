package dev.kmunton.year2023.day14;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day14Test {

  private static Day14 day;
  private static final List<String> INPUT = """
      O....#....
      O.OO#....#
      .....##...
      OO.#O....O
      .O.....O#.
      O.#..O.#.#
      ..O..#O..O
      .......O..
      #....###..
      #OO..#....
      """.lines().toList();

  @BeforeAll
  static void beforeAll() {
    day = new Day14(INPUT);
  }

  @Test
  void part1() {
    assertEquals(136, day.part1());

  }

  @Test
  void part2() {
    assertEquals(64, day.part2());
  }
}
