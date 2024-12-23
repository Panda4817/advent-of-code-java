package dev.kmunton.year2024.day9;


import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day9 implements Day<Long, Long> {

  private final List<Integer> disk = new ArrayList<>();
  private final Map<Integer, Integer> files = new HashMap<>();
  private final Map<Integer, Integer> free = new TreeMap<>();

  public Day9(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    List<Integer> diskLengths = Arrays.stream(input.getFirst().split("")).map(Integer::parseInt).toList();
    boolean file = true;
    int fileIndex = 0;
    int diskIndex = 0;
    for (Integer value : diskLengths) {
      if (file) {
        files.put(fileIndex, value);
        for (long i = 0; i < value; i++) {
          disk.add(fileIndex);
        }
        fileIndex += 1;
        file = false;
        diskIndex += value;
        continue;
      }
      free.put(diskIndex, value);
      for (long i = 0; i < value; i++) {
        disk.add(-1);
      }
      file = true;
      diskIndex += value;
    }
  }

  @Override
  public Long part1() {
    List<Integer> copy = new ArrayList<>(disk);
    for (int i = disk.size() - 1; i > -1; i--) {
      int newIndex = copy.indexOf(-1);
      if (disk.get(i) == -1L || newIndex > i) {
        continue;
      }
      copy.set(newIndex, disk.get(i));
      copy.set(i, -1);
    }
    return checksum(copy);
  }

  @Override
  public Long part2() {
    List<Integer> copy = new ArrayList<>(disk);
    Set<Integer> doneFiles = new HashSet<>();
    for (int i = disk.size() - 1; i > -1; i--) {
      if (copy.get(i) == -1L || doneFiles.contains(copy.get(i))) {
        continue;
      }
      int file = copy.get(i);
      int fileLength = files.get(file);
      doneFiles.add(file);
      for (Entry<Integer, Integer> entry : free.entrySet()) {
        if (entry.getKey() < i && entry.getValue() >= fileLength) {
          int fileIndex = i;
          for (int j = entry.getKey(); j < entry.getKey() + fileLength; j++) {
            copy.set(j, copy.get(fileIndex));
            copy.set(fileIndex, -1);
            fileIndex -= 1;
          }
          free.put(entry.getKey() + fileLength, entry.getValue() - fileLength);
          free.put(entry.getKey(), 0);
          break;
        }
      }
    }
    return checksum(copy);
  }

  private long checksum(List<Integer> input) {
    long id = 0;
    long sum = 0;
    for (Integer value : input) {
      if (value == -1L) {
        id += 1;
        continue;
      }
      sum += (value * id);
      id += 1;
    }
    return sum;
  }

}
