package org.aoc.aoc2021.days;

import java.util.Arrays;
import java.util.List;
import org.aoc.common.AoCDay;


import static org.aoc.common.util.Util.firstThreeResults;
import static org.aoc.common.util.Util.readInputFromFile;

public class Day1 implements AoCDay {

  public static final String INPUT_FILE = "Day1_input.txt";
  public static final int YEAR = 2021;

  @Override
  public Integer part1() {
    final List<Integer> inputList = getInputAsList();

    Integer lastMeasurement = null;
    var nrOfIncreases = 0;
    for (Integer measurement : inputList) {
      if (lastMeasurement != null && measurement > lastMeasurement) {
        nrOfIncreases += 1;
      }
      lastMeasurement = measurement;
    }
    return nrOfIncreases;
  }

  @Override
  public Integer part2() {
    final List<Integer> inputList = getInputAsList();

    return recursivePart2Solver(inputList, 0, 0);
  }

  private static Integer recursivePart2Solver(List<Integer> inputList, Integer lastSum, int nrOfIncrements) {
    final List<Integer> firstThreeResults = firstThreeResults(inputList);
    final int sum = firstThreeResults.stream().reduce(0, Integer::sum);

      if(sum > lastSum && lastSum != 0) {
        nrOfIncrements++;
      }

      var reducedList = inputList.subList(1, inputList.size());
      if (reducedList.size() > 2) {
        return recursivePart2Solver(reducedList, sum, nrOfIncrements);
      }

      return nrOfIncrements;
  }

  private static List<Integer> getInputAsList() {
    return Arrays.stream(readInputFromFile(YEAR, Day1.INPUT_FILE)).map(Integer::valueOf).toList();
  }
}
