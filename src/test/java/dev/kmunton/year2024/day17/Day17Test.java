package dev.kmunton.year2024.day17;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day17Test {

  private static Day17 day;

  @Test
  void part1_success() {
    List<String> input = """
        Register A: 729
        Register B: 0
        Register C: 0
         
        Program: 0,1,5,4,3,0""".lines().toList();
    day = new Day17(input);
    assertEquals("4,6,3,5,6,3,5,2,1,0", day.part1());
  }

}
