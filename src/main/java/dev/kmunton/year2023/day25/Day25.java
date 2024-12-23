package dev.kmunton.year2023.day25;

import dev.kmunton.utils.days.Day;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.alg.StoerWagnerMinimumCut;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.Multigraph;

public class Day25 implements Day<Long, Long> {

  private final Map<String, Set<String>> connections = new HashMap<>();
  private final Set<String> components = new HashSet<>();

  public Day25(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    for (var line : input) {
      var parts = line.split(": ");
      var key = parts[0];
      var values = parts[1].split(" ");
      if (connections.containsKey(key)) {
        connections.get(key).addAll(Arrays.asList(values));
      } else {
        connections.put(key, new HashSet<>(Arrays.asList(values)));
      }
      for (var value : values) {
        if (connections.containsKey(value)) {
          connections.get(value).add(key);
        } else {
          connections.put(value, new HashSet<>(Collections.singletonList(key)));
        }
      }
      components.add(key);
      components.addAll(Arrays.stream(values).toList());
    }
  }

  public Long part1() {
    Graph<String, DefaultEdge> stringGraph = new Multigraph<>(DefaultEdge.class);
    for (var s : components) {
      stringGraph.addVertex(s);
    }
    for (var key : connections.keySet()) {
      for (var value : connections.get(key)) {
        stringGraph.addEdge(key, value);
      }
    }

    var s = new StoerWagnerMinimumCut<>(stringGraph);
    var v = s.minCut();
    return (long) v.size() * (components.size() - v.size());

  }

  public Long part2() {
    return 0L;
  }
}
