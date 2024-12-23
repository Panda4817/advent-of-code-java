package dev.kmunton.year2024.day22;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Day22Test {

  private static Day22 day;

  @ParameterizedTest
  @MethodSource("part1")
  void part1_success(String strInput, long expected) {
    List<String> input = strInput.lines().toList();
    day = new Day22(input);
    assertEquals(expected, day.part1());
  }

  private static Stream<Arguments> part1() {
    return Stream.of(
        Arguments.of("""
            123""", 1110806L),
        Arguments.of("""
            1
            10
            100
            2024""", 37327623L)

    );
  }

  @ParameterizedTest
  @MethodSource("part2")
  void part2_success(String strInput, long expected) {
    List<String> input = strInput.lines().toList();
    day = new Day22(input);
    assertEquals(expected, day.part2());
  }

  private static Stream<Arguments> part2() {
    return Stream.of(
        Arguments.of("""
            123""", 9L),
        Arguments.of("""
            1
            2
            3
            2024""", 23L)

    );
  }

}
