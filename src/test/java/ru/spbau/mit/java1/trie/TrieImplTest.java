package ru.spbau.mit.java1.trie;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrieImplTest {

    private static Trie instance() {
        try {
            return (TrieImpl) Class.forName("ru.spbau.mit.java1.trie.TrieImpl").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Error while class loading");
    }

    @Test
    public void testAlphabetAddRemove() {
        Trie t = instance();

        int counter = 0;
        try {
            for (int i = 0; i < 26; i++) {
                String small = Character.toString((char) ('a' + i));
                t.add(small);
                counter++;
                assertTrue(t.contains(small));
                assertEquals(counter, t.size());

                String big = Character.toString((char) ('A' + i));
                t.add(big);
                counter++;
                assertTrue(t.contains(big));
                assertEquals(counter, t.size());
            }

            for (int i = 0; i < 26; i++) {
                String small = Character.toString((char) ('a' + i));
                t.remove(small);
                counter--;
                assertFalse(t.contains(small));
                assertEquals(counter, t.size());

                String big = Character.toString((char) ('A' + i));
                t.remove(big);
                counter--;
                assertFalse(t.contains(big));
                assertEquals(counter, t.size());
            }
        } catch (IllegalArgumentException e) {
            fail();
        }
    }

    @Test
    public void testAddContainsSizeRemove() {
        // add - contains - size - remove - size - contains
        Trie t = instance();
        String s = "abc";

        try {
            assertFalse(t.contains(s));
            assertEquals(0, t.size());
            assertFalse(t.remove(s));
            assertEquals(0, t.size());

            assertTrue(t.add(s));
            assertTrue(t.contains(s));
            assertEquals(1, t.size());

            assertFalse(t.add(s));
            assertTrue(t.contains(s));

            assertTrue(t.remove(s));
            assertEquals(0, t.size());
            assertFalse(t.contains(s));
        } catch (IllegalArgumentException e) {
            fail();
        }
    }

    @Test
    public void testPrefix1() {
        Trie t = instance();
        String s = "abc";

        try {
            assertTrue(t.add(s));
            assertEquals(1, t.howManyStartsWithPrefix("ab"));
            assertEquals(1, t.howManyStartsWithPrefix("a"));
            assertEquals(1, t.howManyStartsWithPrefix("abc"));

            assertFalse(t.add(s));
            assertTrue(t.contains(s));
            assertEquals(1, t.howManyStartsWithPrefix("ab"));
            assertEquals(1, t.howManyStartsWithPrefix("a"));

            assertTrue(t.remove(s));
            assertFalse(t.contains(s));
            assertEquals(0, t.size());
            assertEquals(0, t.howManyStartsWithPrefix("ab"));
            assertEquals(0, t.howManyStartsWithPrefix("a"));
        } catch (IllegalArgumentException e) {
            fail();
        }
    }

    @Test
    public void tesPrefix2() {
        // add - contains - prefix - add - prefix - remove - size - prefix
        Trie t = instance();
        String s1 = "aBcde";
        String s2 = "aBcd";
        String s3 = "aBcdf";
        try {
            assertTrue(t.add(s1));
            assertTrue(t.contains(s1));
            assertEquals(1, t.size());
            assertEquals(1, t.howManyStartsWithPrefix("aBcd"));
            assertEquals(0, t.howManyStartsWithPrefix("abcd"));
            assertEquals(1, t.howManyStartsWithPrefix("a"));

            assertTrue(t.add(s2));
            assertTrue(t.contains(s2));
            assertEquals(2, t.size());
            assertEquals(2, t.howManyStartsWithPrefix("a"));
            assertEquals(2, t.howManyStartsWithPrefix("aBcd"));
            assertEquals(0, t.howManyStartsWithPrefix("aBcdp"));

            assertTrue(t.remove(s1));
            assertFalse(t.contains(s1));
            assertTrue(t.contains(s2));
            assertEquals(1, t.size());

            assertEquals(1, t.howManyStartsWithPrefix("aB"));
            assertEquals(1, t.howManyStartsWithPrefix("aBcd"));
            assertEquals(0, t.howManyStartsWithPrefix("aBcdp"));

            assertTrue(t.add(s3));
            assertTrue(t.contains(s3));
            assertTrue(t.contains(s2));
            assertFalse(t.contains(s1));
            assertEquals(2, t.size());

            assertEquals(2, t.howManyStartsWithPrefix("aB"));
            assertEquals(2, t.howManyStartsWithPrefix("aBcd"));
            assertEquals(1, t.howManyStartsWithPrefix("aBcdf"));
            assertEquals(0, t.howManyStartsWithPrefix("aBcdp"));

            assertTrue(t.remove(s2));
            assertFalse(t.contains(s2));
            assertFalse(t.contains(s1));
            assertTrue(t.contains(s3));
            assertEquals(1, t.size());
            assertEquals(1, t.howManyStartsWithPrefix("aB"));
            assertEquals(1, t.howManyStartsWithPrefix("aBcdf"));
            assertEquals(0, t.howManyStartsWithPrefix("aBcdgp"));

            assertTrue(t.remove(s3));
            assertFalse(t.contains(s2));
            assertFalse(t.contains(s1));
            assertFalse(t.contains(s3));
            assertEquals(0, t.size());
            assertEquals(0, t.howManyStartsWithPrefix("aB"));
            assertEquals(0, t.howManyStartsWithPrefix("aBcdf"));
            assertEquals(0, t.howManyStartsWithPrefix("aBcdgp"));
        } catch (IllegalArgumentException e) {
            fail();
        }
    }

    @Test
    public void testPrefix3() {
        Trie t = instance();
        String s1 = "fizz";
        String s2 = "buzz";
        String s3 = "bring";
        String s4 = "buzo";
        String s5 = "fizzzika";

        try {
            assertTrue(t.add(s1));
            assertTrue(t.add(s2));
            assertTrue(t.add(s4));
            assertTrue(t.add(s5));

            assertFalse(t.contains(s3));
            assertTrue(t.add(s3));
            assertEquals(5, t.size());

            assertEquals(5, t.howManyStartsWithPrefix(""));
            assertEquals(2, t.howManyStartsWithPrefix("fi"));
            assertEquals(2, t.howManyStartsWithPrefix("fizz"));
            assertEquals(1, t.howManyStartsWithPrefix("buzz"));
            assertEquals(2, t.howManyStartsWithPrefix("buz"));
            assertEquals(2, t.howManyStartsWithPrefix("bu"));
            assertEquals(3, t.howManyStartsWithPrefix("b"));

            assertTrue(t.remove(s2));
            assertFalse(t.contains(s2));
            assertEquals(4, t.size());
            assertEquals(4, t.howManyStartsWithPrefix(""));
            assertEquals(0, t.howManyStartsWithPrefix("buzz"));
            assertEquals(1, t.howManyStartsWithPrefix("buz"));
            assertEquals(1, t.howManyStartsWithPrefix("bu"));
            assertEquals(2, t.howManyStartsWithPrefix("b"));

            assertTrue(t.add(s2));
            assertTrue(t.contains(s2));
            assertEquals(5, t.size());
            assertEquals(5, t.howManyStartsWithPrefix(""));
            assertEquals(1, t.howManyStartsWithPrefix("buzz"));
            assertEquals(2, t.howManyStartsWithPrefix("buz"));
            assertEquals(2, t.howManyStartsWithPrefix("bu"));
            assertEquals(3, t.howManyStartsWithPrefix("b"));
        } catch (IllegalArgumentException e) {
            fail();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadSymbol() throws IllegalArgumentException {
        Trie t = instance();
        String s1 = "time7:40";

        t.add(s1);
    }
}