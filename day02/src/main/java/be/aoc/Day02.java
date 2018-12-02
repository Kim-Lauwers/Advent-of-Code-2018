package be.aoc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;
import static java.util.stream.Collectors.toList;

public class Day02 {

    public static void main(String[] args) throws Exception {
        final Path path = Paths.get(Day02.class.getClassLoader().getResource("input.txt").toURI());
        final Stream<String> lines = lines(path);

        int twice = 0;
        int three = 0;

        for (final String device : lines.collect(toList())) {
            int twiceInWord = 0;
            int threeInWord = 0;

            for (final char c : device.chars()
                    .mapToObj(c -> (char) c)
                    .distinct()
                    .collect(toList())) {
                long count = device.chars().filter(ch -> ch == c).count();

                if (count == 2) {
                    twiceInWord++;
                }
                if (count == 3) {
                    threeInWord++;
                }

            }

            if(twiceInWord>0){twice++;}
            if(threeInWord>0){three++;}
        }

        System.out.println(twice * three);


    }
}