package dev.kmunton.year2023.day8;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day8Test {

  @Test
  void part1() {
    var input = """
        LLR
                
        AAA = (BBB, BBB)
        BBB = (AAA, ZZZ)
        ZZZ = (ZZZ, ZZZ)
        """.lines().toList();
    var day = new Day8(input);
    assertEquals(6, day.part1());

  }

  @Test
  void part2() {
    var input = """
        LR
                    
        11A = (11B, XXX)
        11B = (XXX, 11Z)
        11Z = (11B, XXX)
        22A = (22B, XXX)
        22B = (22C, 22C)
        22C = (22Z, 22Z)
        22Z = (22B, 22B)
        XXX = (XXX, XXX)
        """.lines().toList();
    var day = new Day8(input);
    assertEquals(6, day.part2());
  }
}
