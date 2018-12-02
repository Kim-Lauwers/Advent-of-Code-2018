package be.aoc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;

public class Day01 {

    public static void main(String[] args) throws Exception {
        final Path path = Paths.get(Day01.class.getClassLoader().getResource("input.txt").toURI());
        final Stream<String> lines = lines(path);
        final int sum = lines
                .mapToInt(Integer::parseInt)
                .sum();

        lines.close();

        System.out.println(sum);
    }
}