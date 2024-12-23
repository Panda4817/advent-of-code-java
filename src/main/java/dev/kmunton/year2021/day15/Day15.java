package dev.kmunton.year2021.day15;

import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

public class Day15 implements Day<Integer, Integer> {

  private List<List<Chiton>> cave;
  private List<List<Chiton>> bigCave;
  Map<Chiton, Integer> visited = new HashMap<>();
  private int height;
  private int width;
  private final int[] dx = {0, 0, -1, 1};
  private final int[] dy = {-1, 1, 0, 0};

  public Day15(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    cave = new ArrayList<>();
    int y = 0;
    for (String s : input) {
      List<Chiton> row = new ArrayList<>();
      List<Integer> riskNumbers = List.of(s.split("(?<=\\G.)")).stream().map(Integer::valueOf).collect(Collectors.toList());
      int x = 0;
      for (Integer risk : riskNumbers) {
        row.add(new Chiton(x, y, risk));
        x += 1;
      }
      cave.add(row);
      y += 1;
    }
    height = cave.size();
    width = cave.get(0).size();
  }

  private void printCave() {
    for (List<Chiton> row : bigCave) {
      System.out.println(row);
    }
  }


  private boolean notInVisitedOrHigherRisk(Chiton chiton, int newRisk) {
    Integer risk = visited.getOrDefault(chiton, -1);
    if (risk == -1 || risk > newRisk) {
      visited.put(chiton, newRisk);
      return true;
    }
    return false;
  }


  @Override
  public Integer part1() {
    System.out.println("grid size: " + height + " " + width);
    return findRiskFreeRoute(cave);
  }

  private int updateRisk(int risk) {
    if (risk + 1 == 10) {
      return 1;
    }
    return risk + 1;
  }

  private int findRiskFreeRoute(List<List<Chiton>> cave) {
    Chiton start = cave.get(0).get(0);
    Chiton end = cave.get(height - 1).get(width - 1);
    Queue<Node> q = new PriorityQueue<>(1, Comparator.comparingInt(Node::getCurrentRisk));
    Node startNode = new Node(start, 0);
    q.add(startNode);
    visited.put(start, 0);
    while (!q.isEmpty()) {
      Node node = q.remove();
      if (node.getChiton().equals(end)) {
        System.out.println(node);
        return node.getCurrentRisk();
      }

      for (int i = 0; i < 4; i++) {
        int x = node.getChiton().getX() + dx[i];
        int y = node.getChiton().getY() + dy[i];

        if (x < 0 || x >= width || y < 0 || y >= height) {
          continue;
        }

        Chiton neighbor = cave.get(y).get(x);
        int newRisk = neighbor.getRisk() + node.getCurrentRisk();
        if (notInVisitedOrHigherRisk(neighbor, newRisk)) {
          Node newNode = new Node(neighbor, newRisk);
          q.add(newNode);
        }
      }

    }
    return 0;
  }

  private void createBigGridFromTile(int size) {
    bigCave = new ArrayList<>();
    int rowOfTiles = 0;
    final int tileWidth = cave.get(0).size();
    final int tileHeight = cave.size();
    List<List<Chiton>> nextRowStart = new ArrayList<>(cave);
    while (rowOfTiles < size) {
      List<List<Chiton>> bigRow = new ArrayList<>(nextRowStart);
      List<List<Chiton>> current = new ArrayList<>(nextRowStart);
      for (int tile = 1; tile < size; tile++) {

        List<List<Chiton>> caveNew = current.stream().map(
            lst -> lst.stream()
                      .map(c -> new Chiton(c.getX() + tileWidth, c.getY(), updateRisk(c.getRisk())))
                      .collect(Collectors.toList())
        ).collect(Collectors.toList());
        if (tile == 1) {
          nextRowStart = caveNew.stream().map(
              lst -> lst.stream()
                        .map(c -> new Chiton(c.getX() - tileWidth, c.getY() + tileHeight, c.getRisk()))
                        .collect(Collectors.toList())
          ).collect(Collectors.toList());

        }
        int i = 0;
        for (List<Chiton> row : caveNew) {
          for (Chiton c : row) {

            bigRow.get(i).add(new Chiton(c.getX(), c.getY(), c.getRisk()));
          }
          i += 1;
        }
        current = new ArrayList<>(caveNew);
      }

      bigCave.addAll(bigRow);
      rowOfTiles += 1;
    }

    height = bigCave.size();
    width = bigCave.get(0).size();
  }

  @Override
  public Integer part2() {
    createBigGridFromTile(5);
    System.out.println("grid size: " + height + " " + width);
    visited.clear();

    return findRiskFreeRoute(bigCave);
  }
}
