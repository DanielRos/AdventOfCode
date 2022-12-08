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
  private static final  UnaryOperator<Integer> plusOne = n -> n+1;

  @Override
  public Number part1() {
    final int[][] input = getInput();
    int visibleTrees = 0;

    for (int row = 0; row < input.length; row++) {
      for (int col = 0; col < input[row].length; col++) {
        if(isOnTheEdge(row, col, input[row].length-1,input.length-1)
            || isVisible(row, col, input)) {
          visibleTrees++;
        }
      }
    }
    return visibleTrees;
  }

  @Override
  public Number part2() {
    final int[][] input = getInput();
    int maxScenicScore = 0;

    for (int row = 0; row < input.length; row++) {
      for (int col = 0; col < input[row].length; col++) {
        if(!isOnTheEdge(row, col, input[row].length-1,input.length-1)) {
          maxScenicScore = Math.max(calculateScenicScore(row, col, input), maxScenicScore);
        }
      }
    }
    return maxScenicScore;
  }

  private int calculateScenicScore(int row, int col, int[][] input) {
    final int value = input[row][col];
    //check above
    final int northView = verticalViewingDistance(moreThanOrEqualTo, 0, input, minusOne, col, row, value,0);
    //check below
    final int southView = verticalViewingDistance(lessThan, input.length, input, plusOne, col, row, value, 0);
    //check left
    final int westView = horizontalViewingDistance(moreThanOrEqualTo, 0, input, minusOne, col, row, value, 0);
    //check right
    final int eastView = horizontalViewingDistance(lessThan, input[0].length, input, plusOne, col, row, value, 0);

    return northView * westView * eastView * southView;
  }

  private boolean isOnTheEdge(int row, int col, int colSize, int rowSize) {
    return col == 0 ||
        row == 0 ||
        col == colSize ||
        row == rowSize;
  }

  private static int verticalViewingDistance(BiPredicate<Integer, Integer> p,
      int stopAt, int[][] input, UnaryOperator<Integer> next, int col, int row, int originalValue, int viewDistance) {
    int nextRowIndex = next.apply(row);
    final boolean withinBounds = p.test(nextRowIndex, stopAt);

    if (!withinBounds) {
      return viewDistance;
    }else if(input[nextRowIndex][col] < originalValue){
      return verticalViewingDistance(p, stopAt, input, next, col, nextRowIndex, originalValue, ++viewDistance);
    }
    return ++viewDistance;
  }

  private static int horizontalViewingDistance(BiPredicate<Integer, Integer> p,
      int stopAt, int[][] input, UnaryOperator<Integer> next, int col, int row, int originalValue, int viewDistance) {

    int nextCol = next.apply(col);
    final boolean withinBounds = p.test(nextCol, stopAt);

    if (!withinBounds) {
      return viewDistance;
    }else if(input[row][nextCol] < originalValue){

      return horizontalViewingDistance(p, stopAt, input, next, nextCol, row, originalValue, ++viewDistance);
    }
    return ++viewDistance;
  }

  private boolean isVisible(int row, int col, int[][] input) {
    final int value = input[row][col];
    //check above
    if(visibleFromVerticalAngle(moreThanOrEqualTo, 0, input,  minusOne, col, row, value)) return true;
    //check below
    if(visibleFromVerticalAngle(lessThan, input.length, input, plusOne, col, row, value)) return true;
    //check left
    if(visibleFromHorizontalAngle(moreThanOrEqualTo, 0, input, minusOne, col, row, value)) return true;
    //check right
    return visibleFromHorizontalAngle(lessThan, input[0].length, input, plusOne, col, row, value);
  }

  private static boolean visibleFromVerticalAngle(BiPredicate<Integer, Integer> p,
      int stopAt, int[][] input, UnaryOperator<Integer> next, int col, int row, int originalValue) {
    int nextRowIndex = next.apply(row);
    final boolean withinBounds = p.test(nextRowIndex, stopAt);

    if (!withinBounds) {
      return true;
    }else if(input[nextRowIndex][col] < originalValue){
      return visibleFromVerticalAngle(p, stopAt, input, next, col, nextRowIndex, originalValue);
    }
    return false;
  }

  private static boolean visibleFromHorizontalAngle(BiPredicate<Integer, Integer> p,
      int stopAt, int[][] input, UnaryOperator<Integer> next, int col, int row, int originalValue) {
    int nextCol = next.apply(col);
    final boolean withinBounds = p.test(nextCol, stopAt);

    if (!withinBounds) {
      return true;
    }else if(input[row][nextCol] < originalValue){
      return visibleFromHorizontalAngle(p, stopAt, input, next, nextCol, row, originalValue);
    }
    return false;
  }

  private int[][] getInput() {
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
