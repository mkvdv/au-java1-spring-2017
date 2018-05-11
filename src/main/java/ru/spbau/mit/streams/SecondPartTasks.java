package ru.spbau.mit.streams;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SecondPartTasks {

    private SecondPartTasks() {
    }

    // Найти строки из переданных файлов, в которых встречается указанная подстрока.
    public static List<String> findQuotes(@NotNull List<String> paths, CharSequence sequence) {
        return paths.stream().flatMap((String p) ->
                {
                    try {
                        return Files.lines(Paths.get(p));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return Stream.empty();
                }
        )
                .filter(l -> l.contains(sequence))
                .collect(Collectors.toList());
    }


    // В квадрат с длиной стороны 1 вписана мишень.
    // Стрелок атакует мишень и каждый раз попадает в произвольную точку квадрата.
    // Надо промоделировать этот процесс с помощью класса java.util.Random и посчитать,
    // какова вероятность попасть в мишень.
    public static double piDividedBy4() {
        final int NUMBER_OF_SHOOTS = 1_000_000;
        double R = 0.5;
        Random rnd = new Random();
        return rnd.doubles(NUMBER_OF_SHOOTS)
                .boxed()
                .collect(Collectors.toMap(Function.identity(), d -> rnd.nextDouble()))
                .entrySet().stream()
                .map(e -> Math.sqrt(Math.pow((e.getKey() - 0.5), 2) + Math.pow((e.getValue() - 0.5), 2)))
                .filter(p -> p <= R)
                .count() / (double) NUMBER_OF_SHOOTS;

    }

    // Дано отображение из имени автора в список с содержанием его произведений.
    // Надо вычислить, чья общая длина произведений наибольшая.
    public static Optional<String> findPrinter(@NotNull Map<String, List<String>> compositions) {
        return compositions.entrySet().stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue().stream().collect(Collectors.joining())))
                .entrySet().stream()
                .max(Comparator.comparingInt(e -> e.getValue().length()))
                .map(Map.Entry::getKey);
    }

    // Вы крупный поставщик продуктов. Каждая торговая сеть делает вам заказ в виде Map<Товар, Количество>.
    // Необходимо вычислить, какой товар и в каком количестве надо поставить.
    public static Map<String, Integer> calculateGlobalOrder(@NotNull List<Map<String, Integer>> orders) {
        return orders.stream().reduce(null, new BinaryOperator<Map<String, Integer>>() {
            HashMap<String, Integer> store = new HashMap<>();

            @Override
            public Map<String, Integer> apply(Map<String, Integer> ini, Map<String, Integer> x) {
                x.forEach((s, integer) -> store.merge(s, integer,
                        (integer1, integer2) -> integer1 + integer2));
                return store;
            }
        });
    }
}
