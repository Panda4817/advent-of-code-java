package dev.kmunton.days.day23;


import static dev.kmunton.utils.data.CollectionsUtils.generateCombinations;

import dev.kmunton.days.Day;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day23 implements Day<Long, String> {

  private final Map<String, List<String>> networkMap = new HashMap<>();


  public Day23(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    input.forEach(line -> {
      String[] computers = line.split("-");
      List<String> computerZeroConnectToList = networkMap.getOrDefault(computers[0], new ArrayList<>());
      List<String> computerOneConnectedToList = networkMap.getOrDefault(computers[1], new ArrayList<>());
      computerOneConnectedToList.add(computers[0]);
      networkMap.put(computers[1], computerOneConnectedToList);
      computerZeroConnectToList.add(computers[1]);
      networkMap.put(computers[0], computerZeroConnectToList);
    });
  }

  @Override
  public Long part1() {
    Set<Set<String>> allSets = new HashSet<>();
    for (Entry<String, List<String>> entry : networkMap.entrySet()) {
      List<String> connected = entry.getValue();
      List<List<String>> combinations = generateCombinations(connected, 2);
      for (List<String> combination : combinations) {
        String firstComputer = combination.get(0);
        String secondComputer = combination.get(1);
        List<String> firstComputerConnectedToList = networkMap.get(firstComputer);
        if (firstComputerConnectedToList.contains(secondComputer)) {
          allSets.add(Set.of(entry.getKey(), firstComputer, secondComputer));
        }
      }
    }
    return allSets.stream().filter(s -> s.stream().anyMatch(c -> c.startsWith("t"))).count();
  }


  @Override
  public String part2() {
    Set<Set<String>> allCliques = new HashSet<>();
    for (String key : networkMap.keySet()) {
      Set<String> current = new HashSet<>();
      current.add(key);
      for (Entry<String, List<String>> entry : networkMap.entrySet()) {
        if (key.equals(entry.getKey())) {
          continue;
        }
        boolean belongsToClique = true;
        for (String computer : current) {
          if (!entry.getValue().contains(computer)) {
            belongsToClique = false;
            break;
          }
        }
        if (belongsToClique) {
          current.add(entry.getKey());
        }
      }
      allCliques.add(current);
    }
    return String.join(",", allCliques.stream().toList()
                                      .stream().sorted(Comparator.comparing(Set::size)).toList()
                                      .getLast().stream().sorted().toList());
  }

}
