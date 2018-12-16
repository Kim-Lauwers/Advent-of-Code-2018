package be.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.lines;
import static java.util.stream.Collectors.toList;

public class Day10 {
    class Point {
        private int x;
        private int y;
        private final int vx;
        private final int vy;

        public Point(String raw) {
            // position=< 52484, -20780> velocity=<-5, 2>
            // position=<-52068, 31483> velocity=< 5, -3>
            String parts[] = raw.split("<");
            String posParts[] = parts[1].substring(0, parts[1].indexOf('>')).split(",");
            x = Integer.parseInt(posParts[0].trim());
            y = Integer.parseInt(posParts[1].trim());
            String velParts[] = parts[2].substring(0, parts[2].length() - 1).split(",");
            vx = Integer.parseInt(velParts[0].trim());
            vy = Integer.parseInt(velParts[1].trim());
        }

        public void tick() {
            x += vx;
            y += vy;
        }

        public void reverseTick() {
            x -= vx;
            y -= vy;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean atCoords(int x, int y) {
            return this.x == x && this.y == y;
        }
    }

    private void main() throws Exception {
        final Path path = Paths.get(Day10.class.getClassLoader().getResource("input.txt").toURI());
        final List<String> input = lines(path)
                .map(String::new)
                .collect(toList());

        if (input == null || input.isEmpty()) {
            return;
        }

        List<Point> points = new ArrayList<>();
        for (String line : input) {
            points.add(new Point(line));
        }

        int minX = Integer.MIN_VALUE, maxX = Integer.MAX_VALUE, minY = Integer.MIN_VALUE, maxY = Integer.MAX_VALUE;
        int xDiff = Integer.MAX_VALUE, yDiff = Integer.MAX_VALUE;
        int seconds = 0;
        boolean first = true;

        // To have the lights spell out something, points have to be close together. We warp time while both dimensions keep decreasing and stop when we
        // detect an increase on either X or Y coordinate.
        do {
            if (first) {
                first = false;
            } else {
                xDiff = maxX - minX;
                yDiff = maxY - minY;
            }

            minX = Integer.MAX_VALUE;
            maxX = Integer.MIN_VALUE;
            minY = Integer.MAX_VALUE;
            maxY = Integer.MIN_VALUE;

            for (Point point : points) {
                point.tick();

                if (point.getX() < minX) {
                    minX = point.getX();
                }
                if (point.getX() > maxX) {
                    maxX = point.getX();
                }

                if (point.getY() < minY) {
                    minY = point.getY();
                }
                if (point.getY() > maxY) {
                    maxY = point.getY();
                }
            }
            seconds++;
        } while ((maxX - minX) < xDiff && (maxY - minY) < yDiff);

        // Since we detected an increase on either X or Y axis, we're 1 step too far. So we back off 1 step to get the message.
        minX = Integer.MAX_VALUE;
        maxX = Integer.MIN_VALUE;
        minY = Integer.MAX_VALUE;
        maxY = Integer.MIN_VALUE;
        for (Point point : points) {
            if (point.getX() < minX) {
                minX = point.getX();
            }
            if (point.getX() > maxX) {
                maxX = point.getX();
            }

            if (point.getY() < minY) {
                minY = point.getY();
            }
            if (point.getY() > maxY) {
                maxY = point.getY();
            }

            point.reverseTick();
        }
        seconds--;

        // Display the message
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                boolean found = false;
                for (Point point : points) {
                    if (point.atCoords(x, y)) {
                        found = true;
                        break;
                    }
                }

                if (found) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println("");
        }

        // Part 2 -- display the seconds we warped
        System.out.println(String.format("Seconds: %d", seconds));
    }

    public static void main(String[] args) throws Exception {
        new Day10().main();
    }
}