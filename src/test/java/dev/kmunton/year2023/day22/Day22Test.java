package dev.kmunton.year2023.day22;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day22Test {

  private static Day22 day;
  private static final List<String> INPUT = """
      1,0,1~1,2,1
      0,0,2~2,0,2
      0,2,3~2,2,3
      0,0,4~0,2,4
      2,0,5~2,2,5
      0,1,6~2,1,6
      1,1,8~1,1,9
      """.lines().toList();

  @BeforeAll
  static void beforeAll() {
    day = new Day22(INPUT);
  }

  @Test
  void part1() {
    assertEquals(5, day.part1());

  }

  @Test
  void part2() {
    assertEquals(7, day.part2());
  }
}
