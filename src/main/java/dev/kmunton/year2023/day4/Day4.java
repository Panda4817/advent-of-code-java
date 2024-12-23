package dev.kmunton.year2023.day4;

import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day4 implements Day<Long, Long> {

  private final Map<Integer, List<Integer>> winningNumbers = new HashMap<>();
  private final Map<Integer, List<Integer>> scratchCardNumbers = new HashMap<>();

  public Day4(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    var cardNumber = 1;
    for (var line : input) {
      var lineSplit = line.split(":");
      var winningScratchCard = lineSplit[1].split(" \\| ");
      var winningNum = Arrays.stream(winningScratchCard[0].split(" "))
                             .map(String::trim)
                             .filter(s -> !s.isEmpty())
                             .map(Integer::parseInt).toList();
      var scratchCardNum = Arrays.stream(winningScratchCard[1].split(" "))
                                 .map(String::trim)
                                 .filter(s -> !s.isEmpty())
                                 .map(Integer::parseInt).toList();
      winningNumbers.put(cardNumber, winningNum);
      scratchCardNumbers.put(cardNumber, scratchCardNum);
      cardNumber++;
    }
  }

  public Long part1() {
    var sumOfPoints = 0;
    var cardToWinnings = getCardToWinnings();
    for (var entry : scratchCardNumbers.entrySet()) {
      var countOfWinningNumbers = cardToWinnings.get(entry.getKey());
      if (countOfWinningNumbers == 0) {
        continue;
      }
      var points = (int) Math.pow(2, (double) countOfWinningNumbers - 1);
      sumOfPoints += points;
    }

    return (long) sumOfPoints;

  }

  public Long part2() {
    var cardToWinnings = getCardToWinnings();
    var cards = new ArrayList<>(scratchCardNumbers.keySet().stream().map(card -> 1).toList());
    for (int i = 0; i < cards.size(); i++) {
      var matchingNumbers = cardToWinnings.get(i + 1);
      for (int j = i + 1; j <= i + matchingNumbers; j++) {
        if (j >= cards.size()) {
          break;
        }
        cards.set(j, cards.get(j) + cards.get(i));
      }
    }

    return cards.stream().mapToLong(Integer::longValue).sum();

  }


  private Map<Integer, Integer> getCardToWinnings() {
    var cardToWinnings = new HashMap<Integer, Integer>();
    for (var entry : scratchCardNumbers.entrySet()) {
      var winningNum = winningNumbers.get(entry.getKey());
      var countOfWinningNumbers = 0;
      for (int number : entry.getValue()) {
        if (winningNum.contains(number)) {
          countOfWinningNumbers++;
        }
      }
      cardToWinnings.put(entry.getKey(), countOfWinningNumbers);
    }
    return cardToWinnings;
  }
}
