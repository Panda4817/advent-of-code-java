package dev.kmunton.year2023.day7;

import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day7 implements Day<Long, Long> {

  private final List<Hand> handsNormal = new ArrayList<>();
  private final List<Hand> handsJoker = new ArrayList<>();

  public Day7(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    for (String line : input) {
      var parts = line.split(" ");
      handsNormal.add(new Hand(parts[0], Long.parseLong(parts[1]), false));
      handsJoker.add(new Hand(parts[0], Long.parseLong(parts[1]), true));
    }
  }

  public Long part1() {
    sortHands(handsNormal);
    return getWinnings(handsNormal);
  }

  public Long part2() {
    sortHands(handsJoker);
    return getWinnings(handsJoker);
  }

  private void sortHands(List<Hand> hands) {
    hands.sort(Comparator.comparing(Hand::getType)
                         .thenComparing((Hand h) -> h.getCards().get(0))
                         .thenComparing((Hand h) -> h.getCards().get(1))
                         .thenComparing((Hand h) -> h.getCards().get(2))
                         .thenComparing((Hand h) -> h.getCards().get(3))
                         .thenComparing((Hand h) -> h.getCards().get(4))
                         .reversed());
  }

  private long getWinnings(List<Hand> hands) {
    var winnings = 0L;
    var rank = hands.size();
    for (var hand : hands) {
      winnings += hand.getBid() * rank;
      rank--;
    }
    return winnings;
  }
}
