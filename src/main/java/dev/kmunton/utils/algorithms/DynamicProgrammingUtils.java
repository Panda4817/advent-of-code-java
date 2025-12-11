package dev.kmunton.utils.algorithms;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@UtilityClass
@Slf4j
public class DynamicProgrammingUtils {

    public static <T> long countPathsInDAG(
            Map<T, List<T>> graph,
            T start,
            T goal,
            Set<T> required
    ) {
        if (required == null) {
            required = Set.of();  // treat null as empty
        }

        // cur and nxt store: node -> Map<Set<T>, Long>
        Map<T, Map<Set<T>, Long>> cur = new HashMap<>();
        Map<T, Map<Set<T>, Long>> nxt = new HashMap<>();

        // At start: 1 path, seen no required nodes yet
        Map<Set<T>, Long> startMap = new HashMap<>();
        startMap.put(Collections.emptySet(), 1L);
        cur.put(start, startMap);

        long total = 0;

        while (!cur.isEmpty()) {

            for (Map.Entry<T, Map<Set<T>, Long>> entry : cur.entrySet()) {
                T node = entry.getKey();
                Map<Set<T>, Long> stateMap = entry.getValue();

                // If we've reached the goal, count valid paths
                if (node.equals(goal)) {

                    // If no required nodes, ALL paths count
                    if (required.isEmpty()) {
                        for (long ways : stateMap.values()) {
                            total += ways;
                        }
                    } else {
                        // Otherwise only count those whose seen-set covers all required
                        for (Map.Entry<Set<T>, Long> st : stateMap.entrySet()) {
                            if (st.getKey().containsAll(required)) {
                                total += st.getValue();
                            }
                        }
                    }

                    continue; // do not propagate past goal
                }

                // Propagate to neighbors
                for (T next : graph.getOrDefault(node, List.of())) {

                    Map<Set<T>, Long> nextMap =
                            nxt.computeIfAbsent(next, k -> new HashMap<>());

                    boolean nextIsRequired = required.contains(next);

                    for (Map.Entry<Set<T>, Long> st : stateMap.entrySet()) {
                        Set<T> oldSet = st.getKey();
                        long ways = st.getValue();

                        // build new seen-required set
                        Set<T> newSet;

                        if (nextIsRequired) {
                            newSet = new HashSet<>(oldSet);
                            newSet.add(next);
                        } else {
                            newSet = oldSet; // safe, treated as immutable
                        }

                        nextMap.merge(newSet, ways, Long::sum);
                    }
                }
            }

            // move frontier forward
            cur = nxt;
            nxt = new HashMap<>();
        }

        return total;
    }
}
