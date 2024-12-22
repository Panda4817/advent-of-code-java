package dev.kmunton.days.day22;


import dev.kmunton.days.Day;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day22 implements Day<Long, Long> {

  private final List<Long> numbers = new ArrayList<>();
  private final Map<String, Long> cache = new HashMap<>();


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
    Map<Long, List<Price>> priceMap = new HashMap<>();
    Set<List<Long>> distinctChanges = new HashSet<>();

    for (long number : numbers) {
      List<Price> seq = new ArrayList<>();
      long prevDigit = getOneDigit(number);
      seq.add(new Price(prevDigit, 0, new ArrayList<>()));
      long secretNumber = number;
      List<Long> changes = new ArrayList<>();

      for (int i = 0; i < 2000; i++) {
        secretNumber = nextSecretNumber(secretNumber);
        long currentDigit = getOneDigit(secretNumber);
        long change = currentDigit - prevDigit;
        changes.add(change);

        if (changes.size() > 4) {
          changes.removeFirst();
        }

        seq.add(new Price(currentDigit, change, new ArrayList<>(changes)));
        prevDigit = currentDigit;
      }

      seq = seq.stream()
               .filter(p -> p.fourConsecutiveChanges.size() == 4)
               .toList();
      priceMap.put(number, seq);

      seq.forEach(p -> distinctChanges.add(new ArrayList<>(p.fourConsecutiveChanges)));
    }

    Set<List<Long>> filtered = distinctChanges.stream()
                                              .filter(change -> change.stream().mapToLong(i -> i).sum() > 0)
                                              .collect(Collectors.toSet());
    log.info("toCheck: {}", filtered.size());
    long currentMax = 0;

    for (List<Long> change : filtered) {
      long maxPrice = 0;

      for (Long number : numbers) {

        maxPrice += cache.computeIfAbsent(number.toString() + change.toString(), k -> {
          OptionalLong optionalPrice = priceMap.get(number).stream()
                                               .filter(p -> p.fourConsecutiveChanges.equals(change))
                                               .mapToLong(p -> p.amount)
                                               .findFirst();
          return optionalPrice.isPresent() ? optionalPrice.getAsLong() : 0L;
        });
      }

      if (maxPrice > currentMax) {
        currentMax = maxPrice;
        // This prints out the current sequence of changes and the maximum total price at that sequence - found the answer to part 2 from this
        log.info("{}: {}", change, currentMax);
      }
    }

    return currentMax;
  }


}
