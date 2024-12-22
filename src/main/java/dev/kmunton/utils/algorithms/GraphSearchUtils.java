package dev.kmunton.utils.algorithms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class GraphSearchUtils {

  /**
   * Performs the A* search algorithm to find the shortest path from the start node to the goal node.
   *
   * @param start        The starting node.
   * @param goal         The goal node.
   * @param getNeighbors Function to get neighbors and their edge weights for a given node.
   * @param heuristic    Function to estimate the cost from a node to the goal.
   * @param <T>          The type of the nodes.
   * @return A list representing the shortest path from start to goal, or an empty list if no path is found.
   */
  public static <T> List<T> aStarSearch(
      T start,
      Predicate<T> isGoal,
      Function<T, Map<T, Integer>> getNeighbors,
      ToIntFunction<T> heuristic
  ) {
    PriorityQueue<Node<T>> openSet = new PriorityQueue<>();
    Map<T, Integer> gCosts = new HashMap<>();
    Map<T, T> cameFrom = new HashMap<>();
    Set<T> openSetTracker = new HashSet<>();

    openSet.add(new Node<>(start, 0, heuristic.applyAsInt(start)));
    gCosts.put(start, 0);
    openSetTracker.add(start);

    while (!openSet.isEmpty()) {
      Node<T> current = openSet.poll();
      openSetTracker.remove(current.value);

      if (isGoal.test(current.value)) {
        return reconstructPath(cameFrom, current.value);
      }

      for (Map.Entry<T, Integer> neighborEntry : getNeighbors.apply(current.value).entrySet()) {
        T neighbor = neighborEntry.getKey();
        int tentativeGCost = gCosts.get(current.value) + neighborEntry.getValue();

        if (tentativeGCost < gCosts.getOrDefault(neighbor, Integer.MAX_VALUE)) {
          cameFrom.put(neighbor, current.value);
          gCosts.put(neighbor, tentativeGCost);

          int fCost = tentativeGCost + heuristic.applyAsInt(neighbor);
          if (!openSetTracker.contains(neighbor)) {
            openSet.add(new Node<>(neighbor, tentativeGCost, fCost));
            openSetTracker.add(neighbor);
          }
        }
      }
    }

    return List.of(); // No path found
  }

  public static <T, R> List<T> aStarSearchVisited(
      T start,
      Predicate<T> isGoal,
      Function<T, Map<T, Integer>> getNeighbors,
      ToIntFunction<T> heuristic,
      Function<T, R> addToVisited
  ) {
    PriorityQueue<Node<T>> openSet = new PriorityQueue<>();
    Map<T, Integer> gCosts = new HashMap<>();
    Map<T, T> cameFrom = new HashMap<>();
    Set<T> openSetTracker = new HashSet<>();
    Set<R> visited = new HashSet<>();

    openSet.add(new Node<>(start, 0, heuristic.applyAsInt(start)));
    gCosts.put(start, 0);
    openSetTracker.add(start);

    while (!openSet.isEmpty()) {
      Node<T> current = openSet.poll();
      openSetTracker.remove(current.value);
      visited.add(addToVisited.apply(current.value));

      if (isGoal.test(current.value)) {
        return reconstructPath(cameFrom, current.value);
      }

      for (Map.Entry<T, Integer> neighborEntry : getNeighbors.apply(current.value).entrySet()) {
        T neighbor = neighborEntry.getKey();
        if (visited.contains(addToVisited.apply(neighbor))) {
          continue;
        }

        int tentativeGCost = gCosts.get(current.value) + neighborEntry.getValue();

        if (tentativeGCost < gCosts.getOrDefault(neighbor, Integer.MAX_VALUE)) {
          cameFrom.put(neighbor, current.value);
          gCosts.put(neighbor, tentativeGCost);

          int fCost = tentativeGCost + heuristic.applyAsInt(neighbor);
          if (!openSetTracker.contains(neighbor)) {
            openSet.add(new Node<>(neighbor, tentativeGCost, fCost));
            openSetTracker.add(neighbor);
          }
        }
      }
    }

    return List.of(); // No path found
  }

  /**
   * Performs Dijkstra's algorithm to find the shortest path from the start node to the goal node.
   *
   * @param start        The starting node.
   * @param goal         The goal node.
   * @param getNeighbors Function to get neighbors and their edge weights for a given node.
   * @param <T>          The type of the nodes.
   * @return A list representing the shortest path from start to goal, or an empty list if no path is found.
   */
  public static <T> List<T> dijkstraSearch(
      T start,
      T goal,
      Function<T, Map<T, Integer>> getNeighbors
  ) {
    PriorityQueue<Node<T>> queue = new PriorityQueue<>();
    Map<T, Integer> gCosts = new HashMap<>();
    Map<T, T> cameFrom = new HashMap<>();
    Set<T> queueTracker = new HashSet<>();

    queue.add(new Node<>(start, 0, 0));
    gCosts.put(start, 0);
    queueTracker.add(start);

    while (!queue.isEmpty()) {
      Node<T> current = queue.poll();
      queueTracker.remove(current.value);

      if (current.value.equals(goal)) {
        return reconstructPath(cameFrom, goal);
      }

      for (Map.Entry<T, Integer> neighborEntry : getNeighbors.apply(current.value).entrySet()) {
        T neighbor = neighborEntry.getKey();
        int tentativeGCost = gCosts.get(current.value) + neighborEntry.getValue();

        if (tentativeGCost < gCosts.getOrDefault(neighbor, Integer.MAX_VALUE)) {
          cameFrom.put(neighbor, current.value);
          gCosts.put(neighbor, tentativeGCost);

          if (!queueTracker.contains(neighbor)) {
            queue.add(new Node<>(neighbor, tentativeGCost, 0)); // fCost not used in Dijkstra
            queueTracker.add(neighbor);
          }
        }
      }
    }

    return Collections.emptyList(); // No path found
  }

  public static <T> List<List<T>> dijkstraSearchAllPathsGivenKnownMaxCost(
      T start,
      Predicate<T> isGoal,
      Function<T, Map<T, Integer>> getNeighbors,
      int maxAllowedCost
  ) {
    PriorityQueue<Node<T>> queue = new PriorityQueue<>();
    Map<T, Integer> gCosts = new HashMap<>();
    Map<T, List<T>> parents = new HashMap<>();
    Set<T> queueTracker = new HashSet<>();

    gCosts.put(start, 0);
    parents.put(start, Collections.emptyList()); // start has no parents
    queue.add(new Node<>(start, 0, 0));
    queueTracker.add(start);

    // We'll store the nodes that qualify as goals with cost <= maxAllowedCost
    List<T> qualifyingGoals = new ArrayList<>();

    while (!queue.isEmpty()) {
      Node<T> current = queue.poll();
      queueTracker.remove(current.value);

      int currentCost = gCosts.get(current.value);
      if (isGoal.test(current.value) && currentCost <= maxAllowedCost) {
        // Record this goal for path reconstruction later
        qualifyingGoals.add(current.value);
        // Don't return here, because we might find other equally-short paths.
        // Continue to process other nodes to ensure all paths are found.
      }

      // Explore neighbors
      for (Map.Entry<T, Integer> neighborEntry : getNeighbors.apply(current.value).entrySet()) {
        T neighbor = neighborEntry.getKey();
        int edgeCost = neighborEntry.getValue();
        int tentativeGCost = currentCost + edgeCost;

        // If the tentative cost exceeds maxAllowedCost, no need to proceed
        if (tentativeGCost > maxAllowedCost) {
          continue;
        }

        int knownCost = gCosts.getOrDefault(neighbor, Integer.MAX_VALUE);

        if (tentativeGCost < knownCost) {
          // Found a strictly better path to 'neighbor'
          gCosts.put(neighbor, tentativeGCost);
          parents.put(neighbor, new ArrayList<>(List.of(current.value)));

          if (!queueTracker.contains(neighbor)) {
            queue.add(new Node<>(neighbor, tentativeGCost, 0));
            queueTracker.add(neighbor);
          }
        } else if (tentativeGCost == knownCost) {
          // Found another equally good path to 'neighbor'
          // Add current node to the parents list without removing existing ones
          parents.get(neighbor).add(current.value);
        }
      }
    }

    // Now we have:
    // - gCosts: the best known costs for all reachable nodes
    // - parents: lists of parents for each node on the shortest paths
    // - qualifyingGoals: all goal nodes reached with cost <= maxAllowedCost

    // Reconstruct all shortest paths for each qualifying goal
    List<List<T>> allPaths = new ArrayList<>();
    for (T goal : qualifyingGoals) {
      allPaths.addAll(reconstructAllPaths(parents, start, goal));
    }

    return allPaths;
  }

  /**
   * Reconstructs all shortest paths from start to goal using the parents map.
   */
  private static <T> List<List<T>> reconstructAllPaths(Map<T, List<T>> parents, T start, T goal) {
    List<List<T>> result = new ArrayList<>();
    LinkedList<T> path = new LinkedList<>();
    backtrackAllPaths(parents, start, goal, path, result);
    return result;
  }

  private static <T> void backtrackAllPaths(Map<T, List<T>> parents, T start, T current,
      LinkedList<T> path, List<List<T>> result) {
    path.addFirst(current);

    if (current.equals(start)) {
      // Reached the start, record a copy of the current path
      result.add(new ArrayList<>(path));
    } else {
      // Recurse for all parents of the current node
      List<T> preds = parents.get(current);
      if (preds != null) {
        for (T pred : preds) {
          backtrackAllPaths(parents, start, pred, path, result);
        }
      }
    }

    path.removeFirst();
  }


  /**
   * Performs Breadth-First Search (BFS) to find the shortest path (in terms of number of steps) from the start node to a node satisfying the goal
   * predicate.
   *
   * @param start        The starting node.
   * @param isGoal       Predicate to test if a node is the goal.
   * @param getNeighbors Function to get neighbors for a given node.
   * @param <T>          The type of the nodes.
   * @return A list representing the shortest path from start to the goal node, or an empty list if no path is found.
   */
  public static <T> List<T> shortestPathWithBfs(
      T start,
      Predicate<T> isGoal,
      Function<T, List<T>> getNeighbors
  ) {
    Queue<List<T>> queue = new LinkedList<>();
    Set<T> visited = new HashSet<>();
    queue.add(Collections.singletonList(start));
    visited.add(start);

    while (!queue.isEmpty()) {
      List<T> path = queue.poll();
      T current = path.getLast();

      if (isGoal.test(current)) {
        return path;
      }

      for (T neighbor : getNeighbors.apply(current)) {
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          List<T> newPath = new ArrayList<>(path);
          newPath.add(neighbor);
          queue.add(newPath);
        }
      }
    }

    return List.of(); // No path found
  }

  /**
   * Finds all paths from a starting node to any node that satisfies a given goal condition, using a Breadth-First Search (BFS) approach.
   *
   * <p>This method explores paths in a graph-like structure defined implicitly by a
   * "getNeighbors" function. Starting from the given start node, it generates paths by iteratively expanding the frontier of search, enqueuing each
   * successive node appended to the paths discovered. When a path reaches a goal node (as determined by the {@code isGoal} predicate), that path is
   * recorded in the results.
   *
   * <p>Optionally, the search can track visited nodes. When {@code checkVisited} is
   * {@code true}, once a node is encountered, it will not be revisited as part of any other path. This can prevent infinite loops in the presence of
   * cycles, but may also prevent finding multiple distinct paths to the same goal node. When {@code checkVisited} is {@code false}, all possible
   * paths will be discovered (barring infinite expansions due to cycles).
   *
   * @param <T>          the type of nodes
   * @param start        the starting node of the search
   * @param isGoal       a predicate that returns {@code true} if the given node is a goal node
   * @param getNeighbors a function that, given a node, returns its neighboring nodes
   * @param checkVisited if {@code true}, the search will never revisit already visited nodes; if {@code false}, nodes can appear in multiple paths
   * @return a list of all paths found from the start node to any goal node, where each path is represented as a list of nodes in the order they are
   * visited
   * @throws NullPointerException if any of the parameters {@code start}, {@code isGoal}, or {@code getNeighbors} are {@code null}
   */
  public static <T> List<List<T>> findAllPathsWithBfs(
      T start,
      Predicate<T> isGoal,
      Function<T, List<T>> getNeighbors,
      boolean checkVisited
  ) {
    Queue<List<T>> queue = new LinkedList<>();
    queue.add(Collections.singletonList(start));

    Set<T> visited = new HashSet<>();
    if (checkVisited) {
      visited.add(start);
    }

    List<List<T>> paths = new ArrayList<>();

    while (!queue.isEmpty()) {
      List<T> path = queue.poll();
      T current = path.getLast();

      if (isGoal.test(current)) {
        paths.add(path);
        continue;
      }

      for (T neighbor : getNeighbors.apply(current)) {
        if (checkVisited) {
          if (!visited.contains(neighbor)) {
            visited.add(neighbor);
            List<T> newPath = new ArrayList<>(path);
            newPath.add(neighbor);
            queue.add(newPath);
          }
          continue;
        }

        List<T> newPath = new ArrayList<>(path);
        newPath.add(neighbor);
        queue.add(newPath);

      }
    }

    return paths;
  }

  public static <T> Map<T, Integer> findAllStepsToEachPossibleNodeBfs(
      T start,
      Function<T, List<T>> getNeighbors
  ) {
    Queue<T> queue = new LinkedList<>();
    queue.add(start);

    Set<T> visited = new HashSet<>();
    Map<T, Integer> map = new HashMap<>();
    map.put(start, 0);
    visited.add(start);

    while (!queue.isEmpty()) {
      T current = queue.poll();
      int steps = map.get(current);

      for (T neighbor : getNeighbors.apply(current)) {
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          map.put(neighbor, steps + 1);
          queue.add(neighbor);
        }
      }
    }

    return map;
  }

  /**
   * Performs Depth-First Search (DFS) to determine if there is a path from the start node to a node satisfying the goal predicate.
   *
   * @param start        The starting node.
   * @param isGoal       Predicate to test if a node is the goal.
   * @param getNeighbors Function to get neighbors for a given node.
   * @param <T>          The type of the nodes.
   * @return True if a path to the goal node exists, false otherwise.
   */
  public static <T> boolean dfs(
      T start,
      Predicate<T> isGoal,
      Function<T, List<T>> getNeighbors
  ) {
    Deque<T> stack = new ArrayDeque<>();
    Set<T> visited = new HashSet<>();
    stack.push(start);

    while (!stack.isEmpty()) {
      T current = stack.pop();
      if (visited.contains(current)) {
        continue;
      }
      visited.add(current);

      if (isGoal.test(current)) {
        return true;
      }

      for (T neighbor : getNeighbors.apply(current)) {
        if (!visited.contains(neighbor)) {
          stack.push(neighbor);
        }
      }
    }

    return false;
  }

  /**
   * Reconstructs the path from the start node to the goal node using the cameFrom map.
   *
   * @param cameFrom Map of nodes to their predecessors.
   * @param goal     The goal node.
   * @param <T>      The type of the nodes.
   * @return A list representing the path from start to goal.
   */
  private static <T> List<T> reconstructPath(Map<T, T> cameFrom, T goal) {
    LinkedList<T> path = new LinkedList<>();
    T current = goal;

    while (current != null) {
      path.addFirst(current);
      current = cameFrom.get(current);
    }

    return path;
  }

  /**
   * Inner class representing a node in the search algorithms.
   *
   * @param <T> The type of the node value.
   */
  private static class Node<T> implements Comparable<Node<T>> {

    final T value;
    final int gCost; // Cost from start to this node
    final int fCost; // Estimated total cost (gCost + heuristic)

    Node(T value, int gCost, int fCost) {
      this.value = value;
      this.gCost = gCost;
      this.fCost = fCost;
    }

    @Override
    public int compareTo(Node<T> other) {
      return Integer.compare(this.fCost, other.fCost);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Node<?> node)) {
        return false;
      }
      return value.equals(node.value);
    }

    @Override
    public int hashCode() {
      return Objects.hash(value);
    }
  }

  /**
   * Finds the longest path from the start node to the goal node in a directed acyclic graph (DAG).
   *
   * @param start The starting node.
   * @param goal  The goal node.
   * @param graph The graph represented as a map of nodes to their neighbors and edge weights.
   * @param <T>   The type of the nodes.
   * @return A list representing the longest path from start to goal, or an empty list if no path exists.
   */
  public static <T> List<T> findLongestPathDirected(
      T start,
      T goal,
      Map<T, Map<T, Integer>> graph
  ) {
    Function<T, Map<T, Integer>> getNeighbors = graph::get;

    // Perform topological sort on the entire graph
    List<T> topoOrder = topologicalSort(graph);
    if (topoOrder.isEmpty() || !topoOrder.contains(goal)) {
      return List.of(); // Graph is not a DAG or goal is unreachable
    }

    // Initialize distances and predecessors
    Map<T, Integer> distances = new HashMap<>();
    Map<T, T> predecessors = new HashMap<>();
    for (T node : topoOrder) {
      distances.put(node, Integer.MIN_VALUE);
    }
    distances.put(start, 0);

    // Process nodes in topological order
    for (T node : topoOrder) {
      if (distances.get(node) == Integer.MIN_VALUE) {
        continue; // Skip unreachable nodes
      }

      Map<T, Integer> neighbors = getNeighbors.apply(node);
      if (neighbors == null) {
        neighbors = Map.of();
      }

      for (Map.Entry<T, Integer> neighborEntry : neighbors.entrySet()) {
        T neighbor = neighborEntry.getKey();
        int weight = neighborEntry.getValue();
        int newDistance = distances.get(node) + weight;

        if (newDistance > distances.getOrDefault(neighbor, Integer.MIN_VALUE)) {
          distances.put(neighbor, newDistance);
          predecessors.put(neighbor, node);
        }
      }
    }

    // Reconstruct the path from start to goal
    if (!distances.containsKey(goal) || distances.get(goal) == Integer.MIN_VALUE) {
      return List.of(); // Goal is unreachable
    }
    return reconstructPath(predecessors, goal);
  }

  /**
   * Finds the longest path from the start node to the goal node in an undirected graph.
   *
   * @param start        The starting node.
   * @param goal         The goal node.
   * @param getNeighbors Function to get neighbors and their edge weights for a given node.
   * @param <T>          The type of the nodes.
   * @return A list representing the longest path from start to goal, or an empty list if no path exists.
   */
  public static <T> List<T> findLongestPathUndirected(
      T start,
      T goal,
      Function<T, Map<T, Integer>> getNeighbors
  ) {
    Set<T> visited = new HashSet<>();
    List<T> longestPath = new ArrayList<>();
    List<T> currentPath = new ArrayList<>();
    currentPath.add(start);

    int[] maxLength = new int[]{0}; // Initialize to 0
    findLongestPathDFS(start, goal, getNeighbors, visited, currentPath, longestPath, 0, maxLength);

    return longestPath;
  }

  /**
   * Helper method for DFS traversal to find the longest path in an undirected graph.
   *
   * @param current       The current node.
   * @param goal          The goal node.
   * @param getNeighbors  Function to get neighbors and their edge weights for a given node.
   * @param visited       Set of visited nodes.
   * @param currentPath   The current path being explored.
   * @param longestPath   The longest path found so far.
   * @param currentLength The length of the current path.
   * @param maxLength     The maximum length found so far.
   * @param <T>           The type of the nodes.
   */
  @SuppressWarnings("java:S107")
  private static <T> void findLongestPathDFS(
      T current,
      T goal,
      Function<T, Map<T, Integer>> getNeighbors,
      Set<T> visited,
      List<T> currentPath,
      List<T> longestPath,
      int currentLength,
      int[] maxLength
  ) {
    if (current.equals(goal)) {
      if (currentLength >= maxLength[0]) {
        maxLength[0] = currentLength;
        longestPath.clear();
        longestPath.addAll(new ArrayList<>(currentPath));
      }
      return;
    }

    visited.add(current);

    // Safely get neighbors
    Map<T, Integer> neighbors = getNeighbors.apply(current);
    if (neighbors == null) {
      neighbors = Collections.emptyMap();
    }

    for (Map.Entry<T, Integer> neighborEntry : neighbors.entrySet()) {
      T neighbor = neighborEntry.getKey();
      int weight = neighborEntry.getValue();

      if (!visited.contains(neighbor)) {
        currentPath.add(neighbor);
        findLongestPathDFS(
            neighbor,
            goal,
            getNeighbors,
            visited,
            currentPath,
            longestPath,
            currentLength + weight,
            maxLength
        );
        currentPath.removeLast();
      }
    }

    visited.remove(current);
  }

  /**
   * Performs topological sorting on a directed graph.
   *
   * @param graph The graph represented as a map of nodes to their neighbors and edge weights.
   * @param <T>   The type of the nodes.
   * @return A list representing the topological order of nodes, or an empty list if the graph contains a cycle.
   */
  private static <T> List<T> topologicalSort(Map<T, Map<T, Integer>> graph) {
    Set<T> visited = new HashSet<>();
    Deque<T> stack = new ArrayDeque<>();

    for (T node : graph.keySet()) {
      if (!visited.contains(node) && dfsTopologicalSort(node, graph, visited, new HashSet<>(), stack)) {
        return List.of(); // Graph contains a cycle
      }

    }

    // Collect nodes from the stack into the topological order list
    List<T> topoOrder = new ArrayList<>();
    while (!stack.isEmpty()) {
      topoOrder.add(stack.pop());
    }
    return topoOrder;
  }

  /**
   * Helper method for DFS traversal during topological sorting.
   *
   * @param node           The current node.
   * @param graph          The graph represented as a map.
   * @param visited        Set of visited nodes.
   * @param recursionStack Set of nodes in the current recursion stack to detect cycles.
   * @param stack          Stack to store the topological order.
   * @param <T>            The type of the nodes.
   * @return True if a cycle is detected, false otherwise.
   */
  private static <T> boolean dfsTopologicalSort(
      T node,
      Map<T, Map<T, Integer>> graph,
      Set<T> visited,
      Set<T> recursionStack,
      Deque<T> stack
  ) {
    visited.add(node);
    recursionStack.add(node);

    Map<T, Integer> neighbors = graph.getOrDefault(node, Collections.emptyMap());
    for (T neighbor : neighbors.keySet()) {
      if (recursionStack.contains(neighbor)) {
        return true; // Cycle detected
      }
      if (!visited.contains(neighbor) && dfsTopologicalSort(neighbor, graph, visited, recursionStack, stack)) {
        return true; // Cycle detected
      }

    }

    recursionStack.remove(node);
    stack.push(node);
    return false;
  }
}


