package dev.kmunton.days.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day3Test {

  private static Day3 day;

  @Test
  void part1_success() {
    List<String> input = """
             xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))""".lines().toList();
    day =  new Day3(input);
    assertEquals(161L, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
             xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))""".lines().toList();
    day =  new Day3(input);
    assertEquals(48L, day.part2());
  }

}
