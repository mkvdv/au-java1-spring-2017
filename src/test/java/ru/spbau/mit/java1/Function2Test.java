package ru.spbau.mit.java1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Function2Test {
    private Function2<Integer, Boolean, Double> f1;

    @Before
    public void setUp() {
        f1 = new Function2<Integer, Boolean, Double>() {
            @Override
            Double apply(Integer arg1, Boolean arg2) {
                if (arg2) {
                    return arg1 * -1.0;
                }
                return Double.valueOf(arg1);
            }
        };
    }

    @Test
    public void apply() {
        assertEquals(Double.valueOf(-5.0), f1.apply(5, true));
    }

    @Test
    public void compose() {
        Function1<Double, String> f2 = new Function1<Double, String>() {
            @Override
            String apply(Double arg1) {
                return arg1.toString();
            }
        };

        assertEquals("-5.0", f1.compose(f2).apply(5, true));
    }

    @Test
    public void bind1() {
        assertEquals(Double.valueOf(-5.0), f1.bind1(5).apply(true));
    }

    @Test
    public void bind2() {
        assertEquals(Double.valueOf(-5.0), f1.bind2(true).apply(5));
    }

    @Test
    public void curry() {
        assertEquals(Double.valueOf(-5.0), f1.curry().apply(5).apply(true));
    }
}