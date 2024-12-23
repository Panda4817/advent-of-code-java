package dev.kmunton.year2024.day6;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day6Test {

  private static Day6 day;

  @Test
  void part1_success() {
    List<String> input = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...""".lines().toList();
    day = new Day6(input);
    assertEquals(41L, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...""".lines().toList();
    day = new Day6(input);
    assertEquals(6L, day.part2());
  }

}
