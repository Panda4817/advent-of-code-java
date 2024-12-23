package dev.kmunton.year2021.day3;


import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day3 implements Day<Integer, Integer> {

  private final List<String> data = new ArrayList<>();
  private final int length;

  public Day3(List<String> input) {
    processData(input);
    length = data.get(0).length();
  }

  @Override
  public void processData(List<String> input) {
    input.forEach(s -> data.add(s));
  }

  private Map<Integer, Long> bitCount(int index, List<String> lst) {
    long ones = lst.stream().filter(s -> s.charAt(index) == '1').count();
    long zeros = lst.stream().filter(s -> s.charAt(index) == '0').count();

    Map<Integer, Long> map = new HashMap<>();
    map.put(1, ones);
    map.put(0, zeros);
    return map;
  }

  @Override
  public Integer part1() {
    StringBuilder gammaRate = new StringBuilder();
    StringBuilder epsilonRate = new StringBuilder();

    for (int i = 0; i < length; i++) {
      Map<Integer, Long> map = bitCount(i, data);

      if (map.get(1) > map.get(0)) {
        gammaRate.append('1');
        epsilonRate.append('0');
      } else {
        epsilonRate.append('1');
        gammaRate.append('0');
      }
    }

    int gRate = Integer.parseInt(gammaRate.toString(), 2);
    int eRate = Integer.parseInt(epsilonRate.toString(), 2);

    return gRate * eRate;
  }

  private Boolean isOneLeft(List<String> arr) {
    return arr.size() == 1;
  }

  private List<String> filterBy(char c, List<String> lst, int index) {
    return lst.stream().filter(s -> s.charAt(index) == c).collect(Collectors.toCollection(ArrayList::new));
  }

  @Override
  public Integer part2() {
    String oxygenRating;
    String co2Rating;

    List<String> oxygenArr = new ArrayList<>(data);
    int i = 0;
    while (!isOneLeft(oxygenArr) && i != length) {
      Map<Integer, Long> map = bitCount(i, oxygenArr);

      if (map.get(1) >= map.get(0)) {
        oxygenArr = filterBy('1', oxygenArr, i);
      } else {
        oxygenArr = filterBy('0', oxygenArr, i);
      }

      i += 1;
    }

    oxygenRating = oxygenArr.get(0);

    List<String> co2Arr = new ArrayList<>(data);
    i = 0;
    while (!isOneLeft(co2Arr) && i != length) {
      Map<Integer, Long> map = bitCount(i, co2Arr);

      if (map.get(0) <= map.get(1)) {
        co2Arr = filterBy('0', co2Arr, i);
      } else {
        co2Arr = filterBy('1', co2Arr, i);
      }

      i += 1;
    }

    co2Rating = co2Arr.get(0);

    int oRate = Integer.parseInt(oxygenRating, 2);
    int cRate = Integer.parseInt(co2Rating, 2);

    return oRate * cRate;

  }
}
