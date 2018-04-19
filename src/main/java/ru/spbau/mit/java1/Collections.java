package ru.spbau.mit.java1;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Collections {
    static <T, R> List<R> map(@NotNull Function1<? super T, R> f, @NotNull Iterable<T> iterable) {
        ArrayList<R> res = new ArrayList<>();
        for (T elem : iterable) {
            res.add(f.apply(elem));
        }
        return res;
    }

    static <T> List<T> filter(@NotNull Function1<? super T, Boolean> f, @NotNull Iterable<T> iterable) {
        ArrayList<T> res = new ArrayList<>();
        for (T elem : iterable) {
            if (f.apply(elem)) {
                res.add(elem);
            }
        }
        return res;
    }

    static <T> List<T> takeWhile(@NotNull Function1<? super T, Boolean> f, @NotNull Iterable<T> iterable) {
        ArrayList<T> res = new ArrayList<>();
        for (T elem : iterable) {
            if (f.apply(elem)) {
                res.add(elem);
            } else {
                return res;
            }
        }
        return res;
    }

    static <T> List<T> takeUnless(@NotNull Function1<? super T, Boolean> f, @NotNull Iterable<T> iterable) {
        ArrayList<T> res = new ArrayList<>();
        for (T elem : iterable) {
            if (!f.apply(elem)) { // can't use not(), it ma be not predicate
                res.add(elem);
            } else {
                return res;
            }
        }
        return res;
    }

    // f(x, f(y, f(z, ini) ) )
    // (a -> b -> b) -> b -> t a -> b
    static <A, B> B foldr(@NotNull Function2<? super A, B, B> f, B ini, @NotNull Iterable<A> iterable) {
        ArrayList<A> original = new ArrayList<>();
        for (A elem : iterable) {
            original.add(elem);
        }

        ListIterator<A> listIterator = original.listIterator(original.size());

        B res = ini;
        while (listIterator.hasPrevious()) {
            res = f.apply(listIterator.previous(), res);
        }

        return res;
    }

    // f( f( f (ini, x), y), z)
    // (b -> a -> b) -> b -> t a -> b
    static <A, B> B foldl(@NotNull Function2<B, ? super A, B> f, B ini, @NotNull Iterable<A> iterable) {
        B res = ini;
        for (A x : iterable) {
            res = f.apply(res, x);
        }

        return res;
    }

}
