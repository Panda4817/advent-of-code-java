package dev.kmunton.days.day18;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day18Test {

  private static Day18 day;

  @Test
  void part1_success() {
    List<String> input = """
        5,4
        4,2
        4,5
        3,0
        2,1
        6,3
        2,4
        1,5
        0,6
        3,3
        2,6
        5,1
        1,2
        5,5
        2,5
        6,5
        1,4
        0,4
        6,4
        1,1
        6,1
        1,0
        0,5
        1,6
        2,0""".lines().toList();
    day = new Day18(input);
    assertEquals(22L, day.part1Test());
  }

  @Test
  void part2_success() {
    List<String> input = """
        5,4
        4,2
        4,5
        3,0
        2,1
        6,3
        2,4
        1,5
        0,6
        3,3
        2,6
        5,1
        1,2
        5,5
        2,5
        6,5
        1,4
        0,4
        6,4
        1,1
        6,1
        1,0
        0,5
        1,6
        2,0""".lines().toList();
    day = new Day18(input);
    assertEquals("(6, 1)", day.part2Test());
  }
}
