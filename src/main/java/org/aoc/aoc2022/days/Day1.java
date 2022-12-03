package org.aoc.aoc2022.days;

import static org.aoc.common.util.Util.readInputFromFile;

import com.google.common.flogger.FluentLogger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.aoc.common.AoCDay;

public class Day1 implements AoCDay {

  public static final String INPUT_FILE = "Day1_input.txt";
  public static final int YEAR = 2022;

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();


  @Override
  public Number part1() {
    final List<List<Integer>> input = getInputAsList();

    return input.stream()
        .map(l -> l.stream().reduce(0, Integer::sum))
        .max(Comparator.naturalOrder())
        .orElse(0);
  }

  @Override
  public Number part2() {
    final List<List<Integer>> input = getInputAsList();

    return input.stream()
        .map(l -> l.stream().reduce(0, Integer::sum))
        .sorted(Comparator.reverseOrder())
        .limit(3)
        .reduce(0, Integer::sum);
  }

  private static List<List<Integer>> getInputAsList() {
    List<List<Integer>> data = new ArrayList<>();
    final List<String> input = Arrays.stream(readInputFromFile(YEAR, INPUT_FILE)).toList();
    List<Integer> currentList = new ArrayList<>();
    for(String line : input ) {
      if(!line.equals("")){
        currentList.add(Integer.valueOf(line));
      }else {
        data.add(currentList.stream().toList());
        currentList.clear();
      }
    }
    return data;
  }
}
