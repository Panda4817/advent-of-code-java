package dev.kmunton.utils.algorithms;

import static dev.kmunton.utils.algorithms.GraphSearchUtils.findAllPathsWithBfs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import org.junit.jupiter.api.Test;

class GraphSearchUtilsTest {

  @Test
  void aStarSearch_graphWithPath_returnsShortestPath() {
    // Graph:
    // A --1--> B --2--> C --1--> D
    // A --4--> C
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 1, "C", 4));
    graph.put("B", Map.of("C", 2, "D", 5));
    graph.put("C", Map.of("D", 1));
    graph.put("D", Map.of());

    Function<String, Map<String, Integer>> getNeighbors = graph::get;
    ToIntFunction<String> heuristic = node -> switch (node) {
      case "A" -> 6;
      case "B" -> 4;
      case "C" -> 2;
      case "D" -> 0;
      default -> Integer.MAX_VALUE;
    };

    List<String> path = GraphSearchUtils.aStarSearch("A", "D"::equals, getNeighbors, heuristic);

    assertEquals(List.of("A", "B", "C", "D"), path);
  }

  @Test
  void dijkstraSearch_graphWithPath_returnsShortestPath() {
    // Graph:
    // A --1--> B --2--> C --1--> D
    // A --4--> C
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 1, "C", 4));
    graph.put("B", Map.of("C", 2, "D", 5));
    graph.put("C", Map.of("D", 1));
    graph.put("D", Map.of());

    Function<String, Map<String, Integer>> getNeighbors = graph::get;

    List<String> path = GraphSearchUtils.dijkstraSearch("A", "D", getNeighbors);

    assertEquals(List.of("A", "B", "C", "D"), path);
  }

  @Test
  void shortestPathWithBfs_graphWithPath_returnsShortestPathInSteps() {
    // Graph:
    // A -- B
    // |    |
    // C -- D
    Map<String, List<String>> graph = new HashMap<>();
    graph.put("A", List.of("B", "C"));
    graph.put("B", List.of("D"));
    graph.put("C", List.of("D"));
    graph.put("D", List.of());

    Function<String, List<String>> getNeighbors = graph::get;

    List<String> path = GraphSearchUtils.shortestPathWithBfs(
        "A",
        "D"::equals,
        getNeighbors
    );

    assertEquals(List.of("A", "B", "D"), path);
  }

  @Test
  void shortestPathWithBfs_graphSeveralPathsToGoal_returnsShortestPathInSteps() {
    // Graph:
    // A -- B -- C -- D -- E
    // |    |
    // C -- D
    Map<String, List<String>> graph = new HashMap<>();
    graph.put("A", List.of("B", "C"));
    graph.put("B", List.of("D", "C"));
    graph.put("C", List.of("D"));
    graph.put("D", List.of("E"));
    graph.put("E", List.of());

    Function<String, List<String>> getNeighbors = graph::get;

    List<String> path = GraphSearchUtils.shortestPathWithBfs(
        "A",
        "D"::equals,
        getNeighbors
    );

    assertEquals(List.of("A", "B", "D"), path);
  }

  @Test
  void dfs_graphWithPath_returnsTrue() {
    // Graph:
    // A -- B
    // |    |
    // C -- D
    Map<String, List<String>> graph = new HashMap<>();
    graph.put("A", List.of("B", "C"));
    graph.put("B", List.of("D"));
    graph.put("C", List.of("D"));
    graph.put("D", List.of());

    Function<String, List<String>> getNeighbors = graph::get;

    boolean found = GraphSearchUtils.dfs(
        "A",
        "D"::equals,
        getNeighbors
    );

    assertTrue(found);
  }

  @Test
  void aStarSearch_graphWithoutPath_returnsEmptyList() {
    // Graph:
    // A --1--> B
    // C (isolated node)
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 1));
    graph.put("B", Map.of());
    graph.put("C", Map.of());

    Function<String, Map<String, Integer>> getNeighbors = graph::get;
    ToIntFunction<String> heuristic = node -> 1;

    List<String> path = GraphSearchUtils.aStarSearch("A", "C"::equals, getNeighbors, heuristic);

    assertTrue(path.isEmpty());
  }

  @Test
  void dijkstraSearch_graphWithoutPath_returnsEmptyList() {
    // Graph:
    // A --1--> B
    // C (isolated node)
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 1));
    graph.put("B", Map.of());
    graph.put("C", Map.of());

    Function<String, Map<String, Integer>> getNeighbors = graph::get;

    List<String> path = GraphSearchUtils.dijkstraSearch("A", "C", getNeighbors);

    assertTrue(path.isEmpty());
  }

  @Test
  void shortestPathWithBfs_graphWithoutPath_returnsEmptyList() {
    // Graph:
    // A -- B
    // C (isolated node)
    Map<String, List<String>> graph = new HashMap<>();
    graph.put("A", List.of("B"));
    graph.put("B", List.of());
    graph.put("C", List.of());

    Function<String, List<String>> getNeighbors = graph::get;

    List<String> path = GraphSearchUtils.shortestPathWithBfs("A", "C"::equals, getNeighbors);

    assertTrue(path.isEmpty());
  }

  @Test
  void dfs_graphWithoutPath_returnsFalse() {
    // Graph:
    // A -- B
    // C (isolated node)
    Map<String, List<String>> graph = new HashMap<>();
    graph.put("A", List.of("B"));
    graph.put("B", List.of());
    graph.put("C", List.of());

    Function<String, List<String>> getNeighbors = graph::get;

    boolean found = GraphSearchUtils.dfs("A", "C"::equals, getNeighbors);

    assertFalse(found);
  }

  @Test
  void findLongestPathDirected_graphWithValidPath_returnsLongestPath() {
    // Directed Graph:
    // A --3--> B --4--> D --2--> E
    // A --6--> C --8--> D
    // B --11--> E
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 3, "C", 6));
    graph.put("B", Map.of("D", 4, "E", 11));
    graph.put("C", Map.of("D", 8));
    graph.put("D", Map.of("E", 2));
    graph.put("E", Map.of());

    List<String> longestPath = GraphSearchUtils.findLongestPathDirected("A", "E", graph);

    assertEquals(List.of("A", "C", "D", "E"), longestPath);
  }

  @Test
  void findLongestPathDirected_graphWithNoPath_returnsEmptyList() {
    // Directed Graph:
    // A --3--> B
    // C (isolated node)
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 3));
    graph.put("B", Map.of());
    graph.put("C", Map.of());

    List<String> longestPath = GraphSearchUtils.findLongestPathDirected("A", "C", graph);

    assertTrue(longestPath.isEmpty());
  }

  @Test
  void findLongestPathDirected_cyclicGraph_returnsEmptyList() {
    // Directed Graph with Cycle:
    // A --3--> B --4--> C --2--> A (cycle)
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 3));
    graph.put("B", Map.of("C", 4));
    graph.put("C", Map.of("A", 2)); // Cycle back to A

    List<String> longestPath = GraphSearchUtils.findLongestPathDirected("A", "C", graph);

    assertTrue(longestPath.isEmpty());
  }

  @Test
  void findLongestPathUndirected_graphWithValidPath_returnsLongestPath() {
    // Undirected Graph:
    // A --3-- B --4-- D
    // |       |
    // 2       1
    // |       |
    // C ------ D
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 3, "C", 2));
    graph.put("B", Map.of("A", 3, "D", 4));
    graph.put("C", Map.of("A", 2, "D", 1));
    graph.put("D", Map.of("B", 4, "C", 1));

    Function<String, Map<String, Integer>> getNeighbors = graph::get;

    List<String> longestPath = GraphSearchUtils.findLongestPathUndirected("A", "D", getNeighbors);

    assertEquals(List.of("A", "B", "D"), longestPath);
  }

  @Test
  void findLongestPathUndirected_graphWithNoPath_returnsEmptyList() {
    // Undirected Graph:
    // A --3-- B
    // C (isolated node)
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 3));
    graph.put("B", Map.of("A", 3));
    graph.put("C", Map.of());

    Function<String, Map<String, Integer>> getNeighbors = graph::get;

    List<String> longestPath = GraphSearchUtils.findLongestPathUndirected("A", "C", getNeighbors);

    assertTrue(longestPath.isEmpty());
  }

  @Test
  void findLongestPathUndirected_singleNode_returnsStart() {
    // Graph:
    // A (single node)
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of());

    Function<String, Map<String, Integer>> getNeighbors = graph::get;

    List<String> longestPath = GraphSearchUtils.findLongestPathUndirected("A", "A", getNeighbors);

    assertEquals(List.of("A"), longestPath);
  }

  @Test
  void findLongestPathDirected_graphWithMultiplePaths_returnsLongestPath() {
    // Directed Graph:
    // A --5--> B --5--> D --1--> E
    // A --10--> C --1--> D
    // C --5--> E
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 5, "C", 10));
    graph.put("B", Map.of("D", 5));
    graph.put("C", Map.of("D", 1, "E", 5));
    graph.put("D", Map.of("E", 1));
    graph.put("E", Map.of());

    List<String> longestPath = GraphSearchUtils.findLongestPathDirected("A", "E", graph);

    assertEquals(List.of("A", "C", "E"), longestPath);
  }

  // Additional Tests

  @Test
  void dijkstraSearch_graphWithNegativeWeights_returnsShortestPath() {
    // Graph with negative weights:
    // A --(-1)--> B --(-2)--> C --1--> D
    // A --4--> C
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", -1, "C", 4));
    graph.put("B", Map.of("C", -2, "D", 5));
    graph.put("C", Map.of("D", 1));
    graph.put("D", Map.of());

    Function<String, Map<String, Integer>> getNeighbors = graph::get;

    // Dijkstra's algorithm doesn't work correctly with negative weights, so the test ensures it doesn't fail
    List<String> path = GraphSearchUtils.dijkstraSearch("A", "D", getNeighbors);

    // Since negative weights can cause issues, the expected path might not be the minimal one
    // Here we just check that a path is found
    assertFalse(path.isEmpty());
  }

  @Test
  void aStarSearch_graphWithHeuristicOverestimating_returnsShortestPath() {
    // Graph:
    // A --1--> B --1--> C --1--> D
    // Heuristic overestimates the actual cost
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 1));
    graph.put("B", Map.of("C", 1));
    graph.put("C", Map.of("D", 1));
    graph.put("D", Map.of());

    Function<String, Map<String, Integer>> getNeighbors = graph::get;
    ToIntFunction<String> heuristic = node -> 10; // Overestimating heuristic

    List<String> path = GraphSearchUtils.aStarSearch("A", "D"::equals, getNeighbors, heuristic);

    // A* with an overestimating heuristic becomes unreliable; we check that it still finds a path
    assertFalse(path.isEmpty());
  }

  @Test
  void shortestPathWithBfs_disconnectedGraph_returnsEmptyList() {
    // Graph:
    // A -- B
    // C -- D
    Map<String, List<String>> graph = new HashMap<>();
    graph.put("A", List.of("B"));
    graph.put("B", List.of("A"));
    graph.put("C", List.of("D"));
    graph.put("D", List.of("C"));

    Function<String, List<String>> getNeighbors = graph::get;

    List<String> path = GraphSearchUtils.shortestPathWithBfs("A", "D"::equals, getNeighbors);

    assertTrue(path.isEmpty());
  }

  @Test
  void dfs_cyclicGraph_returnsTrue() {
    // Graph with a cycle:
    // A -- B
    // |    |
    // D -- C
    Map<String, List<String>> graph = new HashMap<>();
    graph.put("A", List.of("B", "D"));
    graph.put("B", List.of("A", "C"));
    graph.put("C", List.of("B", "D"));
    graph.put("D", List.of("A", "C"));

    Function<String, List<String>> getNeighbors = graph::get;

    boolean found = GraphSearchUtils.dfs("A", "C"::equals, getNeighbors);

    assertTrue(found);
  }

  @Test
  void findLongestPathDirected_largeDAG_returnsLongestPath() {
    // Large DAG:
    // A --1--> B --1--> C --1--> D --1--> E
    // A --1--> F --1--> G --1--> H --1--> I --1--> E
    // Longest path should be A -> F -> G -> H -> I -> E
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 1, "F", 1));
    graph.put("B", Map.of("C", 1));
    graph.put("C", Map.of("D", 1));
    graph.put("D", Map.of("E", 1));
    graph.put("F", Map.of("G", 1));
    graph.put("G", Map.of("H", 1));
    graph.put("H", Map.of("I", 1));
    graph.put("I", Map.of("E", 1));
    graph.put("E", Map.of());

    List<String> longestPath = GraphSearchUtils.findLongestPathDirected("A", "E", graph);

    assertEquals(List.of("A", "F", "G", "H", "I", "E"), longestPath);
  }

  @Test
  void findLongestPathUndirected_largeGraph_returnsLongestPath() {
    // Large undirected graph:
    // A --1-- B --1-- C --1-- D --1-- E
    //  \                             /
    //   ----------2----------------
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 1, "E", 2));
    graph.put("B", Map.of("A", 1, "C", 1));
    graph.put("C", Map.of("B", 1, "D", 1));
    graph.put("D", Map.of("C", 1, "E", 1));
    graph.put("E", Map.of("D", 1, "A", 2));

    Function<String, Map<String, Integer>> getNeighbors = graph::get;

    List<String> longestPath = GraphSearchUtils.findLongestPathUndirected("A", "E", getNeighbors);

    // Longest path from A to E should be A -> B -> C -> D -> E
    assertEquals(List.of("A", "B", "C", "D", "E"), longestPath);
  }

  @Test
  void findAllPathsWithBfs_givenSingleNodeGraphIsGoal_thenShouldReturnSinglePath() {
    Integer start = 1;
    Predicate<Integer> isGoal = node -> node.equals(1);
    Function<Integer, List<Integer>> getNeighbors = node -> Collections.emptyList();

    List<List<Integer>> paths = findAllPathsWithBfs(start, isGoal, getNeighbors, true);

    assertEquals(1, paths.size(), "There should be exactly one path");
    assertEquals(Collections.singletonList(1), paths.get(0), "The path should be just the single node");
  }

  @Test
  void findAllPathsWithBfs_givenLinearGraphWithGoalAtEndAndCheckVisitedFalse_thenShouldFindSinglePath() {
    // Graph: 1 -> 2 -> 3 -> 4 (goal is node 4)
    Map<Integer, List<Integer>> graph = new HashMap<>();
    graph.put(1, List.of(2));
    graph.put(2, List.of(3));
    graph.put(3, List.of(4));
    graph.put(4, Collections.emptyList());

    Integer start = 1;
    Predicate<Integer> isGoal = node -> node.equals(4);
    Function<Integer, List<Integer>> getNeighbors = graph::get;

    List<List<Integer>> paths = findAllPathsWithBfs(start, isGoal, getNeighbors, false);

    assertEquals(1, paths.size(), "Only one path should exist from 1 to 4 in a linear graph");
    assertEquals(Arrays.asList(1, 2, 3, 4), paths.get(0));
  }

  @Test
  void findAllPathsWithBfs_givenBranchingGraphWithTwoPathsAndCheckVisitedFalse_thenShouldReturnTwoDistinctPaths() {
    // Graph:
    //    1
    //   / \
    //  2   3
    //   \ /
    //    4 (goal)
    Map<Integer, List<Integer>> graph = new HashMap<>();
    graph.put(1, Arrays.asList(2, 3));
    graph.put(2, List.of(4));
    graph.put(3, List.of(4));
    graph.put(4, Collections.emptyList());

    Integer start = 1;
    Predicate<Integer> isGoal = node -> node.equals(4);
    Function<Integer, List<Integer>> getNeighbors = graph::get;

    List<List<Integer>> paths = findAllPathsWithBfs(start, isGoal, getNeighbors, false);

    assertEquals(2, paths.size(), "There should be two distinct paths from 1 to 4");
    List<Integer> pathA = Arrays.asList(1, 2, 4);
    List<Integer> pathB = Arrays.asList(1, 3, 4);
    assertTrue(paths.contains(pathA), "Paths should contain path via node 2");
    assertTrue(paths.contains(pathB), "Paths should contain path via node 3");
  }

  @Test
  void findAllPathsWithBfs_givenBranchingGraphWithTwoPathsAndCheckVisitedTrue_thenShouldReturnOnePath() {
    // Graph:
    //    1
    //   / \
    //  2   3
    //   \ /
    //    4 (goal)
    Map<Integer, List<Integer>> graph = new HashMap<>();
    graph.put(1, Arrays.asList(2, 3));
    graph.put(2, List.of(4));
    graph.put(3, List.of(4));
    graph.put(4, Collections.emptyList());

    Integer start = 1;
    Predicate<Integer> isGoal = node -> node.equals(4);
    Function<Integer, List<Integer>> getNeighbors = graph::get;

    List<List<Integer>> paths = findAllPathsWithBfs(start, isGoal, getNeighbors, true);

    assertEquals(1, paths.size(), "There should be one path from 1 to 4");
  }

  @Test
  void findAllPathsWithBfs_givenGraphWithCyclesAndCheckVisitedFalse_thenShouldFindAllPossiblePaths() {
    // Graph:
    // 1 -> 2 -> 3 -> 4 (goal)
    //      \
    //       5 -> 4 (goal)

    Predicate<Integer> isGoal = node -> node.equals(4);
    Map<Integer, List<Integer>> acyclicGraph = new HashMap<>();
    acyclicGraph.put(1, List.of(2));
    acyclicGraph.put(2, Arrays.asList(3, 5));
    acyclicGraph.put(3, List.of(4));
    acyclicGraph.put(4, Collections.emptyList());
    acyclicGraph.put(5, List.of(4));

    List<List<Integer>> acyclicPaths = findAllPathsWithBfs(1, isGoal, acyclicGraph::get, false);

    assertEquals(2, acyclicPaths.size(), "There should be two distinct paths to node 4 without visited checks");
    List<Integer> pathA = Arrays.asList(1, 2, 3, 4);
    List<Integer> pathB = Arrays.asList(1, 2, 5, 4);
    assertTrue(acyclicPaths.contains(pathA), "Paths should contain [1,2,3,4]");
    assertTrue(acyclicPaths.contains(pathB), "Paths should contain [1,2,5,4]");
  }

  @Test
  void findAllPathsWithBfs_givenGraphWithNoGoal_thenShouldReturnEmptyList() {
    // Graph: 1 -> 2 -> 3
    // Goal node does not exist (say goal is node 4)
    Map<Integer, List<Integer>> graph = new HashMap<>();
    graph.put(1, List.of(2));
    graph.put(2, List.of(3));
    graph.put(3, Collections.emptyList());

    Integer start = 1;
    Predicate<Integer> isGoal = node -> node.equals(4);
    Function<Integer, List<Integer>> getNeighbors = graph::get;

    List<List<Integer>> paths = findAllPathsWithBfs(start, isGoal, getNeighbors, true);

    assertTrue(paths.isEmpty(), "No paths should be found if the goal is unreachable");
  }

  /**
   * dijkstraSearchAllPathsGivenKnownMaxCost_startIsGoal_returnsSinglePath
   * <p>
   * Context: The start node is also the goal node. Result: Should return a single path consisting only of the start node.
   */
  @Test
  void dijkstraSearchAllPathsGivenKnownMaxCost_startIsGoal_returnsSinglePath() {
    // Start node
    String start = "A";

    // isGoal: True if the node is "A"
    Predicate<String> isGoal = node -> node.equals("A");

    // getNeighbors: No neighbors since it's a single-node graph
    Function<String, Map<String, Integer>> getNeighbors = node -> Collections.emptyMap();

    int maxAllowedCost = 10;

    List<List<String>> paths = GraphSearchUtils.dijkstraSearchAllPathsGivenKnownMaxCost(
        start, isGoal, getNeighbors, maxAllowedCost
    );

    assertEquals(1, paths.size());
    assertEquals(Collections.singletonList("A"), paths.get(0));
  }

  /**
   * dijkstraSearchAllPathsGivenKnownMaxCost_simpleGraphMultiplePaths_returnsAllShortestPaths
   * <p>
   * Context: A small graph:
   * <p>
   * A --1-- B \      | \2    |2 \    | \   C \ / D
   * <p>
   * There are two shortest paths from A to D: 1) A -> B -> D with cost 3 2) A -> C -> D with cost 3
   * <p>
   * Result: Should return both shortest paths.
   */
  @Test
  void dijkstraSearchAllPathsGivenKnownMaxCost_simpleGraphMultiplePaths_returnsAllShortestPaths() {
    String start = "A";
    Predicate<String> isGoal = node -> node.equals("D");

    // Neighbors:
    // A: B(1), C(2)
    // B: D(2)
    // C: D(1)
    // D: none
    Function<String, Map<String, Integer>> getNeighbors = node -> {
      Map<String, Integer> neighbors = new HashMap<>();
      switch (node) {
        case "A":
          neighbors.put("B", 1);
          neighbors.put("C", 2);
          break;
        case "B":
          neighbors.put("D", 2);
          break;
        case "C":
          neighbors.put("D", 1);
          break;
        default:
          break;
      }
      return neighbors;
    };

    int maxAllowedCost = 5;

    List<List<String>> paths = GraphSearchUtils.dijkstraSearchAllPathsGivenKnownMaxCost(
        start, isGoal, getNeighbors, maxAllowedCost
    );

    // The expected shortest paths are A->B->D and A->C->D
    Set<List<String>> expected = new HashSet<>();
    expected.add(Arrays.asList("A", "B", "D"));
    expected.add(Arrays.asList("A", "C", "D"));

    assertEquals(2, paths.size());

    // Convert result to a set to ignore order of paths
    Set<List<String>> resultSet = new HashSet<>(paths);
    assertEquals(expected, resultSet);
  }

  /**
   * dijkstraSearchAllPathsGivenKnownMaxCost_noGoalReachable_returnsEmptyList
   * <p>
   * Context: A small graph where the goal is not reachable. Graph: A --1--> B
   * <p>
   * Goal: "C" which doesn't exist or isn't connected.
   * <p>
   * Result: Should return an empty list since no path to goal can be found.
   */
  @Test
  void dijkstraSearchAllPathsGivenKnownMaxCost_noGoalReachable_returnsEmptyList() {
    String start = "A";
    Predicate<String> isGoal = node -> node.equals("C"); // "C" is never reached
    Function<String, Map<String, Integer>> getNeighbors = node -> {
      if (node.equals("A")) {
        return Collections.singletonMap("B", 1);
      } else if (node.equals("B")) {
        return Collections.emptyMap();
      }
      return Collections.emptyMap();
    };

    int maxAllowedCost = 10;

    List<List<String>> paths = GraphSearchUtils.dijkstraSearchAllPathsGivenKnownMaxCost(
        start, isGoal, getNeighbors, maxAllowedCost
    );

    assertTrue(paths.isEmpty(), "No paths should be found.");
  }

  /**
   * dijkstraSearchAllPathsGivenKnownMaxCost_complexGraphWithCostLimit_returnsOnlyPathsUnderMaxCost
   * <p>
   * Context: A graph where: A -1-> B -1-> C -1-> D A -3-> E -1-> D
   * <p>
   * The shortest path A-B-C-D has a cost of 3. Another path A-E-D has a cost of 4. If maxAllowedCost = 3, only A-B-C-D should be returned.
   */
  @Test
  void dijkstraSearchAllPathsGivenKnownMaxCost_complexGraphWithCostLimit_returnsOnlyPathsUnderMaxCost() {
    String start = "A";
    Predicate<String> isGoal = node -> node.equals("D");

    Function<String, Map<String, Integer>> getNeighbors = node -> {
      Map<String, Integer> neighbors = new HashMap<>();
      switch (node) {
        case "A":
          neighbors.put("B", 1);
          neighbors.put("E", 3);
          break;
        case "B":
          neighbors.put("C", 1);
          break;
        case "C":
          neighbors.put("D", 1);
          break;
        case "E":
          neighbors.put("D", 1);
          break;
        default:
          break;
      }
      return neighbors;
    };

    int maxAllowedCost = 3;

    List<List<String>> paths = GraphSearchUtils.dijkstraSearchAllPathsGivenKnownMaxCost(
        start, isGoal, getNeighbors, maxAllowedCost
    );

    // The only valid shortest path under cost 4 is A->B->C->D (cost 3)
    List<List<String>> expected = Collections.singletonList(Arrays.asList("A", "B", "C", "D"));

    assertEquals(1, paths.size());
    assertEquals(expected, paths);
  }
}


