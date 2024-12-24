package dev.kmunton.year2024.day24;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day24Test {

  private static Day24 day;

  @Test
  void part1_success() {
    List<String> input = """
        x00: 1
        x01: 1
        x02: 1
        y00: 0
        y01: 1
        y02: 0
                
        x00 AND y00 -> z00
        x01 XOR y01 -> z01
        x02 OR y02 -> z02""".lines().toList();
    day = new Day24(input);
    assertEquals(4L, day.part1());
  }

}
