package dev.kmunton.year2021.day23;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day23Test {

  private static Day23 day;

  @Test
  void part1_success() {
    List<String> input = """
        #############
        #...........#
        ###B#C#B#D###
          #A#D#C#A#
          #########""".lines().toList();
    day = new Day23(input);
    assertEquals(12521, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        #############
        #...........#
        ###B#C#B#D###
          #A#D#C#A#
          #########""".lines().toList();
    day = new Day23(input);
    assertEquals(44169, day.part2());
  }
}
