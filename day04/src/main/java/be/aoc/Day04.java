package be.aoc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.lines;
import static java.util.Arrays.fill;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class Day04 {
    public static void main(String[] args) throws Exception {
        Map<Integer, int[]> data = new HashMap<>();
        final Path path = Paths.get(Day04.class.getClassLoader().getResource("input.txt").toURI());

        final List<String> lines = lines(path)
                .map(String::new)
                .sorted()
                .collect(toList());

        int current = 0;
        int start = 0;
        for (final String inputLine : lines) {
            int min = Integer.parseInt(inputLine.substring(15, 17));

            int id;
            if (isShift(inputLine)) {
                id = parseGuardId(inputLine);
                current = id;
                if (!data.containsKey(id)) {
                    data.put(current, new int[60]);
                    fill(data.get(current), 0);
                }
            }
            if (isASleep(inputLine)) {
                start = min;
            }
            if (isAwake(inputLine)) {
                for (int i = start; i < min; i++) {
                    data.get(current)[i] += 1;
                }
            }
        }

        final List<Integer> keys = new ArrayList(data.keySet());
        int best = keys.get(0);
        int bestsum = 0;
        for (final int id : keys) {
            int sum = stream(data.get(id)).filter(x -> x >= 1).sum();
            if (sum > bestsum) {
                bestsum = sum;
                best = id;
            }
        }
        int bestMinute = 0;
        int bestTime = 0;
        for (int i = 0; i < 60; i++) {
            if (data.get(best)[i] > bestMinute) {
                bestMinute = data.get(best)[i];
                bestTime = i;
            }
        }

        System.out.println("Guard id: " + best);
        System.out.println("Minute: " + bestTime);
        System.out.println("Solution to part 1: " + best * bestTime);

        best = keys.get(0);
        bestMinute = 0;
        for (final int id : keys) {
            int minute = stream(data.get(id)).max().orElse(-1);
            if (minute > bestMinute) {
                bestMinute = minute;
                best = id;
            }
        }

        bestTime = 0;
        for (int i = 0; i < 60; i++) {
            if (data.get(best)[i] == bestMinute) {
                bestTime = i;
            }
        }

        System.out.println("Guard id: " + best);
        System.out.println("Minute: " + bestTime);
        System.out.println("Solution to part 2: " + best * bestTime);
    }

    private static int parseGuardId(final String inputLine) {
        return Integer.parseInt(inputLine.split(" ")[3].substring(1));
    }

    private static boolean isASleep(final String inputLine) {
        return inputLine.contains("sleep");
    }

    private static boolean isAwake(final String inputLine) {
        return inputLine.contains("wake");
    }

    private static boolean isShift(final String inputLine) {
        return inputLine.contains("shift");
    }
}