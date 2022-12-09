package org.aoc.aoc2022.days;

import static org.aoc.common.util.Util.readInputFromFile;

import com.google.common.flogger.FluentLogger;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.UnaryOperator;
import org.aoc.common.AoCDay;

public class Day8 implements AoCDay {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  public static final String INPUT_FILE = "Day8_input.txt";
  //public static final String INPUT_FILE = "test.txt";
  public static final int YEAR = 2022;

  private static final BiPredicate<Integer, Integer> moreThanOrEqualTo = (next, size) -> next >= size;
  private static final BiPredicate<Integer, Integer> lessThan = (next, size) -> next < size;
  private static final UnaryOperator<Integer> minusOne = n -> n-1;
  private static final UnaryOperator<Integer> plusOne = n -> n+1;

  private static final int[][] input = getInput();

  @Override
  public Number part1() {
    int visibleTrees = 0;
    for (int row = 0; row < input.length; row++) {
      for (int col = 0; col < input[row].length; col++) {
        if(isOnTheEdge(row, col) || isVisible(row, col)) {
          visibleTrees++;
        }
      }
    }
    return visibleTrees;
  }

  @Override
  public Number part2() {
    int maxScenicScore = 0;
    for (int row = 0; row < input.length; row++) {
      for (int col = 0; col < input[row].length; col++) {
        if(!isOnTheEdge(row, col)) {
          maxScenicScore = Math.max(calculateScenicScore(row, col), maxScenicScore);
        }
      }
    }
    return maxScenicScore;
  }

  private int calculateScenicScore(int row, int col) {
    final int value = input[row][col];

    final int north = verticalViewingDistance(moreThanOrEqualTo, 0, minusOne, col, row, value,0);
    final int south = verticalViewingDistance(lessThan, input.length, plusOne, col, row, value, 0);
    final int west = horizontalViewingDistance(moreThanOrEqualTo, 0, minusOne, col, row, value, 0);
    final int east = horizontalViewingDistance(lessThan, input[0].length, plusOne, col, row, value, 0);

    return north * west * east * south;
  }

  private boolean isOnTheEdge(int row, int col) {
    return col == 0
        || row == 0
        || col == input[row].length-1
        || row == input.length-1;
  }

  private static int verticalViewingDistance(BiPredicate<Integer, Integer> p,
      int stopAt, UnaryOperator<Integer> next, int col, int row, int originalValue, int viewDistance) {
    int nextRowIndex = next.apply(row);
    final boolean withinBounds = p.test(nextRowIndex, stopAt);

    if (!withinBounds) {
      return viewDistance;
    }else if(input[nextRowIndex][col] < originalValue){
      return verticalViewingDistance(p, stopAt, next, col, nextRowIndex, originalValue, ++viewDistance);
    }
    return ++viewDistance;
  }

  private static int horizontalViewingDistance(BiPredicate<Integer, Integer> p,
      int stopAt, UnaryOperator<Integer> next, int col, int row, int originalValue, int viewDistance) {

    int nextCol = next.apply(col);
    final boolean withinBounds = p.test(nextCol, stopAt);

    if (!withinBounds) {
      return viewDistance;
    }else if(input[row][nextCol] < originalValue){
      return horizontalViewingDistance(p, stopAt, next, nextCol, row, originalValue, ++viewDistance);
    }
    return ++viewDistance;
  }

  private boolean isVisible(int row, int col) {
    int value = input[row][col];
    return visibleVertically(0, row, col, value)
        || visibleVertically(row + 1, input.length, col, value)
        || visibleHorizontally(0, col, row, value)
        || visibleHorizontally(col + 1, input[0].length, row, value);
  }

  private boolean visibleVertically(int from, int to, int col, int value) {
    for (int i = from; i < to; i++)
      if (input[i][col] >= value) return false;
    return true;
  }

  private boolean visibleHorizontally(int from, int to, int row, int value) {
    for (int i = from; i < to; i++)
      if (input[row][i] >= value) return false;
    return true;
  }

  private static int[][] getInput() {
    final List<String> splitLines = Arrays.stream(readInputFromFile(YEAR, INPUT_FILE))
        .toList();

    int[][] input = new int[splitLines.size()][splitLines.get(0).length()];
    for(int i = 0; i < splitLines.size(); i++) {
      final char[] chars = splitLines.get(i).toCharArray();
      for (int j = 0; j < chars.length; j++) {
        input[i][j] = Character.getNumericValue(chars[j]);
      }
    }
    return input;
  }
}
