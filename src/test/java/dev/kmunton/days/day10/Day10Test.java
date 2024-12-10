package dev.kmunton.days.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Day10Test {

  private static Day10 day;

  @ParameterizedTest
  @MethodSource("part1")
  void part1_success(String input, long expected) {
    day = new Day10(input.lines().toList());
    assertEquals(expected, day.part1());
  }

  private static Stream<Arguments> part1() {
    return Stream.of(
        Arguments.of("""
            89010123
            78121874
            87430965
            96549874
            45678903
            32019012
            01329801
            10456732""", 36L),
        Arguments.of("""
                0123
                1234
                8765
                9876""", 1L
        ),
        Arguments.of("""
            9990999
            9991998
            9992997
            6543456
            7659987
            8769919
            9879999""", 4L)
    );
  }

  @ParameterizedTest
  @MethodSource("part2")
  void part2_success(String input, long expected) {
    day = new Day10(input.lines().toList());
    assertEquals(expected, day.part2());
  }

  private static Stream<Arguments> part2() {
    return Stream.of(
        Arguments.of("""
            89010123
            78121874
            87430965
            96549874
            45678903
            32019012
            01329801
            10456732""", 81L),
        Arguments.of("""
            9990999
            9991998
            9992997
            6543456
            7659987
            8769919
            9879999""", 13L)
    );
  }

}
