package be.aoc;

import java.awt.Point;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;
import static java.nio.file.Files.lines;
import static java.util.stream.Collectors.toList;

public class Day06 {

    public static void main(String[] args) throws Exception {
        final Path path = Paths.get(Day06.class.getClassLoader().getResource("input.txt").toURI());

        //* Toggle comment - switch start of this line between /* and //* to toggle which section of code is active.
        final List<String> lines = lines(path)
                .map(String::new)
                .collect(toList());

        /*/ String[] input =("1, 1\n" + "1, 6\n" + "8, 3\n" + "3, 4\n" + "5, 5\n" + "8, 9").split("\n");
		//*/

        HashMap<Integer, Point> points = new HashMap<Integer, Point>();

        int maxx = 0;
        int maxy = 0;
        int count = 0;
        for (final String line : lines) {

            final String s[] = line.trim().split(", ");
            int x = parseInt(s[0]);
            int y = parseInt(s[1]);
            points.put(count, new Point(x, y));
            count++;

            maxx = Math.max(x, maxx);
            maxy = Math.max(y, maxy);

        }

        int[][] grid = new int[maxx + 1][maxy + 1];
        Map<Integer, Integer> regions = new HashMap<>();

        for (int x = 0; x <= maxx; x++) {
            for (int y = 0; y <= maxy; y++) {

                int best = maxx + maxy;
                int bestnum = -1;

                // find distance to closest point
                for (int i = 0; i < count; i++) {
                    final Point p = points.get(i);

                    int dist = abs(x - p.x) + abs(y - p.y);
                    if (dist < best) {
                        best = dist;
                        bestnum = i;
                    } else if (dist == best) {
                        bestnum = -1;
                    }
                }

                grid[x][y] = bestnum;
                Integer total = regions.get(bestnum);
                if (total == null) {
                    total = 1;
                } else {
                    total = total + 1;
                }
                regions.put(bestnum, total);
            }
        }

        // remove infinite
        for (int x = 0; x <= maxx; x++) {
            int bad = grid[x][0];
            regions.remove(bad);
            bad = grid[x][maxy];
            regions.remove(bad);
        }
        for (int y = 0; y <= maxy; y++) {
            int bad = grid[0][y];
            regions.remove(bad);
            bad = grid[maxx][y];
            regions.remove(bad);
        }

        // find biggest
        int biggest = 0;
        for (int size : regions.values()) {
            if (size > biggest) {
                biggest = size;
            }
        }

        System.out.println("Biggest: " + biggest);

        int inarea = 0;

        for (int x = 0; x <= maxx; x++) {
            for (int y = 0; y <= maxy; y++) {

                int size = 0;
                for (int i = 0; i < count; i++) {
                    Point p = points.get(i);
                    int dist = abs(x - p.x) + abs(y - p.y);
                    size += dist;
                }

                if (size < 10000) {
                    inarea++;
                }

            }
        }

        System.out.println("Area Size: " + inarea);
    }
}
