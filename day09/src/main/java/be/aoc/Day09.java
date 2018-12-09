package be.aoc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.List;

import static java.nio.file.Files.lines;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class Day09 {
    static int players;
    static int end;

    public static void main(String[] args) throws Exception {
        final Path path = Paths.get(Day09.class.getClassLoader().getResource("input.txt").toURI());
        final List<String> lines = lines(path)
                .map(String::new)
                .collect(toList());

        parse(lines);
        System.out.println("Winning Elf score: " + part1());
        System.out.println("Winning Elf score with end marble * 100: " + part2());
    }

    static class CircleDeque<T> extends ArrayDeque<T> {
        void rotate(final int num) {
            if (num == 0) return;
            if (num > 0) {
                for (int i = 0; i < num; i++) {
                    T t = this.removeLast();
                    this.addFirst(t);
                }
            } else {
                for (int i = 0; i < Math.abs(num) - 1; i++) {
                    T t = this.remove();
                    this.addLast(t);
                }
            }
        }
    }

    static long game(int players, int end) {
        CircleDeque<Integer> circle = new CircleDeque<>();
        circle.addFirst(0);
        long[] scores = new long[players];
        for (int i = 1; i <= end; i++) {
            if (i % 23 == 0) {
                circle.rotate(-7);
                scores[i % players] += i + circle.pop();

            } else {
                circle.rotate(2);
                circle.addLast(i);
            }
        }
        return stream(scores).max().getAsLong();
    }

    private static Long part1() {
        return game(players, end);
    }

    private static Long part2() {
        return game(players, end * 100);
    }


    private static void parse(final List<String> lines) {
        String[] split = lines.get(0).split(" ");
        players = Integer.parseInt(split[0]);
        end = Integer.parseInt(split[6]);
    }
}