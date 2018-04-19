package ru.spbau.mit.java1;

import org.jetbrains.annotations.NotNull;

public abstract class Function1<T, R> {
    abstract R apply(T arg1);

    /**
     * this :: T -> R
     * (R -> S) -> (T -> S)
     *
     * @param g (R -> S)
     * @return (T - > S)
     */
    <S> Function1<T, S> compose(@NotNull final Function1<? super R, S> g) {
        return new Function1<T, S>() {
            @Override
            S apply(T arg1) {
                return g.apply(Function1.this.apply(arg1)); // oh, wait... what?
            }
        };
    }
}
