package ru.spbau.mit.java1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Function1Test {
    private Function1<Integer, Double> f1;

    @Before
    public void setUp() {
        f1 = new Function1<Integer, Double>() {
            @Override
            Double apply(Integer arg1) {
                return arg1 * 2.0;
            }
        };
    }

    @Test
    public void testApply() {
        assertEquals((Double) 10.0, f1.apply(5));
    }

    @Test
    public void testCompose() {
        Function1<Double, String> f2 = new Function1<Double, String>() {
            @Override
            String apply(Double arg1) {
                return arg1.toString();
            }
        };
        assertEquals("10.0", f1.compose(f2).apply(5));
    }
}