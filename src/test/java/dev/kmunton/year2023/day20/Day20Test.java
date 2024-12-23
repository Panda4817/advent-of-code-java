package dev.kmunton.year2023.day20;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day20Test {

  private static Day20 day;
  private static final List<String> INPUT = """
      broadcaster -> a, b, c
      %a -> b
      %b -> c
      %c -> inv
      &inv -> a
      """.lines().toList();

  @BeforeAll
  static void beforeAll() {
    day = new Day20(INPUT);
  }

  @Test
  void part1() {
    assertEquals(32000000, day.part1());

  }

  @Test
  void part1_2() {
    var input = """
        broadcaster -> a
        %a -> inv, con
        &inv -> b
        %b -> con
        &con -> output
        """.lines().toList();
    var day = new Day20(input);
    assertEquals(11687500, day.part1());

  }

  @Test
  void part2() {
    // no test data
    assertEquals(1, day.part2());
  }
}
