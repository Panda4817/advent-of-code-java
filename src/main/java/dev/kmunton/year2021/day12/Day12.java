package dev.kmunton.year2021.day12;

import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day12 implements Day<Integer, Integer> {

  private final Graph graph = new Graph();

  public Day12(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    input.forEach(s -> {
      String[] caves = s.split("-");
      graph.addVertex(caves[0]);
      graph.addVertex(caves[1]);
      graph.addEdge(caves[0], caves[1]);
    });
  }

  private boolean isSmall(String s) {
    if (s == "start" || s == "end") {
      return false;
    }

    for (char c : s.toCharArray()) {
      int n = c;
      if (n >= 97 && n <= 122) {
        return true;
      }
    }
    return false;
  }

  private boolean smallCheck(List<Vertex> lst) {
    Map<String, Integer> count = new HashMap<>();
    for (Vertex n : lst) {
      if (isSmall(n.getLabel())) {
        int num = count.getOrDefault(n.getLabel(), 0);
        count.put(n.getLabel(), num + 1);
      }

    }
    return count.values().stream().filter(i -> i > 1).count() == 0;
  }


  private void recurse(Graph graph, Vertex vertex, List<Vertex> current, List<List<Vertex>> paths, boolean part1) {
    if (Objects.equals(vertex.getLabel(), "end")) {
      paths.add(current);
      return;
    }

    for (Vertex v : graph.getAdjVertices(vertex.getLabel())) {
      if (Objects.equals(v.getLabel(), "start")) {
        continue;
      }

      if (part1) {
        if (isSmall(v.getLabel()) && current.contains(v)) {
          continue;
        }
      } else {
        if (isSmall(v.getLabel()) && current.contains(v) && !smallCheck(current)) {
          continue;
        }
      }

      List<Vertex> currentPath = new ArrayList<>(current);
      currentPath.add(v);
      recurse(graph, v, currentPath, paths, part1);
    }
  }

  @Override
  public Integer part1() {
    List<List<Vertex>> paths = new ArrayList<>();
    List<Vertex> lst = new ArrayList<>();
    Vertex start = new Vertex("start");
    lst.add(start);
    recurse(graph, start, lst, paths, true);
    return paths.size();
  }

  @Override
  public Integer part2() {
    List<List<Vertex>> paths = new ArrayList<>();
    List<Vertex> lst = new ArrayList<>();
    Vertex start = new Vertex("start");
    lst.add(start);
    recurse(graph, start, lst, paths, false);
    return paths.size();
  }
}
