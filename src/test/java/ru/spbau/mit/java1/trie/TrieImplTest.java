package ru.spbau.mit.java1.trie;

import org.junit.Assert;
import org.junit.Test;

public class TrieImplTest {

    private static TrieImpl instance() {
        try {
            return (TrieImpl) Class.forName("ru.spbau.mit.java1.trie.TrieImpl").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Error while class loading");
    }

    @Test
    public void testAlphabetAddRemove() {
        TrieImpl t = instance();

        int counter = 0;
        for (int i = 0; i < 26; i++) {
            String small = Character.toString((char) ('a' + i));
            t.add(small);
            counter++;
            Assert.assertEquals(true, t.contains(small));
            Assert.assertEquals(counter, t.size());

            String big = Character.toString((char) ('A' + i));
            t.add(big);
            counter++;
            Assert.assertEquals(true, t.contains(big));
            Assert.assertEquals(counter, t.size());
        }

        for (int i = 0; i < 26; i++) {
            String small = Character.toString((char) ('a' + i));
            t.remove(small);
            counter--;
            Assert.assertEquals(false, t.contains(small));
            Assert.assertEquals(counter, t.size());

            String big = Character.toString((char) ('A' + i));
            t.remove(big);
            counter--;
            Assert.assertEquals(false, t.contains(big));
            Assert.assertEquals(counter, t.size());
        }
    }

    @Test
    public void testAddContainsSizeRemove() {
        // add - contains - size - remove - size - contains
        TrieImpl t = instance();
        String s = "abc";

        Assert.assertEquals(false, t.contains(s));
        Assert.assertEquals(0, t.size());
        Assert.assertEquals(false, t.remove(s));
        Assert.assertEquals(0, t.size());

        Assert.assertEquals(true, t.add(s));
        Assert.assertEquals(true, t.contains(s));
        Assert.assertEquals(1, t.size());

        Assert.assertEquals(false, t.add(s));
        Assert.assertEquals(true, t.contains(s));

        Assert.assertEquals(true, t.remove(s));
        Assert.assertEquals(0, t.size());
        Assert.assertEquals(false, t.contains(s));
    }

    @Test
    public void testPrefix1() {
        TrieImpl t = instance();
        String s = "abc";

        Assert.assertEquals(true, t.add(s));
        Assert.assertEquals(1, t.howManyStartsWithPrefix("ab"));
        Assert.assertEquals(1, t.howManyStartsWithPrefix("a"));
        Assert.assertEquals(1, t.howManyStartsWithPrefix("abc"));

        Assert.assertEquals(false, t.add(s));
        Assert.assertEquals(true, t.contains(s));
        Assert.assertEquals(1, t.howManyStartsWithPrefix("ab"));
        Assert.assertEquals(1, t.howManyStartsWithPrefix("a"));

        Assert.assertEquals(true, t.remove(s));
        Assert.assertEquals(false, t.contains(s));
        Assert.assertEquals(0, t.size());
        Assert.assertEquals(0, t.howManyStartsWithPrefix("ab"));
        Assert.assertEquals(0, t.howManyStartsWithPrefix("a"));
    }

    @Test
    public void tesPrefix2() {
        // add - contains - prefix - add - prefix - remove - size - prefix
        TrieImpl t = instance();
        String s1 = "aBcde";
        String s2 = "aBcd";
        String s3 = "aBcdf";

        Assert.assertEquals(true, t.add(s1));
        Assert.assertEquals(true, t.contains(s1));
        Assert.assertEquals(1, t.size());
        Assert.assertEquals(1, t.howManyStartsWithPrefix("aBcd"));
        Assert.assertEquals(0, t.howManyStartsWithPrefix("abcd"));
        Assert.assertEquals(1, t.howManyStartsWithPrefix("a"));

        Assert.assertEquals(true, t.add(s2));
        Assert.assertEquals(true, t.contains(s2));
        Assert.assertEquals(2, t.size());
        Assert.assertEquals(2, t.howManyStartsWithPrefix("a"));
        Assert.assertEquals(2, t.howManyStartsWithPrefix("aBcd"));
        Assert.assertEquals(0, t.howManyStartsWithPrefix("aBcdp"));

        Assert.assertEquals(true, t.remove(s1));
        Assert.assertEquals(false, t.contains(s1));
        Assert.assertEquals(true, t.contains(s2));
        Assert.assertEquals(1, t.size());

        Assert.assertEquals(1, t.howManyStartsWithPrefix("aB"));
        Assert.assertEquals(1, t.howManyStartsWithPrefix("aBcd"));
        Assert.assertEquals(0, t.howManyStartsWithPrefix("aBcdp"));

        Assert.assertEquals(true, t.add(s3));
        Assert.assertEquals(true, t.contains(s3));
        Assert.assertEquals(true, t.contains(s2));
        Assert.assertEquals(false, t.contains(s1));
        Assert.assertEquals(2, t.size());

        Assert.assertEquals(2, t.howManyStartsWithPrefix("aB"));
        Assert.assertEquals(2, t.howManyStartsWithPrefix("aBcd"));
        Assert.assertEquals(1, t.howManyStartsWithPrefix("aBcdf"));
        Assert.assertEquals(0, t.howManyStartsWithPrefix("aBcdp"));

        Assert.assertEquals(true, t.remove(s2));
        Assert.assertEquals(false, t.contains(s2));
        Assert.assertEquals(false, t.contains(s1));
        Assert.assertEquals(true, t.contains(s3));
        Assert.assertEquals(1, t.size());
        Assert.assertEquals(1, t.howManyStartsWithPrefix("aB"));
        Assert.assertEquals(1, t.howManyStartsWithPrefix("aBcdf"));
        Assert.assertEquals(0, t.howManyStartsWithPrefix("aBcdgp"));

        Assert.assertEquals(true, t.remove(s3));
        Assert.assertEquals(false, t.contains(s2));
        Assert.assertEquals(false, t.contains(s1));
        Assert.assertEquals(false, t.contains(s3));
        Assert.assertEquals(0, t.size());
        Assert.assertEquals(0, t.howManyStartsWithPrefix("aB"));
        Assert.assertEquals(0, t.howManyStartsWithPrefix("aBcdf"));
        Assert.assertEquals(0, t.howManyStartsWithPrefix("aBcdgp"));
    }

    @Test
    public void testPrefix3() {
        TrieImpl t = instance();
        String s1 = "fizz";
        String s2 = "buzz";
        String s3 = "bring";
        String s4 = "buzo";
        String s5 = "fizzzika";

        Assert.assertEquals(true, t.add(s1));
        Assert.assertEquals(true, t.add(s2));
        Assert.assertEquals(true, t.add(s4));
        Assert.assertEquals(true, t.add(s5));

        Assert.assertEquals(false, t.contains(s3));
        Assert.assertEquals(true, t.add(s3));
        Assert.assertEquals(5, t.size());

        Assert.assertEquals(5, t.howManyStartsWithPrefix(""));
        Assert.assertEquals(2, t.howManyStartsWithPrefix("fi"));
        Assert.assertEquals(2, t.howManyStartsWithPrefix("fizz"));
        Assert.assertEquals(1, t.howManyStartsWithPrefix("buzz"));
        Assert.assertEquals(2, t.howManyStartsWithPrefix("buz"));
        Assert.assertEquals(2, t.howManyStartsWithPrefix("bu"));
        Assert.assertEquals(3, t.howManyStartsWithPrefix("b"));

        Assert.assertEquals(true, t.remove(s2));
        Assert.assertEquals(false, t.contains(s2));
        Assert.assertEquals(4, t.size());
        Assert.assertEquals(4, t.howManyStartsWithPrefix(""));
        Assert.assertEquals(0, t.howManyStartsWithPrefix("buzz"));
        Assert.assertEquals(1, t.howManyStartsWithPrefix("buz"));
        Assert.assertEquals(1, t.howManyStartsWithPrefix("bu"));
        Assert.assertEquals(2, t.howManyStartsWithPrefix("b"));

        Assert.assertEquals(true, t.add(s2));
        Assert.assertEquals(true, t.contains(s2));
        Assert.assertEquals(5, t.size());
        Assert.assertEquals(5, t.howManyStartsWithPrefix(""));
        Assert.assertEquals(1, t.howManyStartsWithPrefix("buzz"));
        Assert.assertEquals(2, t.howManyStartsWithPrefix("buz"));
        Assert.assertEquals(2, t.howManyStartsWithPrefix("bu"));
        Assert.assertEquals(3, t.howManyStartsWithPrefix("b"));
    }
}