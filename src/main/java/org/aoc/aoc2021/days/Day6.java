package org.aoc.aoc2021.days;

import org.aoc.common.AoCDay;
import org.aoc.common.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Day6 implements AoCDay {

  String input = "aoc2021/Day6_input.txt";

  @Override
  public Long part1() {
    return simulatePopulation(80, extractInputData());
  }

  @Override
  public Long part2() {
    return simulatePopulation(256, extractInputData());
  }

  private List<Long> extractInputData() {
    final Map<Integer, Long> groupByCount = Arrays.stream(Util.readFile(input)
                    .split(","))
            .map(Integer::valueOf)
            .collect(groupingBy(Integer::intValue, counting()));
    List<Long> longList = new ArrayList<>();
    for(Integer i : Util.getNumbersBetween(0,8)) {
      longList.add(i, groupByCount.getOrDefault(i, 0L));
    };
    return longList;
  }

  private long simulatePopulation(int days, List<Long> population) {
    if(days == 0) {
      return population.stream().reduce(0L, Long::sum);
    }
    final long zero = population.get(0);
    final long one = population.get(1);
    final long two = population.get(2);
    final long three = population.get(3);
    final long four = population.get(4);
    final long five = population.get(5);
    final long six = population.get(6);
    final long seven = population.get(7);
    final long eight = population.get(8);

    population.set(8, zero);
    population.set(7, eight);
    population.set(6, zero + seven);
    population.set(5, six);
    population.set(4, five);
    population.set(3, four);
    population.set(2, three);
    population.set(1, two);
    population.set(0, one);

    days--;
    return simulatePopulation(days, population);
  }
}
