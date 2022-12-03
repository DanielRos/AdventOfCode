package org.aoc.aoc2021.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.aoc.common.AoCDay;

import static java.util.stream.Collectors.joining;
import static org.aoc.common.util.Util.readInputFromFile;

public class Day3 implements AoCDay {

  public static final String INPUT_TXT = "Day3_input.txt";
  public static final int YEAR = 2021;

  public Integer part1() {
    final List<List<Integer>> binaryList = extractInputData();
    StringBuilder epsilonBits = new StringBuilder();
    for (int column = 0; column < binaryList.get(0).size(); column++) {
      var columnList = new ArrayList<>();
      for (List<Integer> integers : binaryList) {
        columnList.add(integers.get(column));
      }
      if (Collections.frequency(columnList, 1) > columnList.size() / 2) {
        epsilonBits.append(1);
      } else {
        epsilonBits.append(0);
      }
    }
    int epsilon = Integer.parseInt(epsilonBits.toString(), 2);
    int gamma = Integer.parseInt(flipBits(epsilonBits), 2);

    return epsilon * gamma;
  }

  public Integer part2() {
    int oxygenGeneratorRating = findRating(extractInputData(), 0, false);
    int co2ScrubberRating = findRating(extractInputData(), 0, true);

    return oxygenGeneratorRating * co2ScrubberRating;
  }

  private static int findRating(List<List<Integer>> binaryList, int column, boolean preferLower) {
    if (binaryList.size() == 1) {
      final String collect = binaryList.get(0).stream().map(String::valueOf).collect(joining(""));
      return Integer.parseInt(collect, 2);
    }

    var columnList = new ArrayList<>();
    int finalColumn = column;
    binaryList.forEach(row -> columnList.add(row.get(finalColumn)));

    final int frequencyOfZeroes = Collections.frequency(columnList, 0);
    final double halfOfTheSize = columnList.size() / 2.0;
    List<List<Integer>> winners;

    if ((frequencyOfZeroes > halfOfTheSize && !preferLower)
        || (frequencyOfZeroes < halfOfTheSize && preferLower)
        || (frequencyOfZeroes == halfOfTheSize && preferLower)) {
      winners = getWinners(binaryList, column, 0);
    } else {
      winners = getWinners(binaryList, column, 1);
    }

    column++;
    return findRating(winners, column, preferLower);
  }

  private static List<List<Integer>> getWinners(
      List<List<Integer>> binaryList, int column, int winningNumber) {
    return binaryList.stream().filter(row -> row.get(column) == winningNumber).toList();
  }

  private static String flipBits(StringBuilder epsilonBits) {
    StringBuilder flippedBits = new StringBuilder();
    for (int i = 0; i < epsilonBits.length(); i++) {
      if (Character.getNumericValue(epsilonBits.charAt(i)) == 1) {
        flippedBits.append(0);
      } else {
        flippedBits.append(1);
      }
    }
    return flippedBits.toString();
  }

  private static List<List<Integer>> extractInputData() {
    return Arrays.stream(readInputFromFile(YEAR, INPUT_TXT))
        .map(
            binaryRow ->
                binaryRow.chars().mapToObj(c -> Character.getNumericValue((char) c)).toList())
        .toList();
  }
}
