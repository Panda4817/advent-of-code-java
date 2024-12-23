package dev.kmunton.year2023.day5;

import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day5 implements Day<Long, Long> {

  private static final String SOURCE = "source";
  private static final String DESTINATION = "destination";
  private static final String RANGE = "range";
  private final List<Long> seeds = new ArrayList<>();
  private final List<Map<String, Long>> seedToSoil = new ArrayList<>();
  private final List<Map<String, Long>> soilToFertilizer = new ArrayList<>();
  private final List<Map<String, Long>> fertilizerToWater = new ArrayList<>();
  private final List<Map<String, Long>> waterToLight = new ArrayList<>();
  private final List<Map<String, Long>> lightToTemperature = new ArrayList<>();
  private final List<Map<String, Long>> temperatureToHumidity = new ArrayList<>();
  private final List<Map<String, Long>> humidityToLocation = new ArrayList<>();

  public Day5(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    var currentMap = "";
    for (String line : input) {
      if (line.startsWith("seeds: ")) {
        String[] split = line.split(" ");
        for (int i = 1; i < split.length; i++) {
          seeds.add(Long.parseLong(split[i]));
        }
        continue;
      }
      if (line.startsWith("seed-to-soil map:")) {
        currentMap = "soil";
        continue;
      }
      if (line.startsWith("soil-to-fertilizer map:")) {
        currentMap = "fertilizer";
        continue;
      }
      if (line.startsWith("fertilizer-to-water map:")) {
        currentMap = "water";
        continue;
      }
      if (line.startsWith("water-to-light map:")) {
        currentMap = "light";
        continue;
      }
      if (line.startsWith("light-to-temperature map:")) {
        currentMap = "temperature";
        continue;
      }
      if (line.startsWith("temperature-to-humidity map:")) {
        currentMap = "humidity";
        continue;
      }
      if (line.startsWith("humidity-to-location map:")) {
        currentMap = "location";
        continue;
      }

      if (line.isBlank()) {
        currentMap = "";
        continue;
      }

      List<Long> numbers = Arrays.stream(line.split(" "))
                                 .map(String::trim).map(Long::parseLong).toList();

      var sourceStart = numbers.get(1);
      var destinationStart = numbers.get(0);
      var range = numbers.get(2);
      var map = new HashMap<String, Long>();
      map.put(SOURCE, sourceStart);
      map.put(DESTINATION, destinationStart);
      map.put(RANGE, range);
      switch (currentMap) {
        case "soil":
          seedToSoil.add(map);
          break;
        case "fertilizer":
          soilToFertilizer.add(map);
          break;
        case "water":
          fertilizerToWater.add(map);
          break;
        case "light":
          waterToLight.add(map);
          break;
        case "temperature":
          lightToTemperature.add(map);
          break;
        case "humidity":
          temperatureToHumidity.add(map);
          break;
        case "location":
          humidityToLocation.add(map);
          break;
      }

    }

  }

  public Long part1() {
    var seedToLocation = getSeedToLocation(seeds);
    return seedToLocation.values().stream().mapToLong(Long::longValue).min().orElseThrow();

  }

  public Long part2() {
    var lowestlocation = Long.MAX_VALUE;
    for (int i = 0; i < seeds.size(); i += 2) {
      var seedStart = seeds.get(i);
      var seedEnd = seedStart + seeds.get(i + 1) - 1;
      while ((seedEnd - seedStart) != 1) {
        Long middle = seedStart + Math.floorDiv((seedEnd - seedStart), 2);
        var seedToLocations = getSeedToLocation(List.of(seedStart, middle, seedEnd));
        if (seedToLocations.get(middle) <= seedToLocations.get(seedEnd)) {
          seedEnd = middle;
        } else {
          seedStart = middle;
        }
      }
      var seedToLocations = getSeedToLocation(List.of(seedStart, seedEnd));
      var location = seedToLocations.values().stream().mapToLong(Long::longValue).min().orElseThrow();
      if (location < lowestlocation) {
        lowestlocation = location;
      }

    }
    return lowestlocation;
  }

  private long getMapValue(long source, List<Map<String, Long>> maps) {
    for (var map : maps) {
      if (source >= map.get(SOURCE) && source < (map.get(SOURCE) + map.get(RANGE))) {
        return map.get(DESTINATION) + (source - map.get(SOURCE));
      }
    }
    return source;
  }

  private Map<Long, Long> getSeedToLocation(List<Long> seedList) {
    Map<Long, Long> seedToLocation = new HashMap<>();
    for (var seed : seedList) {
      var soil = getMapValue(seed, seedToSoil);
      var fertilizer = getMapValue(soil, soilToFertilizer);
      var water = getMapValue(fertilizer, fertilizerToWater);
      var light = getMapValue(water, waterToLight);
      var temperature = getMapValue(light, lightToTemperature);
      var humidity = getMapValue(temperature, temperatureToHumidity);
      var location = getMapValue(humidity, humidityToLocation);
      seedToLocation.put(seed, location);
    }
    return seedToLocation;
  }
}
