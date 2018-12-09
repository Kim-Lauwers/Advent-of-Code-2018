package be.aoc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.lines;
import static java.util.stream.Collectors.toList;

public class Day08 {
    static List<Integer> data;
    static int total;
    static TreeNode root = new TreeNode();

    public static void main(String[] args) throws Exception {
        final Path path = Paths.get(Day08.class.getClassLoader().getResource("input.txt").toURI());
        final List<String> lines = lines(path)
                .map(String::new)
                .collect(toList());

        parse(lines);
        System.out.println(part1());
        System.out.println(part2());
    }

    private static void parse(final List<String> lines) {
        data = new ArrayList<>();
        String[] split = lines.get(0).split(" ");
        for (final String each : split) {
            data.add(Integer.parseInt(each));
        }
    }

    static class TreeNode {
        final List<TreeNode> children = new ArrayList<>();
        final List<Integer> metaData = new ArrayList<>();

        int value() {
            if (children.size() == 0) {
                return metaData.stream()
                        .mapToInt(x -> x)
                        .sum();
            } else {
                int sum = 0;
                for (final Integer meta : metaData) {
                    if (meta <= children.size()) {
                        final TreeNode child = children.get(meta - 1);
                        if (child != null) {
                            sum += child.value();
                        }
                    }
                }
                return sum;
            }
        }
    }

    private static Object part1() {
        buildTree(0, root);
        return total;
    }

    private static int buildTree(int index, final TreeNode current) {
        int children = data.get(index++);
        int metaData = data.get(index++);
        for (int i = 0; i < children; i++) {
            TreeNode child = new TreeNode();
            current.children.add(child);
            index = buildTree(index, child);
        }
        for (int i = 0; i < metaData; i++) {
            current.metaData.add(data.get(index + i));
            total += data.get(index + i);
        }
        return index + metaData;

    }

    private static Object part2() {
        return root.value();
    }
}