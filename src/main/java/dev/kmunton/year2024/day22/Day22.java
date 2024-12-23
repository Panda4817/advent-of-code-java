package dev.kmunton.year2024.day22;


import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day22 implements Day<Long, Long> {

  private final List<Long> numbers = new ArrayList<>();


  public Day22(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    input.forEach(line -> numbers.add(Long.parseLong(line)));
  }

  @Override
  public Long part1() {
    return numbers.stream().map(n -> {
      long secretNumber = n;
      for (int i = 0; i < 2000; i++) {
        secretNumber = nextSecretNumber(secretNumber);
      }
      return secretNumber;
    }).reduce(0L, Long::sum);
  }

  private long nextSecretNumber(long currentNumber) {
    long first = prune((currentNumber * 64) ^ currentNumber);
    long second = prune((first / 32) ^ first);
    return prune((second * 2048) ^ second);
  }

  private long prune(long secretNumber) {
    return secretNumber % 16777216;
  }

  private long getOneDigit(long number) {
    return number % 10;
  }

  record Price(long amount, long difference, List<Long> fourConsecutiveChanges) {

  }


  @Override
  public Long part2() {
    Map<List<Long>, Long> sequenceToSum = new HashMap<>();

    for (long number : numbers) {
      long prevDigit = getOneDigit(number);
      long secretNumber = number;
      List<Long> changes = new ArrayList<>();
      Set<List<Long>> visited = new HashSet<>();
      for (int i = 0; i < 2000; i++) {
        secretNumber = nextSecretNumber(secretNumber);
        long currentDigit = getOneDigit(secretNumber);
        long change = currentDigit - prevDigit;
        changes.add(change);

        if (changes.size() > 4) {
          changes.removeFirst();
        }

        if (changes.size() == 4 && !visited.contains(changes)) {
          visited.add(new ArrayList<>(changes));
          sequenceToSum.put(new ArrayList<>(changes), sequenceToSum.getOrDefault(new ArrayList<>(changes), 0L) + currentDigit);
        }

        prevDigit = currentDigit;
      }

    }
    return sequenceToSum.values().stream().mapToLong(s -> s).max().getAsLong();
  }

}
