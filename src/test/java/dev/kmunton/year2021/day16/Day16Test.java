package dev.kmunton.year2021.day16;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day16Test {

  private static Day16 day;

  @Test
  void part1_success() {
    List<String> input = """
        A0016C880162017C3686B18A3D4780""".lines().toList();
    day = new Day16(input);
    assertEquals(31L, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        9C0141080250320F1802104A08""".lines().toList();
    day = new Day16(input);
    assertEquals(1L, day.part2());
  }
}
