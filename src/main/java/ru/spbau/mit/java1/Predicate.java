package ru.spbau.mit.java1;

import org.jetbrains.annotations.NotNull;

public abstract class Predicate<T> extends Function1<T, Boolean> {
    public static final Predicate<Object> ALWAYS_TRUE = new Predicate<Object>() {
        @Override
        Boolean apply(Object arg1) {
            return true;
        }
    };

    public static final Predicate<Object> ALWAYS_FALSE = new Predicate<Object>() {
        @Override
        Boolean apply(Object arg1) {
            return false;
        }
    };

    @Override
    abstract Boolean apply(T arg1);

    Predicate<T> or(@NotNull Predicate<? super T> p) {
        return new Predicate<T>() {
            @Override
            Boolean apply(T arg1) {
                return Predicate.this.apply(arg1) || p.apply(arg1);
            }
        };
    }

    Predicate<T> and(@NotNull Predicate<? super T> p) {
        return new Predicate<T>() {
            @Override
            Boolean apply(T arg1) {
                return Predicate.this.apply(arg1) && p.apply(arg1);
            }
        };
    }

    Predicate<T> not() {
        return new Predicate<T>() {
            @Override
            Boolean apply(T arg1) {
                return !Predicate.this.apply(arg1);
            }
        };
    }


}
