package dev.kmunton.year2021.day12;

import java.util.Objects;

public class Vertex {

  private String label;

  Vertex(String label) {
    this.label = label;
  }

  // equals and hashCode

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vertex vertex = (Vertex) o;
    return Objects.equals(label, vertex.label);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label);
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  @Override
  public String toString() {
    return label;
  }
}
