package org.aoc.aoc2021.days;

import org.aoc.common.AoCDay;
import org.aoc.common.util.Util;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static org.aoc.common.util.Util.charFrequencyCount;
import static org.aoc.common.util.Util.keyOfVal;
import static org.aoc.common.util.Util.sort;

public class Day8 implements AoCDay {

  private static final String INPUT_NAME = "Day8_input.txt";
  private static final int YEAR = 2021;

  @Override
  public Number part1() {

    final List<SimpleEntry<List<String>, List<String>>> data = dataInput();

    int uniqueNumberCount = 0;
    for (Entry<List<String>, List<String>> e : data) {
      for (String s : e.getValue()) {
        final int length = s.length();
        if (length >= 2 && length <= 4 || length == 7) {
          uniqueNumberCount++;
        }
      }
    }
    return uniqueNumberCount;
  }

  @Override
  public Number part2() {

    final List<SimpleEntry<List<String>, List<String>>> data = dataInput();

    List<Integer> outputValues = new ArrayList<>();
    for (Entry<List<String>, List<String>> e : data) {
      StringBuilder outputValue = new StringBuilder();
      Map<String, Integer> cipher = cipherInput(e.getKey());
      for (String s : e.getValue()) {
        outputValue.append(cipher.get(sort(s)));
      }
      outputValues.add(Integer.valueOf(outputValue.toString()));
    }
    return outputValues.stream().reduce(0, Integer::sum);
  }

  private Map<String, Integer> cipherInput(List<String> input) {
    HashMap<String, Integer> cipher = new HashMap<>();
    final List<String> sortedByLength = input.stream().sorted(Comparator.comparingInt(String::length)).toList();
    for (String s : sortedByLength) {
      String sortedInputStr = sort(s);
      final int length = sortedInputStr.length();
      if (mapLengthToNumber(length) > -1) {
        cipher.put(sortedInputStr, mapLengthToNumber(length));
      }else if(length == 5) {
        if(charFrequencyCount(sortedInputStr, keyOfVal(cipher, 7)) == 3){
          cipher.put(sortedInputStr,3);
        }
        else if(charFrequencyCount(sortedInputStr, keyOfVal(cipher,4)) == 3){
          cipher.put(sortedInputStr,5);
        }else {
          cipher.put(sortedInputStr,2);
        }
      } else if(length == 6) {
        if(charFrequencyCount(sortedInputStr, keyOfVal(cipher,4)) == 4){
          cipher.put(sortedInputStr,9);
        }
        else if(charFrequencyCount(sortedInputStr, keyOfVal(cipher,7)) == 3){
          cipher.put(sortedInputStr,0);
        }else {
          cipher.put(sortedInputStr,6);
        }
      }
    }
    return cipher;
  }

  private Integer mapLengthToNumber(int length) {
    int result = 0;
    switch(length) {
      case 2 -> result = 1;
      case 3 -> result = 7;
      case 4 -> result = 4;
      case 7 -> result = 8;
      default -> result = -1;
    }
    return result;
  }


  private List<SimpleEntry<List<String>,List<String>>> dataInput() {
    final List<SimpleEntry<String, String>> inout = Arrays.stream(Util.readInputFromFile(YEAR, INPUT_NAME))
            .map(s -> s.split("\\|"))
            .map(s -> new SimpleEntry<>(s[0], s[1]))
            .toList();
    List<SimpleEntry<List<String>,List<String>>> data = new ArrayList<>();
    inout.forEach(e -> {
      final List<String> key = Arrays.stream(e.getKey().split(" ")).toList();
      final List<String> val = Arrays.stream(e.getValue().split(" ")).filter(s -> !s.isBlank()).toList();
      data.add(new SimpleEntry<>(key,val));
    });
    return data;
  }
}
