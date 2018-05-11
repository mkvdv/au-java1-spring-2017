package ru.spbau.mit.streams;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class FirstPartTasks {


    private FirstPartTasks() {
    }

    // Список названий альбомов
    public static List<String> allNames(@NotNull Stream<Album> albums) {
        return albums.map(Album::getName).collect(Collectors.toList());
    }

    // Список названий альбомов, отсортированный лексикографически по названию
    public static List<String> allNamesSorted(@NotNull Stream<Album> albums) {
        return albums.map(Album::getName).sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.toList());
    }

    // Список треков, отсортированный лексикографически по названию, включающий все треки альбомов из 'albums'
    public static List<String> allTracksSorted(@NotNull Stream<Album> albums) {
        return albums
                .flatMap(al -> al.getTracks().stream())
                .map(Track::getName)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());
    }

    // Список альбомов, в которых есть хотя бы один трек с рейтингом более 95, отсортированный по названию
    public static List<Album> sortedFavorites(@NotNull Stream<Album> s) {
        return s.filter(alb -> alb.getTracks().stream().anyMatch(track -> track.getRating() > 95))
                .sorted(Comparator.comparing(Album::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    // Сгруппировать альбомы по артистам
    public static Map<Artist, List<Album>> groupByArtist(@NotNull Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(Album::getArtist));
    }

    // Сгруппировать альбомы по артистам (в качестве значения вместо объекта 'Artist' использовать его имя)
    public static Map<Artist, List<String>> groupByArtistMapName(@NotNull Stream<Album> albums) {
        return albums.collect(
                Collectors.groupingBy(Album::getArtist,
                        Collectors.mapping(Album::getName, Collectors.toList())));
    }

    // Число повторяющихся альбомов в потоке
    public static long countAlbumDuplicates(@NotNull Stream<Album> albums) {
        return albums
                .collect(Collectors.groupingBy(
                        Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .count();
    }

    // Альбом, в котором максимум рейтинга минимален
    // (если в альбоме нет ни одного трека, считать, что максимум рейтинга в нем --- 0)
    public static Optional<Album> minMaxRating(@NotNull Stream<Album> albums) {
        return albums.collect(Collectors.toMap(Function.identity(),
                al -> al.getTracks().stream()
                        .map(Track::getRating)
                        .max(Comparator.comparingInt(Integer::intValue)).orElse(0)
        )).entrySet().stream().min(Comparator.comparing(Map.Entry::getValue)).map(Map.Entry::getKey);
    }

    // Список альбомов, отсортированный по убыванию среднего рейтинга его треков (0, если треков нет)
    public static List<Album> sortByAverageRating(@NotNull Stream<Album> albums) {
        return albums.collect(Collectors.toMap(Function.identity(),
                al -> al.getTracks().stream()
                        .mapToDouble(Track::getRating)
                        .reduce(0, new DoubleBinaryOperator() {
                            private double acc = 0;
                            private int count = 0;

                            @Override
                            public double applyAsDouble(double ini_ignored, double x) {
                                acc += x;
                                count += 1;
                                return acc / count;
                            }
                        })
        )).entrySet().stream() // <Album, mean_rating>
                .sorted((o1, o2) -> Double.compare(o2.getValue(), o1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // Произведение всех чисел потока по модулю 'modulo'
    // (все числа от 0 до 10000)
    public static int moduloProduction(@NotNull IntStream stream, int modulo) {
        return stream.reduce(1, (ini, x) -> (ini * x) % modulo);
    }

    // Вернуть строку, состояющую из конкатенаций переданного массива, и окруженную строками "<", ">"
    // см. тесты
    public static String joinTo(String... strings) {
        return Arrays.stream(strings)
                .collect(Collectors.joining(", ", "<", ">"));
    }

    // Вернуть поток из объектов класса 'clazz'
    public static <R> Stream<R> filterIsInstance(@NotNull Stream<?> s, @NotNull Class<R> clazz) {
        return (Stream<R>) s.filter(clazz::isInstance);
    }


}