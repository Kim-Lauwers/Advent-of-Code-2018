package be.aoc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;
import static java.util.stream.Collectors.toList;

public class Day02Part2 {

    public static void main(String[] args) throws Exception {
        final Path path = Paths.get(Day02Part2.class.getClassLoader().getResource("input.txt").toURI());
        final List<String> lines = lines(path).collect(toList());
        final List<String> lines2 = lines(path).collect(toList());

        for (String device : lines) {
            for (String device2 : lines2) {


                int diffCount = (int) IntStream.range(0, device.length())
                        .filter(i -> device.charAt(i) != device2.charAt(i)) // corresponding characters from both the strings
                        .count()
                        ;

                if(diffCount==1){
                System.out.println(diffCount);
                System.out.println(device);
                System.out.println(device2);
            }}
        }

    }
}