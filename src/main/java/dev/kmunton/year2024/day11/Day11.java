package dev.kmunton.year2024.day11;


import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day11 implements Day<Long, Long> {

  private final List<Long> stones = new ArrayList<>();

  public Day11(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    input.forEach(line -> Arrays.stream(line.split(" ")).map(Long::parseLong).forEach(stones::add));
  }

  @Override
  public Long part1() {
    return getTotalStonesAfterBlinks(25);
  }

  @Override
  public Long part2() {
    return getTotalStonesAfterBlinks(75);
  }

  private long getTotalStonesAfterBlinks(int blinks) {
    Map<Long, Long> stonesFrequencyMap = stones.stream().collect(Collectors.toMap(v -> v, v -> 1L));

    for (int i = 0; i < blinks; i++) {
      Map<Long, Long> temp = new HashMap<>();
      for (Entry<Long, Long> entry : stonesFrequencyMap.entrySet()) {
        List<Long> newStones = getNewStone(entry.getKey());
        for (Long newStone : newStones) {
          temp.put(newStone, temp.getOrDefault(newStone, 0L) + entry.getValue());
        }
      }
      stonesFrequencyMap = temp;
    }

    return stonesFrequencyMap.values().stream().reduce(0L, Long::sum);
  }

  private List<Long> getNewStone(long stone) {
    if (stone == 0) {
      return List.of(1L);
    }
    String stoneString = String.valueOf(stone);
    if (stoneString.length() % 2 == 0) {
      Long firstHalf = Long.valueOf(stoneString.substring(0, stoneString.length() / 2));
      Long secondHalf = Long.valueOf(stoneString.substring(stoneString.length() / 2));
      return List.of(firstHalf, secondHalf);
    }

    return List.of(stone * 2024);
  }

}
