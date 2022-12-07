package org.aoc.aoc2022.days;

import static org.aoc.common.util.Util.readInputFromFile;

import com.google.common.flogger.FluentLogger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import org.aoc.common.AoCDay;

public class Day7 implements AoCDay {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  public static final String INPUT_FILE = "Day7_input.txt";
  //public static final String INPUT_FILE = "test.txt";
  public static final int YEAR = 2022;

  @Override
  public Number part1() {
    final List<TerminalOutput> terminalOutput = getTerminalOutput();

    Directory root = createDirectories(terminalOutput);

    return root.flatten().stream()
        .map(Directory::getDirectorySize)
        .filter(size -> size <= 100000)
        .reduce(0, Integer::sum);
  }

  @Override
  public Number part2() {
    final List<TerminalOutput> terminalOutput = getTerminalOutput();

    Directory root = createDirectories(terminalOutput);
    int availableSpace = 70000000;
    int usedSpace = root.getDirectorySize();
    int unusedSpace = availableSpace - usedSpace;
    int toBeRemoved = 30000000 - unusedSpace;

    return root.flatten().stream()
        .map(Directory::getDirectorySize)
        .filter(size -> size >= toBeRemoved)
        .min(Comparator.naturalOrder())
        .orElseThrow();
  }

  private List<TerminalOutput> getTerminalOutput() {
    return Arrays.stream(readInputFromFile(YEAR, INPUT_FILE))
        .filter(s -> s.startsWith("$ c") || s.matches("[0-9].*"))
        .map(TerminalOutput::new)
        .toList();
  }

  private Directory createDirectories(List<TerminalOutput> terminalOutput) {
    Deque<Directory> navigation = new ArrayDeque<>();
    Directory currentDir = null;
    Directory root = null;
    for (TerminalOutput out : terminalOutput) {
      if(out.isCommand && out.name.equals("..")) {
        navigation.pop();
        currentDir = navigation.getFirst();
      }else if(out.isCommand){
        final Directory newDir = new Directory(out.name);
        navigation.push(newDir);
        if(currentDir != null) {
          currentDir.addDirectory(newDir);
        } else {
          root = newDir;
        }
        currentDir = newDir;
      } else {
        currentDir.addFile(out.size);
      }
    }
    return root;
  }

  private static class Directory {

    String name;
    List<Directory> directories = new ArrayList<>();

    Integer totalFileSize = 0;

    public Directory(String name) {
        this.name = name;
    }

    private void addFile(Integer size) {
      totalFileSize += size;
    }

    private void addDirectory(Directory dir) {
        directories.add(dir);
    }

    public Integer getDirectorySize() {
      return totalFileSize + directories.stream()
          .map(Directory::getDirectorySize)
          .reduce(0, Integer::sum);
    }

    public List<Directory> flatten() {
      final List<Directory> childDirectories = new ArrayList<>(directories.stream()
          .map(Directory::flatten)
          .flatMap(List::stream)
          .toList());
      childDirectories.add(this);

      return childDirectories;
    }
  }
  private static class TerminalOutput {

    boolean isCommand = false;
    String name;
    Integer size = 0;

    TerminalOutput(String input) {
      final String[] s = input.split(" ");
      if(s[0].startsWith("$")) {
        this.isCommand = true;
        this.name = s[2];
      } else {
        this.size = Integer.parseInt(s[0]);
        this.name = s[1];
      }
    }
  }
}
