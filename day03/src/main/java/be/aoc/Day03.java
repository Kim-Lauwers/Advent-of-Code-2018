package be.aoc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.nio.file.Files.lines;
import static java.util.stream.Collectors.toList;

public class Day03 {

    public static void main(String[] args) throws Exception {
        final Path path = Paths.get(Day03.class.getClassLoader().getResource("input.txt").toURI());
        final List<Claim> claims = lines(path)
                .map(input -> Claim.fromString(input))
                .collect(toList());

        System.out.println(claims);
    }
}

class Claim {
    private final String id;
    private final int leftEdge;
    private final int topEdge;
    private final int wide;
    private final int tall;

    Claim(final String id, final int leftEdge, final int topEdge, final int wide, final int tall) {
        this.id = id;
        this.leftEdge = leftEdge;
        this.topEdge = topEdge;
        this.wide = wide;
        this.tall = tall;
    }

    static Claim fromString(final String input) {
        final String id = input.substring(1, input.indexOf('@') - 1);
        final int leftEdge = parseInt(input.substring(input.indexOf('@') + 1, input.indexOf(",")).trim());
        final int topEdge = parseInt(input.substring(input.indexOf(',') + 1, input.indexOf(":")).trim());
        final int wide = parseInt(input.substring(input.indexOf(':') + 1, input.indexOf("x")).trim());
        final int tall = parseInt(input.substring(input.indexOf('x') + 1).trim());
        return new Claim(id, leftEdge, topEdge, wide, tall);
    }

    @Override
    public String toString() {
        return "Claim{" +
                "id='" + id + '\'' +
                ", leftEdge=" + leftEdge +
                ", topEdge=" + topEdge +
                ", wide=" + wide +
                ", tall=" + tall +
                '}';
    }
}