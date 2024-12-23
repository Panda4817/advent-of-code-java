package dev.kmunton.year2024.day13;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day13Test {

  private static Day13 day;

  @Test
  void part1_success() {
    List<String> input = """
        Button A: X+94, Y+34
        Button B: X+22, Y+67
        Prize: X=8400, Y=5400
                
        Button A: X+26, Y+66
        Button B: X+67, Y+21
        Prize: X=12748, Y=12176
                
        Button A: X+17, Y+86
        Button B: X+84, Y+37
        Prize: X=7870, Y=6450
                
        Button A: X+69, Y+23
        Button B: X+27, Y+71
        Prize: X=18641, Y=10279""".lines().toList();
    day = new Day13(input);
    assertEquals(480L, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        Button A: X+94, Y+34
        Button B: X+22, Y+67
        Prize: X=8400, Y=5400
                
        Button A: X+26, Y+66
        Button B: X+67, Y+21
        Prize: X=12748, Y=12176
                
        Button A: X+17, Y+86
        Button B: X+84, Y+37
        Prize: X=7870, Y=6450
                
        Button A: X+69, Y+23
        Button B: X+27, Y+71
        Prize: X=18641, Y=10279""".lines().toList();
    day = new Day13(input);
    assertEquals(875318608908L, day.part2());
  }

}
