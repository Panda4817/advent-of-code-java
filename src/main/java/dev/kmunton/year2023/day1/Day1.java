package dev.kmunton.year2023.day1;

import static java.lang.Integer.parseInt;

import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Day1 implements Day<Long, Long> {

  private final List<String> data = new ArrayList<>();

  public Day1(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    this.data.addAll(input);
  }

  public Long part1() {
    return (long) data.stream()
                      .map(s -> Arrays.stream(s.split(""))
                                      .filter(this::isNumber).toList())
                      .map(l -> l.get(0) + l.get(l.size() - 1))
                      .mapToInt(Integer::parseInt).sum();
  }

  public Long part2() {
    var numberMap = Map.of(
        "one", "1",
        "two", "2",
        "three", "3",
        "four", "4",
        "five", "5",
        "six", "6",
        "seven", "7",
        "eight", "8",
        "nine", "9"
    );

    var numberReverseMap = Map.of(
        "eno", "1",
        "owt", "2",
        "eerht", "3",
        "ruof", "4",
        "evif", "5",
        "xis", "6",
        "neves", "7",
        "thgie", "8",
        "enin", "9"
    );

    return (long) data.stream()
                      .map(s -> Arrays.stream(s.toLowerCase().split("")).toList())
                      .map(l -> {
                        // first
                        var firstDigit = getNumber(l, numberMap);

                        // last
                        List<String> reverseL = new ArrayList<>(l);
                        Collections.reverse(reverseL);
                        var lastDigit = getNumber(reverseL, numberReverseMap);

                        return firstDigit + lastDigit;
                      })
                      .mapToInt(Integer::parseInt).sum();

  }

  private String getNumber(List<String> list, Map<String, String> numberMap) {
    var word = "";
    for (String character : list) {
      if (isNumber(character)) {
        return character;
      }
      word += character;

      while (doesNotStartWithNumber(word, numberMap)) {
        if (word.length() == 1) {
          word = "";
          break;
        } else {
          word = word.substring(1);
        }
      }

      if (numberMap.containsKey(word)) {
        return numberMap.get(word);
      }
    }
    return "";
  }

  private boolean doesNotStartWithNumber(String s, Map<String, String> numberMap) {
    return numberMap.keySet().stream().noneMatch(n -> n.startsWith(s));
  }

  private boolean isNumber(String s) {
    try {
      parseInt(s);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

}
