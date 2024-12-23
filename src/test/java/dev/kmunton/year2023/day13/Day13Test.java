package dev.kmunton.year2023.day13;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day13Test {

  private static Day13 day;
  private static final List<String> INPUT = """
      #.##..##.
      ..#.##.#.
      ##......#
      ##......#
      ..#.##.#.
      ..##..##.
      #.#.##.#.
              
      #...##..#
      #....#..#
      ..##..###
      #####.##.
      #####.##.
      ..##..###
      #....#..#
      """.lines().toList();

  @BeforeAll
  static void beforeAll() {
    day = new Day13(INPUT);
  }

  @Test
  void part1() {
    assertEquals(405, day.part1());

  }

  @Test
  void part2() {
    assertEquals(400, day.part2());
  }
}
