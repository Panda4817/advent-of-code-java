package dev.kmunton.year2025.day6;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Problem {

    private List<Long> numbers = new ArrayList<>();
    private String operator;
    private Integer columnWidth;
    private List<List<String>> stringNumbers =  new ArrayList<>();

    public Problem(List<Long> newNumbers, String operator) {
        this.numbers = newNumbers;
        this.operator = operator;
    }

    public Problem() {

    }
}
