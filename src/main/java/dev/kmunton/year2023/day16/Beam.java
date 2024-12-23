package dev.kmunton.year2023.day16;

import dev.kmunton.utils.geometry.Direction2D;
import dev.kmunton.utils.geometry.GridPoint;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Beam {

  private Direction2D direction;
  private int row;
  private int col;

  public Beam(int row, int col, Direction2D direction) {
    this.row = row;
    this.col = col;
    this.direction = direction;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Beam beam = (Beam) o;
    return Objects.equals(direction, beam.direction) && super.equals(beam);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), direction);
  }

  public String getKey() {
    return this.getRow() + "_" + this.getCol() + "_" + direction;
  }

  public GridPoint moveByGivenDirection(Direction2D direction) {
    return new GridPoint(this.getCol() + direction.getDx(), this.getRow() + direction.getDy());
  }
}
