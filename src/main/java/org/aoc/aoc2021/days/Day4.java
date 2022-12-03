package org.aoc.aoc2021.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.aoc.common.AoCDay;

import static org.aoc.common.util.Util.partition;
import static org.aoc.common.util.Util.readInputFromFile;

public class Day4 implements AoCDay {

  public static final String INPUT_TXT = "Day4_input.txt";
  public static final int YEAR = 2021;

  public Integer part1() {
    final Bingo bingo = new Bingo(YEAR, INPUT_TXT);
    bingo.play();
    return bingo.getStandings().get(1).getScore();
  }

  public Integer part2() {
    final Bingo bingo = new Bingo(YEAR, INPUT_TXT);
    bingo.play();
    final HashMap<Integer, BingoBoard> standings = bingo.getStandings();
    return standings.get(standings.size()).getScore();
  }
}

class Bingo {
  List<Integer> numbers;
  List<BingoBoard> boards = new ArrayList<>();
  HashMap<Integer, BingoBoard> standings = new HashMap<>();
  int position = 1;

  public Bingo(int year, String input) {
    final List<String> inputList = List.of(readInputFromFile(year, input));
    this.numbers = Arrays.stream(inputList.get(0).split(",")).map(Integer::valueOf).toList();
    createBoardsFromInput(inputList);
  }

  private void createBoardsFromInput(List<String> inputList) {
    List<List<List<Integer>>> partitionedByBoard = partition(
            inputList.stream()
            .skip(1)
            .filter(b -> !b.isBlank())
            .map(str -> Arrays.stream(str.split(" ")).filter(b -> !b.isBlank()).map(Integer::valueOf).toList())
            .toList(), 5);
    for(List<List<Integer>> board : partitionedByBoard) {
      boards.add(new BingoBoard(board));
    }
  }

  public void play() {
    for(Integer number : numbers) {
      boards.stream()
              .filter(board -> !board.getBingo())
              .forEach(board -> board.markNumber(number));

      final List<BingoBoard> bingos = boards.stream()
              .filter(BingoBoard::isBingo)
              .filter(board -> board.getScore() == 0)
              .toList();

      for(BingoBoard board : bingos){
        board.setBingo(true);
        board.setScore(board.unmarkedNumberSum() * number);
        standings.put(position, board);
        position++;
      }
    }
  }

  public HashMap<Integer, BingoBoard> getStandings() {
    return standings;
  }
}

class BingoBoard {
  List<List<BingoNumber>> bingoBoard;
  boolean bingo = false;
  int score = 0;

  public BingoBoard(List<List<Integer>> board) {
    bingoBoard = board.stream().map(row -> row.stream().map(BingoNumber::new).toList()).toList();
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public boolean isBingo() {
    if(bingo) {return true;}
    for(List<BingoNumber> row : bingoBoard) {
      if(row.stream().allMatch(BingoNumber::isMarked)) {
        return true;
      }
    }
    for(int i = 0; i < bingoBoard.size(); i++) {
      List<BingoNumber> column = new ArrayList<>();
      for (List<BingoNumber> bingoNumbers : bingoBoard) {
        column.add(bingoNumbers.get(i));
      }
      if(column.stream().allMatch(BingoNumber::isMarked)) {
        return true;
      }
    }
    return false;
  }

  public void markNumber(Integer number) {
    bingoBoard.stream().flatMap(List::stream).forEach(bn ->bn.mark(number));
  }

  public Integer unmarkedNumberSum() {
    return bingoBoard.stream()
        .flatMap(List::stream)
        .filter(bn -> !bn.isMarked())
        .map(BingoNumber::getNumber)
            .reduce(0, Integer::sum);
  }

  public void setBingo(boolean b) {
    this.bingo = b;
  }

  public boolean getBingo() {
    return this.bingo;
  }
}

class BingoNumber {
  Integer number;
  boolean marked = false;

  public BingoNumber(Integer number) {
    this.number = number;
  }

  public void mark(Integer number) {
    if(Objects.equals(number, this.number)) {
      marked = true;
    }
  }

  public boolean isMarked() {
    return marked;
  }

  public Integer getNumber() {
    return number;
  }
}
