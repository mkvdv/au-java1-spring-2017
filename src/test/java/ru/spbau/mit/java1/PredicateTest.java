package ru.spbau.mit.java1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PredicateTest {
    private Predicate<Integer> p, p2, p3;

    @Before
    public void setUp() {
        p = new Predicate<Integer>() {
            @Override
            Boolean apply(Integer arg1) {
                return arg1 >= 42;
            }
        };

        p2 = new Predicate<Integer>() {
            @Override
            Boolean apply(Integer arg1) {
                return arg1 < 20;
            }
        };

        p3 = new Predicate<Integer>() {
            @Override
            Boolean apply(Integer arg1) {
                return arg1 < 100;
            }
        };
    }

    @Test
    public void apply() {
        assertTrue(p.apply(42));
        assertFalse(p.apply(41));
    }

    @Test
    public void or() {
        assertTrue(p.or(p2).apply(45));
        assertTrue(p.or(p2).apply(15));
        assertFalse(p.or(p2).apply(30));
    }

    @Test
    public void and() {
        assertTrue(p.and(p3).apply(45));
        assertFalse(p.and(p3).apply(30));
    }

    @Test
    public void not() {
        assertFalse(p.not().apply(45));
        assertTrue(p3.not().apply(100));
    }

    @Test
    public void always() {
        assertFalse(Predicate.ALWAYS_FALSE.apply(null));
        assertTrue(Predicate.ALWAYS_TRUE.apply(null));
    }
}