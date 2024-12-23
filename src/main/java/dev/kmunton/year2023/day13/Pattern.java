package dev.kmunton.year2023.day13;

import java.util.ArrayList;
import java.util.List;

public class Pattern {

  private final List<String> rows;
  private final List<String> cols = new ArrayList<>();

  public Pattern(List<String> rows) {
    this.rows = rows;
    int len = this.rows.get(0).length();
    for (var i = 0; i < len; i++) {
      StringBuilder col = new StringBuilder();
      for (var j = 0; j < rows.size(); j++) {
        col.append(rows.get(j).charAt(i));
      }
      cols.add(col.toString());
    }
  }

  public List<String> getRows() {
    return rows;
  }

  public List<String> getCols() {
    return cols;
  }
}
