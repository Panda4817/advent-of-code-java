package dev.kmunton.year2024.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Day12Test {

  private static Day12 day;

  @ParameterizedTest
  @MethodSource("part1")
  void part1_success(String input, long expected) {
    day = new Day12(input.lines().toList());
    assertEquals(expected, day.part1());
  }

  private static Stream<Arguments> part1() {
    return Stream.of(
        Arguments.of("""
            RRRRIICCFF
            RRRRIICCCF
            VVRRRCCFFF
            VVRCCCJFFF
            VVVVCJJCFE
            VVIVCCJJEE
            VVIIICJJEE
            MIIIIIJJEE
            MIIISIJEEE
            MMMISSJEEE""", 1930L),
        Arguments.of("""
            OOOOO
            OXOXO
            OOOOO
            OXOXO
            OOOOO""", 772L
        ),
        Arguments.of("""
            AAAA
            BBCD
            BBCC
            EEEC""", 140L)

    );
  }

  @ParameterizedTest
  @MethodSource("part2")
  void part2_success(String input, long expected) {
    day = new Day12(input.lines().toList());
    assertEquals(expected, day.part2());
  }

  private static Stream<Arguments> part2() {
    return Stream.of(
        Arguments.of("""
            RRRRIICCFF
            RRRRIICCCF
            VVRRRCCFFF
            VVRCCCJFFF
            VVVVCJJCFE
            VVIVCCJJEE
            VVIIICJJEE
            MIIIIIJJEE
            MIIISIJEEE
            MMMISSJEEE""", 1206L),
        Arguments.of("""
            AAAAAA
            AAABBA
            AAABBA
            ABBAAA
            ABBAAA
            AAAAAA""", 368L
        ),
        Arguments.of("""
            OOOOO
            OXOXO
            OOOOO
            OXOXO
            OOOOO""", 436L),
    Arguments.of("""
            AAAA
            BBCD
            BBCC
            EEEC""", 80L),
        Arguments.of (
            """
                EEEEE
                EXXXX
                EEEEE
                EXXXX
                EEEEE""", 236L
        ),
        Arguments.of("""
            AAAA
            AAAA
            BABA
            BBAA""", 166L),
        Arguments.of("""
            AAAA
            AABA
            ABBB
            AABA""", 184L),
        Arguments.of(
            """
                CCCCCCCC
                CAAAAAAC
                CAAABBAC
                CAAABBAC
                CABBAAAC
                CABBAAAC
                CAAAAAAC
                CCCCCCCC""", 592
        )
    );
  }

}
