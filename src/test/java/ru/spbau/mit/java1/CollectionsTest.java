package ru.spbau.mit.java1;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CollectionsTest {
    private Predicate<Integer> ge42, le42;
    private Function1<Integer, Double> mul2;
    private Function2<String, String, String> concat;
    private ArrayList<Integer> cats;
    private ArrayList<String> strings;


    @Before
    public void setUp() {
        ge42 = new Predicate<Integer>() {
            @Override
            Boolean apply(Integer arg1) {
                return arg1 >= 42;
            }
        };
        le42 = new Predicate<Integer>() {
            @Override
            Boolean apply(Integer arg1) {
                return arg1 <= 42;
            }
        };
        mul2 = new Function1<Integer, Double>() {
            @Override
            Double apply(Integer arg1) {
                return arg1 * 2.0;
            }
        };
        concat = new Function2<String, String, String>() {
            @Override
            String apply(String arg1, String arg2) {
                return arg1 + arg2;
            }
        };

        cats = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            cats.add(i * 10);
        }
        for (int i = 9; i >= 0; i--) {
            cats.add(i * 10);
        }

        strings = new ArrayList<>();
        strings.add("Hello");
        strings.add("Cat");
        strings.add("Dog");
    }

    @Test
    public void map() {
        List<Double> res = Collections.map(mul2, cats);
        int i = 0;
        for (Double x : res) {
            assertEquals(Double.valueOf(2.0 * cats.get(i)), x);
            i++;
        }
        assertTrue(true);
    }

    @Test
    public void mapPred() {
        List<Boolean> res = Collections.map(ge42, cats);
        int i = 0;
        for (Boolean x : res) {
            assertEquals(cats.get(i) >= 42, x);
            i++;
        }
        assertTrue(true);
    }

    @Test
    public void filter() {
        List<Integer> res = Collections.filter(ge42, cats);
        for (Integer x : res) {
            assertTrue(x >= 42);
        }
    }

    @Test
    public void takeWhile() {
        List<Integer> res = Collections.takeWhile(le42, cats);
        int i = 0;
        for (Integer x : res) {
            assertTrue(x <= 42);
        }
        assertEquals(5, res.size()); // [0,10,20,30,40], first part
    }

    @Test
    public void takeUnless() {
        List<Integer> res = Collections.takeUnless(ge42, cats);
        int i = 0;
        for (Integer x : res) {
            assertTrue(x <= 42);
        }
        assertEquals(5, res.size());
    }

    @Test
    public void foldr() {
        String res = Collections.foldr(concat, "init: ", strings);
        assertEquals("HelloCatDoginit: ", res);
    }

    @Test
    public void foldl() {
        String res = Collections.foldl(concat, "init: ", strings);
        assertEquals("init: HelloCatDog", res);
    }
}