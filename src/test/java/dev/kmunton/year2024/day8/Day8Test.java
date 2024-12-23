package dev.kmunton.year2024.day8;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day8Test {

  private static Day8 day;

  @Test
  void part1_success() {
    List<String> input = """
        ............
        ........0...
        .....0......
        .......0....
        ....0.......
        ......A.....
        ............
        ............
        ........A...
        .........A..
        ............
        ............""".lines().toList();
    day = new Day8(input);
    assertEquals(14L, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
         ............
        ........0...
        .....0......
        .......0....
        ....0.......
        ......A.....
        ............
        ............
        ........A...
        .........A..
        ............
        ............""".lines().toList();
    day = new Day8(input);
    assertEquals(34L, day.part2());
  }

}