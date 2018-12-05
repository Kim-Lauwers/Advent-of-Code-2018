package be.aoc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.lang.Integer.MAX_VALUE;
import static java.nio.file.Files.lines;
import static java.util.stream.Collectors.toList;

public class Day05 {

    public static void main(String[] args) throws Exception {
        final Path path = Paths.get(Day05.class.getClassLoader().getResource("input.txt").toURI());

        final List<String> lines = lines(path)
                .map(String::new)
                .collect(toList());

        System.out.println((part1(lines.get(0))));
        System.out.println((part2(lines.get(0))));
    }

    private static int remove(final StringBuilder input) {
        boolean removed = true;

        while (removed) {
            for (int i = 0; i < input.length() - 1; i++) {
                //You can test for lowercase/uppercase being the same letter using XOR 32.
                if ((input.charAt(i) ^ input.charAt(i + 1)) == 32) {
                    input.delete(i, i + 2);
                    removed = true;
                    break;
                }
                removed = false;
            }
        }
        return input.length();
    }

    static int part1(final String input) {
        return remove(new StringBuilder(input));
    }

    static int part2(final String input) {
        int min = MAX_VALUE;

        final String[] patterns = new String[26];
        for (int i = 0; i < 26; i++) {
            patterns[i] = "[" + (Character.toString((char) (i + 'a'))) +
                    (Character.toString((char) (i + 'A'))) + "]";
        }

        for (int i = 0; i < 26; i++) {
            String chain = input;
            chain = chain.replaceAll(patterns[i], "");
            int result = remove(new StringBuilder(chain));
            if (result < min) {
                min = result;
            }
        }
        return min;
    }
}
