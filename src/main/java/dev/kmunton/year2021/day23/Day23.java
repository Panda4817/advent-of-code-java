package dev.kmunton.year2021.day23;

import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day23 implements Day<Integer, Integer> {

  private Integer[][] grid;
  private Integer[][] gridPart2;
  private final Map<Integer, Amphipod> letterInfo;
  private Integer maxY;
  private Integer maxX;
  private Integer empty;
  private Integer wall;
  // Hard-coded hallway positions in row 0
  // Allowed "stop" positions in the hallway: x != 2,4,6,8
  private static final Set<Integer> FORBIDDEN_HALL_COLUMNS = Set.of(3, 5, 7, 9);

  public Day23(List<String> input) {
    letterInfo = new HashMap<>();
    letterInfo.put(1, new Amphipod("A", 1, 1, 3));
    letterInfo.put(2, new Amphipod("B", 2, 10, 5));
    letterInfo.put(3, new Amphipod("C", 3, 100, 7));
    letterInfo.put(4, new Amphipod("D", 4, 1000, 9));
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    maxX = 13;
    empty = 0;
    wall = 9;
    maxY = 5;
    grid = new Integer[maxY][maxX];
    int y = 0;
    for (String s : input) {
      List<String> row = List.of(s.split("(?<=\\G.)"));
      int x = 0;
      for (String cell : row) {
        switch (cell) {
          case "#", " " -> grid[y][x] = wall;
          case "." -> grid[y][x] = empty;
          default -> grid[y][x] = switch (cell) {
            case "A" -> 1;
            case "B" -> 2;
            case "C" -> 3;
            default -> 4;
          };
        }
        x += 1;
      }
      while (x < maxX) {
        grid[y][x] = wall;
        x += 1;
      }
      y += 1;
    }

  }

  // For debugging:
  public void printGrid(Integer[][] g) {
    for (int y = 0; y < maxY; y++) {
      for (int x = 0; x < maxX; x++) {
        int v = g[y][x];
        if (v == wall) {
          System.out.print('#');
        } else if (v == empty) {
          System.out.print('.');
        } else {
          switch (v) {
            case 1 -> System.out.print('A');
            case 2 -> System.out.print('B');
            case 3 -> System.out.print('C');
            case 4 -> System.out.print('D');
          }
        }
      }
      System.out.println();
    }
  }

  public Integer part1() {
    printGrid(grid);
    return aStar(grid);
  }

  @Override
  public Integer part2() {
    maxY = 7;  // Increase number of rows
    gridPart2 = new Integer[maxY][maxX];
    Integer[][] extra = new Integer[2][maxX]; // Two extra rows (3 and 4)

    // Populate the extra rows (rows 3 and 4)
    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < maxX; x++) {
        if (x <= 2 || x >= 10 || x % 2 == 0) {
          extra[y][x] = wall;
        }
        if (y == 0) {
          int type = switch (x) {
            case 3 -> 4;
            case 5 -> 3;
            case 7 -> 2;
            case 9 -> 1;
            default -> wall;
          };
          extra[y][x] = type;
        }
        if (y == 1) {
          int type = switch (x) {
            case 3 -> 4;
            case 5 -> 2;
            case 7 -> 1;
            case 9 -> 3;
            default -> wall;
          };
          extra[y][x] = type;
        }
      }
    }

    // Copy original grid to gridPart2 and add extra rows (3 and 4)
    for (int y = 0; y < maxY; y++) {
      for (int x = 0; x < maxX; x++) {
        if (y < 3) {
          gridPart2[y][x] = grid[y][x]; // Copy first 3 rows
        } else if (y == 3) {
          gridPart2[y] = extra[0].clone(); // Copy row 3 (extra)
        } else if (y == 4) {
          gridPart2[y] = extra[1].clone(); // Copy row 4 (extra)
        } else {
          gridPart2[y] = grid[y - 2].clone(); // Copy last 2 rows
        }
      }
    }

    printGrid(gridPart2);

    return aStar(gridPart2);
  }

  private int aStar(Integer[][] grid) {
    PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.costSoFar));
    Map<State, Integer> bestCost = new HashMap<>();

    State start = new State(copyGrid(grid), 0);
    pq.offer(start);
    bestCost.put(start, 0);

    int minSteps = Integer.MAX_VALUE;
    while (!pq.isEmpty()) {
      State current = pq.poll();
      int curCost = current.costSoFar;

      // If we've reached a "goal" arrangement, return the cost
      if (isGoal(current.grid)) {
        minSteps = curCost;
        return minSteps;
      }

      // If we already found a cheaper route to this arrangement, skip
      if (curCost > bestCost.getOrDefault(current, Integer.MAX_VALUE)) {
        continue;
      }

      // Generate next possible states
      for (State nxt : generateMoves(current)) {
        int nxtCost = nxt.costSoFar;
        if (nxtCost < bestCost.getOrDefault(nxt, Integer.MAX_VALUE)) {
          bestCost.put(nxt, nxtCost);
          pq.offer(nxt);
        }
      }
    }

    return minSteps; // might remain Integer.MAX_VALUE if never solved
  }

  /**
   * Check if all amphipods are in their correct burrow columns and in rows 2..3, and the hallway (row=1) is empty.
   */
  private boolean isGoal(Integer[][] g) {
    // Hallway row=1 must have no amphipods
    for (int x = 0; x < maxX; x++) {
      if (g[1][x] != empty && g[1][x] != wall) {
        return false;
      }
    }
    // For each occupant in row=2..3, must be in correct column
    for (int y = 2; y <= maxY - 2; y++) {
      for (int x = 0; x < maxX; x++) {
        int val = g[y][x];
        if (val == wall || val == empty) {
          continue;
        }
        // occupant must be in the correct column
        Amphipod amp = letterInfo.get(val);
        if (amp.getBurrowX() != x) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Generate all possible next states by moving exactly one amphipod either from (row=1) hallway -> (row=2..3) burrow, or from burrow -> hallway.
   */
  private List<State> generateMoves(State st) {
    List<State> result = new ArrayList<>();
    Integer[][] g = st.grid;

    // 1) Move from HALLWAY row=1 => BURROW (rows 2..3)
    for (int x = 0; x < maxX; x++) {
      int occupant = g[1][x];
      if (occupant == empty || occupant == wall) {
        continue;
      }
      // occupant is 1..4 => try moving it to its correct burrow
      result.addAll(moveHallToRoom(g, st.costSoFar, 1, x, occupant));
    }

    // 2) Move from BURROW => HALLWAY row=1
    //   For each burrow column, find the top occupant (lowest y) that can move
    for (int n = 1; n <= 4; n++) {
      int col = letterInfo.get(n).getBurrowX();
      // find occupant in col at row=2 or 3 (the top occupant is row=2 if occupied, else row=3)
      int occupantY = -1;
      int occupantVal = -1;
      for (int row = 2; row <= maxY - 2; row++) {
        if (g[row][col] != empty && g[row][col] != wall) {
          occupantY = row;
          occupantVal = g[row][col];
          break; // top occupant found
        }
      }
      if (occupantY == -1) {
        continue; // no occupant in that burrow
      }

      // If occupant is "settled" (already in correct spot with correct occupant below), skip
      if (isSettled(g, occupantY, col, occupantVal)) {
        continue;
      }

      // else generate moves from that occupant out into the hallway row=1
      result.addAll(moveRoomToHall(g, st.costSoFar, occupantY, col, occupantVal));
    }

    return result;
  }

  /**
   * Move occupantVal from hallway (row=1, col=fromX) to correct burrow (rows 2..3).
   */
  private List<State> moveHallToRoom(Integer[][] g, int costSoFar, int fromY, int fromX, int occupantVal) {
    List<State> states = new ArrayList<>();
    int targetX = letterInfo.get(occupantVal).getBurrowX();

    // Must check the hallway path from "fromX" to "targetX" is clear (row=1)
    if (!canPassHall(g, fromX, targetX)) {
      return states; // blocked
    }

    // Find how deep we can place occupantVal in that column (2 or 3).
    // We can only place occupantVal in row if everything below is occupantVal or wall.
    int placeY = -1;
    for (int row = maxY - 2; row >= 2; row--) {
      if (g[row][targetX] == empty) {
        // check below is occupantVal or wall
        boolean valid = true;
        for (int r2 = row + 1; r2 <= maxY - 2; r2++) {
          if (r2 > 3) {
            break;
          }
          if (g[r2][targetX] != empty && g[r2][targetX] != occupantVal && g[r2][targetX] != wall) {
            valid = false;
            break;
          }
        }
        if (valid) {
          placeY = row;
          break;
        }
      } else if (g[row][targetX] != occupantVal && g[row][targetX] != wall && g[row][targetX] != empty) {
        // another occupant is blocking
        return states;
      }
    }
    if (placeY == -1) {
      return states; // cannot place
    }

    // Now we can move occupantVal from (1, fromX) to (placeY, targetX)
    // Distance = vertical( abs(1 - placeY) ) + horizontal( abs(fromX - targetX) )
    // cost = distance * occupantVal's energy
    int dist = Math.abs(1 - placeY) + Math.abs(fromX - targetX);
    int stepCost = dist * letterInfo.get(occupantVal).getEnergyPerStep();

    Integer[][] newGrid = copyGrid(g);
    newGrid[1][fromX] = empty;
    newGrid[placeY][targetX] = occupantVal;
    states.add(new State(newGrid, costSoFar + stepCost));

    return states;
  }

  /**
   * Move occupantVal from (row>=2) => hallway row=1. We check if vertical path (row=2..fromY-1) is clear in that column, then we can move left or
   * right along row=1 until blocked.
   */
  private List<State> moveRoomToHall(Integer[][] g, int costSoFar, int fromY, int fromX, int occupantVal) {
    List<State> states = new ArrayList<>();

    // Check vertical path up to row=1 is free
    for (int r = fromY - 1; r >= 1; r--) {
      if (g[r][fromX] != empty) {
        return states; // blocked
      }
    }
    // Now occupant is effectively in row=1, col=fromX. Let's see all the places we can stand in row=1
    // We cannot stand in col=2,4,6,8 (doorways), unless we pass into a burrow (but we're not doing that in this method).
    // We'll go left, then go right.

    // go left
    for (int x = fromX - 1; x >= 0; x--) {
      if (g[1][x] != empty) {
        break; // blocked
      }
      if (!FORBIDDEN_HALL_COLUMNS.contains(x)) {
        int dist = (fromY - 1) + Math.abs(fromX - x);
        // fromY-1 is how many steps to row=1 from fromY
        // plus horizontal steps from fromX to x
        int costMove = dist * letterInfo.get(occupantVal).getEnergyPerStep();

        Integer[][] newGrid = copyGrid(g);
        newGrid[fromY][fromX] = empty;
        newGrid[1][x] = occupantVal;
        states.add(new State(newGrid, costSoFar + costMove));
      }
    }

    // go right
    for (int x = fromX + 1; x < maxX; x++) {
      if (g[1][x] != empty) {
        break; // blocked
      }
      if (!FORBIDDEN_HALL_COLUMNS.contains(x)) {
        int dist = (fromY - 1) + Math.abs(fromX - x);
        int costMove = dist * letterInfo.get(occupantVal).getEnergyPerStep();

        Integer[][] newGrid = copyGrid(g);
        newGrid[fromY][fromX] = empty;
        newGrid[1][x] = occupantVal;
        states.add(new State(newGrid, costSoFar + costMove));
      }
    }
    return states;
  }

  /**
   * Return true if occupantVal at (y,x) does NOT need to move: - occupantVal is in the correct column - everything below is occupantVal or wall
   */
  private boolean isSettled(Integer[][] g, int y, int x, int occupantVal) {
    if (letterInfo.get(occupantVal).getBurrowX() != x) {
      return false;
    }
    // check rows below
    for (int row = y + 1; row <= maxY - 2; row++) {
      if (g[row][x] == wall) {
        continue;
      }
      if (g[row][x] != occupantVal) {
        return false;
      }
    }
    return true;
  }

  /**
   * Check if hallway path fromX..targetX is clear at row=1 (excluding occupant's original position). If fromX < targetX, we check row=1, columns
   * fromX+1..targetX If fromX > targetX, we check row=1, columns fromX-1..targetX
   */
  private boolean canPassHall(Integer[][] g, int fromX, int targetX) {
    if (fromX < targetX) {
      for (int x = fromX + 1; x <= targetX; x++) {
        if (g[1][x] != empty) {
          return false;
        }
      }
    } else {
      for (int x = fromX - 1; x >= targetX; x--) {
        if (g[1][x] != empty) {
          return false;
        }
      }
    }
    return true;
  }

  private Integer[][] copyGrid(Integer[][] origin) {
    Integer[][] newG = new Integer[maxY][maxX];
    for (int yy = 0; yy < maxY; yy++) {
      newG[yy] = origin[yy].clone();
    }
    return newG;
  }

  // A small state: the arrangement + cost so far
  private class State {

    Integer[][] grid;
    int costSoFar;

    State(Integer[][] g, int c) {
      this.grid = g;
      this.costSoFar = c;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof State other)) {
        return false;
      }
      for (int yy = 0; yy < maxY; yy++) {
        for (int xx = 0; xx < maxX; xx++) {
          if (!Objects.equals(this.grid[yy][xx], other.grid[yy][xx])) {
            return false;
          }
        }
      }
      return true;
    }

    @Override
    public int hashCode() {
      int hash = 0;
      for (int yy = 0; yy < maxY; yy++) {
        for (int xx = 0; xx < maxX; xx++) {
          hash = 31 * hash + grid[yy][xx];
        }
      }
      return hash;
    }
  }
}
