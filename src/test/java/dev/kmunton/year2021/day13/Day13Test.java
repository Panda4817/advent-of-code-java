package dev.kmunton.year2021.day13;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day13Test {

  private static Day13 day;

  @Test
  void part1_success() {
    List<String> input = """
        6,10
        0,14
        9,10
        0,3
        10,4
        4,11
        6,0
        6,12
        4,1
        0,13
        10,12
        3,4
        3,0
        8,4
        1,10
        2,14
        8,10
        9,0
                
        fold along y=7
        fold along x=5""".lines().toList();
    day = new Day13(input);
    assertEquals(17, day.part1());
  }

}
