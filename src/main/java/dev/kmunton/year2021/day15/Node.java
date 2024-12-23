package dev.kmunton.year2021.day15;

import java.util.Objects;

public class Node {

  private Chiton chiton;
  private int currentRisk;

  public Node(Chiton chiton, int currentRisk) {
    this.chiton = chiton;
    this.currentRisk = currentRisk;
  }

  public Chiton getChiton() {
    return chiton;
  }

  public void setChiton(Chiton chiton) {
    this.chiton = chiton;
  }

  public int getCurrentRisk() {
    return currentRisk;
  }

  public void setCurrentRisk(int currentRisk) {
    this.currentRisk = currentRisk;
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
    return currentRisk == node.currentRisk && Objects.equals(chiton, node.chiton);
  }

  @Override
  public int hashCode() {
    return Objects.hash(chiton, currentRisk);
  }

  @Override
  public String toString() {
    return "Node{" +
        "chiton=" + chiton.getX() + "," + chiton.getY() +
        " , currentRisk=" + currentRisk +
        '}';
  }
}
