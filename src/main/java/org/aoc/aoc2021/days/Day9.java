package org.aoc.aoc2021.days;

import static org.aoc.common.util.Util.readInputFromFile;

import com.google.common.flogger.FluentLogger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.aoc.common.AoCDay;

public class Day9 implements AoCDay {

  public static final String INPUT_FILE = "Day9_input.txt";

  public static final int YEAR = 2021;

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  @Override
  public Number part1() {
    Integer totalRiskLevel = 0;
    final int[][] input = getInput();
    for (int row = 0; row < input.length; row++) {
      final int[] columns = input[row];
      for (int col = 0; col < columns.length; col++) {
        if (isLowPoint(row, col, input)) {
          totalRiskLevel += input[row][col] + 1;
          logger.atInfo().log("lowpoint found: %d", input[row][col]);
        }
      }
    }
    return totalRiskLevel;
  }

  @Override
  public Number part2() {
    List<Integer> basinSizes = new ArrayList<>();
    final int[][] input = getInput();
    for (int row = 0; row < input.length; row++) {
      final int[] columns = input[row];
      for (int col = 0; col < columns.length; col++) {
        if (isLowPoint(row, col, input)) {
          logger.atInfo().log("lowpoint found %d", input[row][col]);
          Set<Position> checkedPositions = new HashSet<>();
          basinSizes.add(findBasinSize(row, col, input, 0, checkedPositions));
        }
      }
    }
    return basinSizes.stream()
        .sorted(Comparator.reverseOrder()).limit(3)
        .reduce(1, (a, b) -> a * b);
  }

  private boolean isLowPoint(int row, int col, int[][] input) {
    //check above
    if (row - 1 >= 0 && input[row - 1][col] <= input[row][col]) {
      return false;
    }
    //check below
    if (row + 1 < input.length && input[row + 1][col] <= input[row][col]) {
      return false;
    }
    //check left
    if (col - 1 >= 0 && input[row][col - 1] <= input[row][col]) {
      return false;
    }
    //check right
    if (col + 1 < input[row].length && input[row][col + 1] <= input[row][col]) {
      return false;
    }
    return true;
  }

  private Integer findBasinSize(int row, int col, int[][] input, int size,
      Set<Position> checkedPositions) {
    if (input[row][col] < 9) {
      checkedPositions.add(new Position(row, col));
      logger.atInfo()
          .log("basin resident found at row: %d col: %d with value %d", row, col, input[row][col]);
      size += 1;
    }

    final int northRow = row - 1;
    if (northRow >= 0 && input[northRow][col] < 9 && !checkedPositions.contains(
        new Position(northRow, col))) {
      size = findBasinSize(northRow, col, input, size, checkedPositions);
    }

    final int southRow = row + 1;
    if (southRow < input.length && input[southRow][col] < 9 && !checkedPositions.contains(
        new Position(southRow, col))) {
      size = findBasinSize(southRow, col, input, size, checkedPositions);
    }

    final int westCol = col - 1;
    if (westCol >= 0 && input[row][westCol] < 9 && !checkedPositions.contains(
        new Position(row, westCol))) {
      size = findBasinSize(row, westCol, input, size, checkedPositions);
    }

    final int eastCol = col + 1;
    if (eastCol < input[row].length && input[row][eastCol] < 9 && !checkedPositions.contains(
        new Position(row, eastCol))) {
      size = findBasinSize(row, eastCol, input, size, checkedPositions);
    }

    return size;
  }

  private static int[][] getInput() {
    final String[] input = readInputFromFile(YEAR, INPUT_FILE);
    int[][] data = new int[input.length][];
    for (int i = 0; i < input.length; i++) {
      String line = input[i];
      int size = line.length();
      data[i] = new int[size];
      for (int j = 0; j < size; j++) {
        data[i][j] = Character.getNumericValue(line.charAt(j));
      }
    }
    return data;
  }

  private class Position {

    private final int row;

    private final int col;

    public Position(int row, int col) {
      this.row = row;
      this.col = col;
    }

    public int getRow() {
      return row;
    }

    public int getCol() {
      return col;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Position position = (Position) o;

      if (row != position.row) {
        return false;
      }
      return col == position.col;
    }

    @Override
    public int hashCode() {
      int result = row;
      result = 31 * result + col;
      return result;
    }
  }
}
