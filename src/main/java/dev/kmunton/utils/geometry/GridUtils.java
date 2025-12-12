package dev.kmunton.utils.geometry;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GridUtils<T> implements ShapeUtils<Map<GridPoint, T>, Direction2D> {

  @Override
  public Map<GridPoint, T> rotateGivenDirection(Map<GridPoint, T> grid, int degrees, Direction2D direction) {
    if (degrees % 90 != 0) {
      throw new IllegalArgumentException("Rotation degrees must be a multiple of 90.");
    }

    if (grid.isEmpty()) {
      return grid;
    }

    degrees = ((degrees % 360) + 360) % 360;
    return switch (degrees) {
      case 0 -> grid; // No rotation
      case 90 -> rotate90(grid);
      case 180 -> rotate90(rotate90(grid));
      case 270 -> rotate90(rotate90(rotate90(grid)));
      default -> throw new IllegalStateException("Unexpected degree value: " + degrees);
    };
  }

    @Override
    public Map<GridPoint, T> flipGivenDirection(Map<GridPoint, T> shape,  Direction2D direction) {
        Map<GridPoint, T> flipped = new HashMap<>();

        int maxX = maxX(shape);
        int maxY = maxY(shape);

        for (Map.Entry<GridPoint, T> entry : shape.entrySet()) {
            GridPoint p = entry.getKey();
            T value = entry.getValue();

            int x = p.x();
            int y = p.y();

            GridPoint newPoint = switch (direction) {
                case RIGHT ->
                    // Mirror across the Y-axis: (x, y) → (-x, y)
                        new GridPoint(maxX - x, y);
                case UP ->
                    // Mirror across the X-axis: (x, y) → (x, -y)
                        new GridPoint(x, maxY - y);
                default ->
                    // No flip or unsupported direction
                        p;
            };

            flipped.put(newPoint, value);
        }

        return flipped;
    }

    @Override
  public int maxX(Map<GridPoint, T> shape) {
    return shape.keySet().stream()
                .mapToInt(GridPoint::x)
                .max()
                .orElse(0);
  }

  @Override
  public int maxY(Map<GridPoint, T> shape) {
    return shape.keySet().stream()
                .mapToInt(GridPoint::y)
                .max()
                .orElse(0);
  }

  @Override
  public int maxZ(Map<GridPoint, T> shape) {
    // Since GridPoint is 2D, we return 0 or throw an exception if appropriate
    return 0;
  }

  @Override
  public int minX(Map<GridPoint, T> shape) {
    return shape.keySet().stream()
                .mapToInt(GridPoint::x)
                .min()
                .orElse(0);
  }

  @Override
  public int minY(Map<GridPoint, T> shape) {
    return shape.keySet().stream()
                .mapToInt(GridPoint::y)
                .min()
                .orElse(0);
  }

  @Override
  public int minZ(Map<GridPoint, T> shape) {
    // Since GridPoint is 2D, we return 0 or throw an exception if appropriate
    return 0;
  }


  private Map<GridPoint, T> rotate90(Map<GridPoint, T> grid) {
    Map<GridPoint, T> rotated = new HashMap<>();
    int maxX = maxX(grid);
    for (Map.Entry<GridPoint, T> entry : grid.entrySet()) {
      GridPoint original = entry.getKey();
      GridPoint rotatedPoint = new GridPoint(original.y(), maxX - original.x());
      rotated.put(rotatedPoint, entry.getValue());
    }
    return rotated;
  }
}


