package dev.kmunton.year2021.day23;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Node {

  private Integer[][] grid;
  private List<Integer> hall;
  private Integer steps;
  private Integer heuristic;

  public Node(Integer[][] grid, Integer steps, Integer heuristic) {
    this.grid = grid;
    hall = Arrays.stream(grid[0]).toList();
    this.steps = steps;
    this.heuristic = heuristic;
  }

  public Integer[][] getGrid() {
    return grid;
  }

  public void setGrid(Integer[][] grid) {
    this.grid = grid;
  }

  public Integer getSteps() {
    return steps;
  }

  public void setSteps(Integer steps) {
    this.steps = steps;
  }

  public Integer getHeuristic() {
    return heuristic;
  }

  public void setHeuristic(Integer heuristic) {
    this.heuristic = heuristic;
  }

  public List<Integer> getHall() {
    return hall;
  }

  public void setHall(List<Integer> hall) {
    this.hall = hall;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Node node = (Node) o;
    return Arrays.deepEquals(grid, node.grid) && Objects.equals(steps, node.steps);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(steps);
    result = 31 * result + Arrays.hashCode(grid);
    return result;
  }

  @Override
  public String toString() {
    return "Node{" +
        "steps=" + steps +
        ", heuristic=" + heuristic +
        '}';
  }
}
