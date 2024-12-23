package dev.kmunton.year2023.day6;

import dev.kmunton.utils.days.Day;
import java.util.Arrays;
import java.util.List;

public class Day6 implements Day<Long, Long> {

  private List<Long> times;
  private List<Long> distances;

  public Day6(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    times = parseLine(input.get(0), "Time");
    distances = parseLine(input.get(1), "Distance");
  }

  private List<Long> parseLine(String line, String lineName) {
    return Arrays.stream(line.split(" "))
                 .map(String::trim)
                 .filter(s -> !s.isBlank())
                 .filter(s -> !s.equals(lineName + ":"))
                 .map(Long::parseLong).toList();
  }

  public Long part1() {
    var marginOfError = 1L;
    for (int i = 0; i < times.size(); i++) {
      var time = times.get(i);
      var distance = distances.get(i);
      marginOfError *= getCountOfWaysToWin(time, distance);
    }
    return marginOfError;

  }

  public Long part2() {
    var time = times.stream()
                    .map(String::valueOf)
                    .reduce((a, b) -> a + b)
                    .map(Long::parseLong).orElseThrow();
    var distance = distances.stream()
                            .map(String::valueOf)
                            .reduce((a, b) -> a + b)
                            .map(Long::parseLong).orElseThrow();
    return getCountOfWaysToWin(time, distance);
  }

  private long getCountOfWaysToWin(long time, long distance) {
    var marginOfError = 1L;
    var countOfWins = 0L;
    for (long j = 1; j < time; j++) {
      var timeLeft = time - j;
      var distanceTravelled = j * timeLeft;
      if (distanceTravelled > distance) {
        countOfWins++;
      }
    }
    marginOfError *= countOfWins;
    return marginOfError;
  }
}
