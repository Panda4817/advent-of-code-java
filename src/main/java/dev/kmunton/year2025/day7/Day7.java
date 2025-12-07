package dev.kmunton.year2025.day7;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.GridPoint;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day7 implements Day<Long, Long> {

    private final List<GridPoint> splitters = new ArrayList<>();
    private GridPoint startPoint;
    private int maxY;

  public Day7(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
      maxY = input.size();
      int y = 0;
      for (String line : input) {
          int x = 0;
          for (char c : line.toCharArray()) {
              GridPoint point = new GridPoint(x, y);
              if (c == 'S') {
                  startPoint = point;
              }
              if (c == '^') {
                  splitters.add(point);
              }
              x++;
          }
          y++;
      }
  }

  @Override
  public Long part1() {
      Set<Integer> beams = new HashSet<>();
      beams.add(startPoint.x());
      long splitCount = 0;
      for (int y = 1; y < maxY; y++) {
          Set<Integer> newBeams = new HashSet<>();
          for (Integer beam : beams) {
              GridPoint point = new GridPoint(beam, y);
              if (splitters.contains(point)) {
                  newBeams.add(beam-1);
                  newBeams.add(beam+1);
                  splitCount++;
                  continue;
              }
              newBeams.add(beam);
          }
          beams = newBeams;
      }
      return splitCount;
  }

  @Override
  public Long part2() {
      Map<Integer, Long> beamMap = new HashMap<>();
      beamMap.put(startPoint.x(), 1L);
      for (int y = 1; y < maxY; y++) {
          Map<Integer, Long> newBeamMap = new HashMap<>();
          for (Integer beam : beamMap.keySet()) {
              GridPoint point = new GridPoint(beam, y);
              if (splitters.contains(point)) {
                  newBeamMap.put(beam-1, beamMap.get(beam) + newBeamMap.getOrDefault(beam-1, 0L));
                  newBeamMap.put(beam+1, beamMap.get(beam) + newBeamMap.getOrDefault(beam+1, 0L));
                  continue;
              }
              newBeamMap.put(beam, beamMap.get(beam) + newBeamMap.getOrDefault(beam, 0L));
          }
          beamMap = newBeamMap;
      }
      return beamMap.values().stream().mapToLong(Long::longValue).sum();
  }

}
