package dev.kmunton.days.day4;

import dev.kmunton.days.Day;
import dev.kmunton.utils.geometry.Direction2D;
import dev.kmunton.utils.geometry.GridPoint;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day4 implements Day<Long, Long> {

  private static final String X = "X";
  private static final String M = "M";
  private static final String A = "A";
  private static final String S = "S";

  private final Map<GridPoint, String> wordsearch = new HashMap<>();

  public Day4(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    for (int y = 0; y < input.size(); y++) {
      List<String> row = Arrays.stream(input.get(y).split("")).toList();
      for (int x = 0; x < row.size(); x++) {
        wordsearch.put(new GridPoint(x, y), row.get(x));
      }
    }
  }

  @Override
  public Long part1() {
    return wordsearch.entrySet().stream()
                     .filter(e -> e.getValue().equals(X))
                     .map(e -> findXmas(e.getKey(), e.getValue(), 0L, null))
                     .reduce(0L, Long::sum);
  }

  private long findXmas(GridPoint p, String letter, long words, Direction2D direction) {
    if (!wordsearch.containsKey(p)) {
      return words;
    }
    if (!wordsearch.get(p).equals(letter)) {
      return words;
    }
    if (wordsearch.get(p).equals(letter) && letter.equals(S)) {
      return words + 1L;
    }
    for (Entry<Direction2D, GridPoint> e : p.getAllNeighborsWithDirection().entrySet()) {
      if (direction == null || direction.equals(e.getKey())) {
        words = switch (letter) {
          case X -> findXmas(e.getValue(), M, words, e.getKey());
          case M -> findXmas(e.getValue(), A, words, e.getKey());
          case A -> findXmas(e.getValue(), S, words, e.getKey());
          default -> throw new IllegalStateException("Unexpected value: " + letter);
        };
      }
    }
    return words;
  }

  @Override
  public Long part2() {
    return wordsearch.entrySet().stream()
                     .filter(e -> e.getValue().equals(A))
                     .map(e -> e.getKey().getDiagonalNeighborsWithDirection().entrySet()
                                .stream().filter(entry -> wordsearch.containsKey(entry.getValue()))
                                .map(entry -> Map.entry(entry.getKey(), wordsearch.get(entry.getValue())))
                                .collect(Collectors.toMap(Entry::getKey, Entry::getValue)))
                     .filter(neighbors -> neighbors.size() == 4)
                     .filter(neighbors -> checkForMas(neighbors, M, S) || checkForMas(neighbors, S, M))
                     .count();
  }

  private boolean checkForMas(Map<Direction2D, String> neighbors, String letter1, String letter2) {
    if (neighbors.get(Direction2D.UP_LEFT).equals(letter1)
        && neighbors.get(Direction2D.UP_RIGHT).equals(S) && neighbors.get(Direction2D.DOWN_LEFT).equals(M)
        && neighbors.get(Direction2D.DOWN_RIGHT).equals(letter2)) {
      return true;
    }

    return neighbors.get(Direction2D.UP_LEFT).equals(letter2)
        && neighbors.get(Direction2D.UP_RIGHT).equals(M) && neighbors.get(Direction2D.DOWN_LEFT).equals(S)
        && neighbors.get(Direction2D.DOWN_RIGHT).equals(letter1);
  }

}
