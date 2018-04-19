package ru.spbau.mit.java1;

import org.jetbrains.annotations.NotNull;

public abstract class Function2<T, U, R> {
    abstract R apply(T arg1, U arg2);

    /**
     * this :: T -> U -> R
     * (T -> U -> R) -> (R-> S) -> (T -> U -> S)
     *
     * @param g (R -> S)
     * @return (T - > U - > S)
     */
    <S> Function2<T, U, S> compose(@NotNull final Function1<? super R, S> g) {
        return new Function2<T, U, S>() {
            @Override
            S apply(T arg1, U arg2) {
                return g.apply(Function2.this.apply(arg1, arg2)); // oh, wait... what?
            }
        };
    }

    Function1<U, R> bind1(T arg1) {
        return new Function1<U, R>() {
            @Override
            R apply(U arg2) {
                return Function2.this.apply(arg1, arg2);
            }
        };
    }

    Function1<T, R> bind2(U arg2) {
        return new Function1<T, R>() {
            @Override
            R apply(T arg1) {
                return Function2.this.apply(arg1, arg2);
            }
        };
    }


    Function1<T, Function1<U, R>> curry() {
        return new Function1<T, Function1<U, R>>() {
            @Override
            Function1<U, R> apply(T arg1) {
                return new Function1<U, R>() {
                    @Override
                    R apply(U arg2) {
                        return Function2.this.apply(arg1, arg2);
                    }
                };
            }
        };
    }

}