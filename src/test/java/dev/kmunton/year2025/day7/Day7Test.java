package dev.kmunton.year2025.day7;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7Test {

  private static Day7 day;

  @BeforeEach
  void setUp() {
      List<String> input = """
            .......S.......
            ...............
            .......^.......
            ...............
            ......^.^......
            ...............
            .....^.^.^.....
            ...............
            ....^.^...^....
            ...............
            ...^.^...^.^...
            ...............
            ..^...^.....^..
            ...............
            .^.^.^.^.^...^.
            ...............""".lines().toList();
      day = new Day7(input);
  }

  @Test
  void part1_success() {
    assertEquals(21L, day.part1());
  }

  @Test
  void part2_success() {
    assertEquals(40L, day.part2());
  }
}
