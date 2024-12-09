package dev.kmunton.days.day9;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day9Test {

  private static Day9 day;

  @Test
  void part1_success() {
    List<String> input = """
        2333133121414131402""".lines().toList();
    day = new Day9(input);
    assertEquals(1928L, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        2333133121414131402""".lines().toList();
    day = new Day9(input);
    assertEquals(2858L, day.part2());
  }

}
