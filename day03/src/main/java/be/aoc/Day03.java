package be.aoc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Boolean.TRUE;
import static java.lang.Integer.parseInt;
import static java.nio.file.Files.lines;
import static java.util.stream.Collectors.toList;

public class Day03 {

    public static void main(String[] args) throws Exception {
        final Path path = Paths.get(Day03.class.getClassLoader().getResource("input.txt").toURI());

        final List<String> lines = lines(path)
                .map(String::new)
                .collect(toList());

        final Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        final Map<Integer, Boolean> intact = new HashMap<>();

        lines.forEach(inputString -> {
            final String[] claim = inputString.replace(":", "").split(" ");
            final int id = parseInt(claim[0].replace("#", ""));
            String[] coord = claim[2].split(",");
            String[] size = claim[3].split("x");
            for (int x = 0; x < parseInt(size[0]); x++) {
                for (int y = 0; y < parseInt(size[1]); y++) {
                    int coordX = parseInt(coord[0]) + x;
                    int coordY = parseInt(coord[1]) + y;
                    final Map<Integer, Integer> m = map.computeIfAbsent(coordX, k -> new HashMap<>());
                    final Integer integer = m.get(coordY);
                    if (integer == null) {
                        m.put(coordY, id);
                        intact.putIfAbsent(id, true);
                    } else {
                        m.put(coordY, -1);
                        intact.put(integer, false);
                        intact.put(id, false);
                    }
                }
            }
        });

        //print part 1
        System.out.println(map.values().stream()
                .flatMap(v -> v.values().stream())
                .filter(v -> v == -1)
                .count());

        //print part 2
        intact.entrySet().stream()
                .filter(e -> e.getValue() == TRUE)
                .forEach(System.out::println);

    }
}