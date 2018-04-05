package ru.spbau.mit.java1.dict;

import org.junit.Test;

import static org.junit.Assert.*;

public class DictionaryImplTest {

    private static Dictionary instance() {
        return new DictionaryImpl(10);
    }

    @Test
    public void testPutGetContainsSize() {
        Dictionary d = instance();
        final int size = 100;

        for (int i = 0; i < size; i++) {
            assertEquals(null, d.put(Integer.toString(i), Integer.toString(1000 + i)));
        }

        for (int i = 0; i < size; i++) {
            assertNotEquals(null, d.put(Integer.toString(i), Integer.toString(1000 + i)));
        }

        for (int i = 0; i < size; i++) {
            assertEquals(Integer.toString(1000 + i), d.get(Integer.toString(i)));
        }

        for (int i = size; i < 2 * size; i++) {
            assertEquals(null, d.get(Integer.toString(i)));
        }

        assertEquals(d.size(), size);

        for (int i = 0; i < size; i++) {
            assertTrue(d.contains(Integer.toString(i)));
        }
    }

    @Test
    public void testRemoveClear() {
        Dictionary d = instance();
        final int size = 100;

        for (int i = 0; i < size; i++) {
            assertEquals(null, d.remove(Integer.toString(i)));
        }

        for (int i = 0; i < size; i++) {
            assertEquals(null, d.put(Integer.toString(i), Integer.toString(1000 + i)));
        }
        assertEquals(d.size(), size);

        for (int i = size; i < 2 * size; ++i) {
            assertEquals(null, d.remove(Integer.toString(i)));
        }
        assertEquals(d.size(), size);

        for (int i = size / 20; i < size; ++i) {
            assertNotEquals(null, d.remove(Integer.toString(i)));
        }
        assertEquals(size / 20, d.size());

        d.clear();
        assertEquals(0, d.size());
        for (int i = 0; i < size; i++) {
            assertEquals(null, d.get(Integer.toString(i)));
            assertFalse(d.contains(Integer.toString(i)));
        }
    }

    @Test
    public void testAddRemove() {
        Dictionary d = instance();
        final int size = 100;

        d.put("1", "10");
        assertEquals("10", d.get("1"));
        assertEquals(1, d.size());

        d.remove("1");
        assertEquals(0, d.size());

        d.put("1", "20");
        assertEquals("20", d.get("1"));
        assertEquals(1, d.size());

        d.remove("1");
        assertEquals(0, d.size());
    }
}