package dev.kmunton.year2025.day8;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.CubePoint;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Day8 implements Day<Long, Long> {

    private final List<CubePoint> junctions = new ArrayList<>();
    private final Set<Set<CubePoint>> circuits = new HashSet<>();
    private final Map<CubePoint, Set<CubePoint>> directConnections = new HashMap<>();
    private CubePoint closestJunction1 = null;
    private CubePoint closestJunction2 = null;

    public Day8(List<String> input) {
        processData(input);
    }

    @Override
    public void processData(List<String> input) {
        input.forEach(line -> {
            String[] split = line.split(",");
            CubePoint cubePoint = new CubePoint(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
            junctions.add(cubePoint);
        });
    }

    @Override
    public Long part1() {
        populateCircuitsForGivenPairs(1000);
        return multiplyLargestCircuits(circuits);
    }

    public Long part1_test() {
        populateCircuitsForGivenPairs(10);
        return multiplyLargestCircuits(circuits);
    }

    private long multiplyLargestCircuits(Set<Set<CubePoint>> circuits) {
        long[] circuitSizes = circuits.stream().mapToLong(Set::size).sorted().toArray();
        return circuitSizes[circuitSizes.length - 3] * circuitSizes[circuitSizes.length - 2] * circuitSizes[circuitSizes.length - 1];
    }

    private void populateCircuitsForGivenPairs(final int pairs) {
        circuits.clear();
        directConnections.clear();
        for (int i = 0; i < pairs; i++) {
            doConnection();
        }
    }

    private void doConnection() {
        double closestDistance = Double.MAX_VALUE;
        for (CubePoint cubePoint : junctions) {
            for (CubePoint other : junctions) {
                if (cubePoint.equals(other)) {
                    continue;
                }
                if (directConnections.containsKey(cubePoint) && directConnections.get(cubePoint).contains(other)) {
                    continue;
                }
                double distance = cubePoint.euclideanDistance(other);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestJunction1 = cubePoint;
                    closestJunction2 = other;
                }
            }
        }
        CubePoint finalClosestJunction1 = closestJunction1;
        CubePoint finalClosestJunction2 = closestJunction2;

        Set<CubePoint> connectionsSet = directConnections.getOrDefault(finalClosestJunction1, new HashSet<>());
        connectionsSet.add(finalClosestJunction2);
        directConnections.put(finalClosestJunction1, connectionsSet);
        connectionsSet = directConnections.getOrDefault(finalClosestJunction2, new HashSet<>());
        connectionsSet.add(finalClosestJunction1);
        directConnections.put(finalClosestJunction2, connectionsSet);

        Set<Set<CubePoint>> matchingCircuits = circuits.stream().filter(c -> c.contains(finalClosestJunction1) || c.contains(finalClosestJunction2)).collect(Collectors.toSet());
        if (matchingCircuits.isEmpty()) {
            circuits.add(new HashSet<>(List.of(finalClosestJunction1, finalClosestJunction2)));
        }
        if (matchingCircuits.size() == 2) {
            circuits.removeAll(matchingCircuits);
            Set<CubePoint> mergedCircuit = matchingCircuits.stream().flatMap(Collection::stream).collect(Collectors.toSet());
            circuits.add(mergedCircuit);
        }
        if (matchingCircuits.size() == 1) {
            circuits.removeAll(matchingCircuits);
            Set<CubePoint> mergedCircuit = matchingCircuits.stream().findFirst().get();
            mergedCircuit.addAll(List.of(finalClosestJunction1, finalClosestJunction2));
            circuits.add(mergedCircuit);
        }
    }


    @Override
    public Long part2() {
        circuits.clear();
        directConnections.clear();
        while (circuits.stream().findFirst().orElse(new HashSet<>()).size() != junctions.size()) {
            doConnection();
        }
        if (closestJunction1 == null || closestJunction2 == null) {
            throw new IllegalStateException("No closest Junctions found");
        }
        return ((long) closestJunction1.x() * closestJunction2.x());
    }

}
