package org.aoc.aoc2021.days;

import org.aoc.common.AoCDay;
import java.util.function.UnaryOperator;
import org.aoc.common.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Day7 implements AoCDay {

  public static final String INPUT_TXT = "Day7_input.txt";
  public static final int YEAR = 2021;

  @Override
  public Integer part1() {
    return calculateTotalFuelCosts(Integer::intValue).stream()
            .min(Integer::compareTo)
            .orElse(-1);
  }

  @Override
  public Number part2() {
    return calculateTotalFuelCosts(this::cumulativeIncrease).stream()
            .min(Integer::compareTo)
            .orElse(-1);
  }

  private List<Integer> calculateTotalFuelCosts(UnaryOperator<Integer> fuelModifier) {
    final List<Integer> submarinePos = extractInputData();
    int min = submarinePos.stream().min(Integer::compareTo).get();
    int max = submarinePos.stream().max(Integer::compareTo).get();

    List<Integer> totalFuelCostsPerAlternative = new ArrayList<>();
    for (int i : Util.getNumbersBetween(min, max)) {
      totalFuelCostsPerAlternative.add(
          submarinePos.stream()
              .map(pos -> Math.abs(pos - i))
              .map(fuelModifier)
              .reduce(0, Integer::sum));
    }
    return totalFuelCostsPerAlternative;
  }

  private int cumulativeIncrease(Integer distance) {
    var cost = 0;
    for (int step = 0; step < distance; step++) {
      cost += step;
    }
    return distance + cost;
  }

  private List<Integer> extractInputData() {
    return Arrays.stream(Util.readInputFromFile(YEAR, INPUT_TXT))
        .map(s -> Arrays.stream(s.split(",")).toList())
        .flatMap(List::stream)
        .map(Integer::valueOf)
        .toList();
  }
}
