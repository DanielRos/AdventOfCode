package org.aoc.aoc2021.days;

import static org.aoc.common.util.Util.readInputFromFile;

import com.google.common.flogger.FluentLogger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.aoc.common.AoCDay;

public class Day10 implements AoCDay {

  public static final String INPUT_FILE = "Day10_input.txt";
  //339516609public static final String INPUT_FILE = "test.txt";

  public static final int YEAR = 2021;

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  @Override
  public Number part1() {
    final List<String> input = getInput();
    int score = 0;
    for (String line : input) {
      score += findIllegalCharacterPoints(line);
    }
    return score;
  }

  private Integer findIllegalCharacterPoints(String line) {
    final List<Character> chars = line.codePoints().mapToObj(c -> (char) c).toList();
    ArrayList<Character> openingChars = new ArrayList<>();
    for (char c : chars) {
      if (isClosingCharacter(c)) {
        if(openingChars.get(openingChars.size()-1).equals(expectedOppositeChar(c))){
          openingChars.remove(openingChars.size()-1);
        }else {
          return scoreCharacter(c);
        }
      }else {
        openingChars.add(c);
      }
    }
    return 0;
  }

  private Integer scoreCharacter(char c) {
    return switch (c) {
      case ')' -> 3;
      case ']' -> 57;
      case '}' -> 1197;
      case '>' -> 25137;
      default -> 0;
    };
  }

  private boolean isClosingCharacter(char c) {
    return c == ']' || c == ')' || c == '}' || c == '>';
  }

  private char expectedOppositeChar(char c) {
    return switch (c) {
      case ')' -> '(';
      case ']' -> '[';
      case '}' -> '{';
      case '>' -> '<';
      case '(' -> ')';
      case '[' -> ']';
      case '{' -> '}';
      case '<' -> '>';
      default -> throw new RuntimeException("unrecognized character");
    };
  }

  @Override
  public Number part2() {
    final List<String> input = getInput();
    /*List<Integer> lineScores = new ArrayList<>();
    for (String line : input) {
      int score = 0;
      for(Character autoChar : completeLine(line)) {
        score *= 5;
        score += scoreAutocompleteCharacter(autoChar);
      }
      if(score > 0){
        lineScores.add(score);
      }
    }
    Collections.sort(lineScores);
    logger.atInfo().log(lineScores.toString());

    return lineScores.get(lineScores.size()/2);*/
    List<Long> scoreList = new ArrayList<>();
    for (String line : validLines(input)) {
      long score = 0;

      List<String> lineList = new ArrayList<>(Arrays.asList(line.split("")));
      var done = false;
      while (!done) {
        for (int i = 0; i < lineList.size(); i++) {
          var actual = lineList.get(i);
          if (actual.equals("[") && i + 1 < lineList.size() && lineList.get(i + 1).equals("]")
              || actual.equals("<") && i + 1 < lineList.size() && lineList.get(i + 1).equals(">")
              || actual.equals("{") && i + 1 < lineList.size() && lineList.get(i + 1).equals("}")
              || actual.equals("(") && i + 1 < lineList.size() && lineList.get(i + 1).equals(")")) {
            lineList.remove(i + 1);
            lineList.remove(i);
            break;
          }
          if (i == lineList.size() - 1) {
            done = true;
          }
        }

      }
      while (lineList.size() != 0) {
        var actual = lineList.get(lineList.size() - 1);
        switch (actual) {
          case "(":
            score = score * 5 + 1;
            lineList.remove(lineList.size() - 1);
            break;
          case "{":
            score = score * 5 + 3;
            lineList.remove(lineList.size() - 1);
            break;
          case "<":
            score = score * 5 + 4;
            lineList.remove(lineList.size() - 1);
            break;
          case "[":
            score = score * 5 + 2;
            lineList.remove(lineList.size() - 1);
            break;
        }
      }
      scoreList.add(score);
    }
    scoreList = scoreList.stream().sorted().collect(Collectors.toList());
    return scoreList.get(scoreList.size()/2);

  }

  private String[] validLines(List<String> inputLines) {
    List<String> validLines = new ArrayList<>();
    for (String line : inputLines) {
      int score = 0;
      var lineList = Arrays.asList(line.split(""));
      List<String> changingList = new ArrayList<>();
      for (int i = 0; i < lineList.size(); i++) {
        var actual = lineList.get(i);
        if (actual.equals("[") || actual.equals("(") || actual.equals("{") || actual.equals("<")) {
          changingList.add(actual);
        } else {
          var changeListSize = changingList.size();
          switch (actual) {
            case ")":
              if (changingList.get(changeListSize - 1).equals("(")) {
                changingList.remove(changeListSize - 1);
              } else
                score += 3;
              break;
            case "}":
              if (changingList.get(changeListSize - 1).equals("{")) {
                changingList.remove(changeListSize - 1);
              } else
                score += 1197;
              break;
            case ">":
              if (changingList.get(changeListSize - 1).equals("<")) {
                changingList.remove(changeListSize - 1);
              } else
                score += 25137;
              break;
            case "]":
              if (changingList.get(changeListSize - 1).equals("[")) {
                changingList.remove(changeListSize - 1);
              } else
                score += 57;
              break;
          }
        }

      }
      if (score == 0) {
        validLines.add(line);
      }
    }
    return validLines.toArray(new String[0]);
  }

  private List<Character> completeLine(String line) {
    final List<Character> chars = line.codePoints().mapToObj(c -> (char) c).toList();
    ArrayList<Character> openingChars = new ArrayList<>();
    for (char c : chars) {
      if (isClosingCharacter(c)) {
        if(openingChars.get(openingChars.size()-1).equals(expectedOppositeChar(c))){
          openingChars.remove(openingChars.size()-1);
        }
        else {

          return Collections.emptyList();
        }
      }else {
        openingChars.add(c);
      }
    }

    ArrayList<Character> charsToComplete = new ArrayList<>();
    Collections.reverse(openingChars);
    for (Character c : openingChars) {
      charsToComplete.add(expectedOppositeChar(c));
    }

    return charsToComplete;
  }

  private Integer scoreAutocompleteCharacter(char c) {
    return switch (c) {
      case ')' -> 1;
      case ']' -> 2;
      case '}' -> 3;
      case '>' -> 4;
      default -> 0;
    };
  }

  private static List<String> getInput() {
    return Arrays.asList(readInputFromFile(YEAR, INPUT_FILE));
  }
}
