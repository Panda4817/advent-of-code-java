package dev.kmunton.year2023.day17;

import dev.kmunton.utils.geometry.Direction2D;
import dev.kmunton.utils.geometry.GridPoint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Crucible {

  private final Direction2D direction;
  private final long totalHeatLoss;
  private final int straightSteps;
  private int row;
  private int col;

  public Crucible(int row, int col, Direction2D direction, long totalHeatLoss, int straightSteps) {
    this.row = row;
    this.col = col;
    this.direction = direction;
    this.totalHeatLoss = totalHeatLoss;
    this.straightSteps = straightSteps;
  }

  @Override
  public String toString() {
    return "Crucible{" +
        "direction='" + direction + '\'' +
        ", totalHeatLoss=" + totalHeatLoss +
        ", straightSteps=" + straightSteps +
        '}';
  }

  public String getKey() {
    return this.getRow() + "_" + this.getCol() + "_" + direction + "_" + straightSteps;
  }

  public GridPoint moveByGivenDirection(Direction2D direction) {
    return new GridPoint(this.getCol() + direction.getDx(), this.getRow() + direction.getDy());
  }
}
