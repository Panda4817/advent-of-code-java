package dev.kmunton.year2023.day17;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day17Test {

  private static Day17 day;
  private static final List<String> INPUT = """
      2413432311323
      3215453535623
      3255245654254
      3446585845452
      4546657867536
      1438598798454
      4457876987766
      3637877979653
      4654967986887
      4564679986453
      1224686865563
      2546548887735
      4322674655533
      """.lines().toList();

  @BeforeAll
  static void beforeAll() {
    day = new Day17(INPUT);
  }

  @Test
  void part1() {
    assertEquals(102, day.part1());

  }

  @Test
  void part2() {
    assertEquals(94, day.part2());
  }

  @Test
  void part2_2() {
    List<String> input = """
        111111111111
        999999999991
        999999999991
        999999999991
        999999999991
        """.lines().toList();
    var day = new Day17(input);
    assertEquals(71, day.part2());
  }
}
