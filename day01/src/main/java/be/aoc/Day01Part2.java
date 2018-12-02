package be.aoc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;
import static java.util.stream.Collectors.toList;

public class Day01Part2 {

    public static void main(String[] args) throws Exception {
        final Path path = Paths.get(Day01Part2.class.getClassLoader().getResource("input.txt").toURI());
        final Stream<String> lines = lines(path);
        final List<String> inputList = lines
                .collect(toList());
        lines.close();


        int sum = 0;
        boolean found = false;
        final List<Integer> history = new ArrayList<>();

        while (!found) {
            for (final String input : inputList) {
                sum += Integer.parseInt(input);
                if (history.contains(sum)) {
                    found = true;
                    System.out.println("FOUND " + sum);
                    break;
                }
                history.add(sum);
            }
        }
    }
}