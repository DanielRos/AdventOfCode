package org.aoc.aoc2021.days;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.List;
import org.aoc.common.AoCDay;

import static org.aoc.common.util.Util.*;

public class Day2 implements AoCDay {

  public static final String INPUT_TXT = "Day2_Input.txt";
  public static final int YEAR = 2021;

  @Override
  public Integer part1() {
    final List<SimpleEntry<String, Integer>> inputList = extractInputData();

    int depth = 0;
    int horizontalPos = 0;

    for (SimpleEntry<String, Integer> entry : inputList) {
      switch (entry.getKey()) {
        case "forward" -> horizontalPos += entry.getValue();
        case "down" -> depth += entry.getValue();
        case "up" -> depth -= entry.getValue();
      }
    }

    return depth * horizontalPos;
  }

  @Override
  public Integer part2() {
    final List<SimpleEntry<String, Integer>> inputList = extractInputData();

    int depth = 0;
    int horizontalPos = 0;
    int aim = 0;

    for (SimpleEntry<String, Integer> entry : inputList) {
      switch (entry.getKey()) {
        case "forward" -> {
          horizontalPos += entry.getValue();
          depth += aim * entry.getValue();
        }
        case "down" -> aim += entry.getValue();
        case "up" -> aim -= entry.getValue();
      }
    }

    return depth * horizontalPos;
  }

  private static List<SimpleEntry<String, Integer>> extractInputData() {
    return Arrays.stream(readInputFromFile(YEAR, INPUT_TXT))
        .map(
            s -> {
              final String[] split = s.split(" ");
              return new SimpleEntry<>(split[0], Integer.valueOf(split[1]));
            })
        .toList();
  }
}
