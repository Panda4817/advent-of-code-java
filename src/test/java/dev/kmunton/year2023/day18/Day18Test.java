package dev.kmunton.year2023.day18;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day18Test {

  private static Day18 day;
  private static final List<String> INPUT = """
      R 6 (#70c710)
      D 5 (#0dc571)
      L 2 (#5713f0)
      D 2 (#d2c081)
      R 2 (#59c680)
      D 2 (#411b91)
      L 5 (#8ceee2)
      U 2 (#caa173)
      L 1 (#1b58a2)
      U 2 (#caa171)
      R 2 (#7807d2)
      U 3 (#a77fa3)
      L 2 (#015232)
      U 2 (#7a21e3)
      """.lines().toList();

  @BeforeAll
  static void beforeAll() {
    day = new Day18(INPUT);
  }

  @Test
  void part1() {
    assertEquals(62, day.part1());

  }

  @Test
  void part2() {
    assertEquals(952408144115L, day.part2());
  }
}
