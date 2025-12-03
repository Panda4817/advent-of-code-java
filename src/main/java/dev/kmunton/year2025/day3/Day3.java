package dev.kmunton.year2025.day3;

import dev.kmunton.utils.days.Day;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Day3 implements Day<Long, Long> {

    private final List<BatteryBank> batteryBanks = new ArrayList<>();

    public Day3(List<String> input) {
        processData(input);
    }

    @Override
    public void processData(List<String> input) {
        input.forEach(line -> {
            final List<Integer> joltageList = Arrays
                    .stream(line.split(""))
                    .map(Integer::parseInt)
                    .toList();
            batteryBanks.add(new BatteryBank(joltageList));
        });
    }

    @Override
    public Long part1() {
        int digitNumber = 2;
        return batteryBanks.stream().mapToLong(bank ->
                largestJoltage(bank.joltageList(), digitNumber)).sum();
    }


    @Override
    public Long part2() {
        int digitNumber = 12;
        return batteryBanks.stream().mapToLong(bank ->
                largestJoltage(bank.joltageList(), digitNumber)).sum();
    }

    private long largestJoltage(List<Integer> joltageList, int digitNumber) {
        final List<Integer> digitIndexes = new ArrayList<>();
        int startIndex = 0;
        int endIndex = joltageList.size();
        for (int i = 0; i < digitNumber; i++) {
            final List<Integer> tempJoltageList = joltageList.subList(startIndex, endIndex);
            int digit = tempJoltageList.stream().reduce(0, Integer::max);
            int digitIndex = tempJoltageList.indexOf(digit) + startIndex;
            digitIndexes.add(digitIndex);
            int minIndex = digitIndexes.stream().min(Integer::compareTo).get();
            startIndex = digitIndex + 1;
            if (minIndex == digitIndex && (startIndex >= joltageList.size() || digitIndexes.contains(startIndex))) {
                endIndex = digitIndex;
                startIndex = 0;
                continue;
            }
            if (startIndex >= endIndex) {
                startIndex = digitIndex;
                endIndex = digitIndex;
                while (!digitIndexes.contains(startIndex - 1) || digitIndexes.contains(startIndex)) {
                    startIndex = startIndex - 1;
                    if (startIndex == 0) {
                        break;
                    }
                }
                while (digitIndexes.contains(endIndex - 1)) {
                    endIndex = endIndex - 1;
                }
            }


        }
        final String joltage = digitIndexes.stream()
                .sorted().map(i -> String.valueOf(joltageList.get(i))).collect(Collectors.joining());
        return Long.parseLong(joltage);
    }

}
