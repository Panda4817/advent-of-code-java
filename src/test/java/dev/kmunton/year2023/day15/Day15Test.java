package dev.kmunton.year2023.day15;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day15Test {

  private static Day15 day;
  private static final List<String> INPUT = """
      rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7
      """.lines().toList();

  @BeforeAll
  static void beforeAll() {
    day = new Day15(INPUT);
  }

  @Test
  void part1() {
    assertEquals(1320, day.part1());

  }

  @Test
  void part2() {
    assertEquals(145, day.part2());
  }
}
