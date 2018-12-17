package be.aoc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.Files.lines;
import static java.util.stream.Collectors.toList;

public class Day11 {
    final static int SERIAL = 7403;
    final static int GRID_SIZE = 300;
    static int[][] grid = new int[GRID_SIZE][GRID_SIZE];
    static int xMax;
    static int yMax;

    static int maxSubMatrix(int array[][], int k) {
        int tmp[][] = new int[GRID_SIZE - k + 1][GRID_SIZE];
        int sum;
        int maxSum = Integer.MIN_VALUE;
        for (int j = 0; j < GRID_SIZE; j++) {
            sum = 0;
            for (int i = 0; i < k; i++) {
                sum += array[i][j];
            }
            tmp[0][j] = sum;
            for (int i = 1; i < GRID_SIZE - k + 1; i++) {
                sum = sum - array[i - 1][j] + array[i + k - 1][j];
                tmp[i][j] = sum;
            }
        }
        int tSum = 0;
        int iIndex = -1;
        int jIndex = -1;
        for (int i = 0; i < tmp.length; i++) {
            tSum = 0;
            int j;
            for (j = 0; j < k; j++) {
                tSum += tmp[i][j];
            }
            if (tSum > maxSum) {
                maxSum = tSum;
                iIndex = i;
                jIndex = j;
            }

            sum = tSum;
            for (j = 1; j < GRID_SIZE - k + 1; j++) {
                sum = sum - tmp[i][j - 1] + tmp[i][j + k - 1];
                if (sum > maxSum) {
                    maxSum = sum;
                    iIndex = i;
                    jIndex = j;
                }
            }
        }
        xMax = jIndex + 1;
        yMax = iIndex + 1;

        return maxSum;
    }

    static int power(int x, int y) {
        return ((((((x + 10) * y) + SERIAL) * (x + 10)) / 100) % 10) - 5;
    }

    public static void main(String[] args) throws Exception {
        final Path path = Paths.get(Day11.class.getClassLoader().getResource("input.txt").toURI());
        final List<String> lines = lines(path)
                .map(String::new)
                .collect(toList());

        System.out.println(part1());
        System.out.println(part2());
    }

    public static Object part1() {
        for (int i = 1; i <= GRID_SIZE; i++) {
            for (int j = 1; j < GRID_SIZE; j++) {
                grid[j - 1][i - 1] = power(i, j);
            }
        }
        maxSubMatrix(grid, 3);
        return xMax + "," + yMax;
    }

    public static Object part2() {
        int max = Integer.MIN_VALUE;
        int maxIndex = 0;
        int maxX = 0;
        int maxY = 0;
        for (int i = 1; i <= GRID_SIZE; i++) {
            int p = maxSubMatrix(grid, i);
            if (p > max) {
                max = p;
                maxIndex = i;
                maxX = xMax;
                maxY = yMax;
            }
        }
        return maxX + "," + maxY + "," + maxIndex;
    }
}