package be.aoc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Character.getNumericValue;
import static java.nio.file.Files.lines;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

public class Day07 {
    public static void main(String[] args) throws Exception {
        final Path path = Paths.get(Day07.class.getClassLoader().getResource("input.txt").toURI());
        final List<String> lines = lines(path)
                .map(String::new)
                .collect(toList());

        Pattern pattern = Pattern.compile("Step (\\w) must be finished before step (\\w) can begin.");
        Map<Character, List<Character>> allSteps = lines.stream()
                .map(line -> {
                    Matcher matcher = pattern.matcher(line);
                    matcher.find();
                    return new AbstractMap.SimpleEntry<>(matcher.group(1).charAt(0), matcher.group(2).charAt(0));
                }).collect(groupingBy(AbstractMap.SimpleEntry::getKey, mapping(AbstractMap.SimpleEntry::getValue, toList())));

        Map<Character, List<Character>> dependencies = allSteps.entrySet().stream()
                .flatMap(e -> e.getValue().stream().map(v -> new AbstractMap.SimpleEntry<>(v, e.getKey())))
                .collect(groupingBy(AbstractMap.SimpleEntry::getKey, mapping(AbstractMap.SimpleEntry::getValue, toList())));

        List<Character> startSteps = allSteps.keySet().stream()
                .filter(key -> !dependencies.containsKey(key))
                .sorted()
                .collect(toList());

        //First
        first(dependencies, allSteps, startSteps); //actually not needed
        second(dependencies, allSteps, startSteps, 1, getNumericValue('A'));
        //Second
        second(dependencies, allSteps, startSteps, 5, 60 - getNumericValue('A'));
    }

    private static void second(Map<Character, List<Character>> dependencies, Map<Character, List<Character>> allSteps,
                               List<Character> startSteps, int amountOfWOrkers, int workSeconds) {
        Set<Character> visited = new LinkedHashSet<>();
        Set<Character> available = new TreeSet<>();
        available.addAll(startSteps);

        List<Map.Entry<AtomicInteger, Character>> workers = IntStream.range(0, amountOfWOrkers)
                .mapToObj(l -> new AbstractMap.SimpleEntry<AtomicInteger, Character>(new AtomicInteger(0), null))
                .collect(toList());

        int seconds = 0;
        while (true) {
            boolean hasMoreWork = false;
            for (int i = 0; i < workers.size(); i++) {
                Map.Entry<AtomicInteger, Character> worker = workers.get(i);
                Character step = worker.getValue();
                if (worker.getKey().get() == 0 && step != null) {
                    visited.add(step);
                    worker.setValue(null);
                    available.addAll(allSteps.getOrDefault(step, emptyList()));
                }
            }

            for (int i = 0; i < workers.size(); i++) {
                Map.Entry<AtomicInteger, Character> worker = workers.get(i);
                AtomicInteger workerSeconds = worker.getKey();
                if (workerSeconds.get() == 0) {
                    getFirstAvailableStep(dependencies, visited, available).ifPresent(nextStep -> {
                        workerSeconds.set(getNumericValue(nextStep) + workSeconds + 1);
                        worker.setValue(nextStep);
                        available.remove(nextStep);
                    });
                }
                if (workerSeconds.get() != 0) {
                    workerSeconds.decrementAndGet();
                    hasMoreWork = true;
                }
            }
            if (!hasMoreWork) {
                break;
            }
            seconds++;
        }
        System.out.println(visited.stream().map(Object::toString).collect(joining()));
        System.out.println(seconds);
    }

    //This is actually not needed, as part 2 solves this with 1 worker
    private static void first(Map<Character, List<Character>> dependencies, Map<Character, List<Character>> allSteps, List<Character> startSteps) {
        Set<Character> visited = new LinkedHashSet<>();
        Set<Character> available = new TreeSet<>();
        Character nextStep = startSteps.get(0);
        available.addAll(startSteps);
        while (!available.isEmpty()) {
            available.remove(nextStep);
            visited.add(nextStep);
            available.addAll(allSteps.getOrDefault(nextStep, emptyList()));
            nextStep = getFirstAvailableStep(dependencies, visited, available).orElse(null);
        }
        System.out.println(visited.stream().map(Object::toString).collect(joining()));
    }

    private static Optional<Character> getFirstAvailableStep(Map<Character, List<Character>> dependencies, Set<Character> visited, Set<Character> available) {
        return available.stream()
                .filter(s -> visited.containsAll(dependencies.getOrDefault(s, emptyList())))
                .findFirst();
    }
}