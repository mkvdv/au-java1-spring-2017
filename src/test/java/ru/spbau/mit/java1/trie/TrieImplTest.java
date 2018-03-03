package ru.spbau.mit.java1.trie;

import org.junit.Assert;
import org.junit.Test;

public class TrieImplTest {

    @Test
    public void testBasic1() {
        // add - contains - size - remove - size - contains
        TrieImpl t = new TrieImpl();
        String s = "abc";
        Assert.assertEquals(false, t.contains(s));
        Assert.assertEquals(0, t.size());

        Assert.assertEquals(false, t.remove(s));
        Assert.assertEquals(0, t.size());

        Assert.assertEquals(true, t.add(s));
        Assert.assertEquals(true, t.contains(s));
        Assert.assertEquals(1, t.size());
        Assert.assertEquals(1, t.howManyStartsWithPrefix("ab"));
        Assert.assertEquals(1, t.howManyStartsWithPrefix("a"));

        Assert.assertEquals(false, t.add(s));
        Assert.assertEquals(true, t.contains(s));
        Assert.assertEquals(1, t.size());

        Assert.assertEquals(true, t.remove(s));
        Assert.assertEquals(false, t.contains(s));
        Assert.assertEquals(0, t.size());
    }

    @Test
    public void testBasic2() {
        // add - contains - prefix - add - prefix - remove - size - prefix
        TrieImpl t = new TrieImpl();
        String s1 = "aBcde";
        String s2 = "aBcfg";

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
        Assert.assertEquals(2, t.howManyStartsWithPrefix("aB"));
        Assert.assertEquals(2, t.howManyStartsWithPrefix("aBc"));
        Assert.assertEquals(1, t.howManyStartsWithPrefix("aBcd"));
        Assert.assertEquals(1, t.howManyStartsWithPrefix("aBcf"));
        Assert.assertEquals(0, t.howManyStartsWithPrefix("aBcfg"));


        Assert.assertEquals(true, t.remove(s1));
        Assert.assertEquals(true, t.contains(s2));
        Assert.assertEquals(false, t.contains(s1));
        Assert.assertEquals(1, t.size());

        Assert.assertEquals(1, t.howManyStartsWithPrefix("aB"));
        Assert.assertEquals(1, t.howManyStartsWithPrefix("aBc"));
        Assert.assertEquals(1, t.howManyStartsWithPrefix("aBcf"));
        Assert.assertEquals(0, t.howManyStartsWithPrefix("aBcfg"));

        Assert.assertEquals(true, t.remove(s2));
        Assert.assertEquals(false, t.contains(s2));
        Assert.assertEquals(0, t.size());
    }
}