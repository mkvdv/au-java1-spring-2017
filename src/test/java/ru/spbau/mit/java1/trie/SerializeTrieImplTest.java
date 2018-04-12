package ru.spbau.mit.java1.trie;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class SerializeTrieImplTest {
    private static Trie instance() {
        try {
            return (TrieImpl) Class.forName("ru.spbau.mit.java1.trie.TrieImpl").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Error while class loading");
    }

    @Test
    public void testSerializationEmpty() {
        TrieImpl trie = (TrieImpl) instance();

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            trie.serialize(baos);

            TrieImpl copy = (TrieImpl) instance();
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            copy.deserialize(bais);

            assertEquals(trie.size(), copy.size());
            assertEquals(trie.contains("foo"), copy.contains("foo"));

        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerialization1() {
        TrieImpl trie = (TrieImpl) instance();

        ArrayList<String> strings = new ArrayList<>(5);
        strings.add("fizz");
        strings.add("buzz");
        strings.add("bring");
        strings.add("buzo");
        strings.add("fizzziKa");

        for (String s : strings) {
            trie.add(s);
        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            trie.serialize(baos);

            TrieImpl copy = (TrieImpl) instance();
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            copy.deserialize(bais);

            for (String s : strings) {
                assertTrue(copy.contains(s));
            }
            assertEquals(trie.size(), copy.size());
            assertEquals(trie.howManyStartsWithPrefix("bu"), copy.howManyStartsWithPrefix("bu"));

        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerialization2() {
        TrieImpl t = (TrieImpl) instance();

        try {
            for (int i = 0; i < 26; i++) {
                String small = Character.toString((char) ('a' + i));
                t.add(small);

                String big = Character.toString((char) ('A' + i));
                t.add(big);
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            t.serialize(baos);

            TrieImpl copy = (TrieImpl) instance();
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            copy.deserialize(bais);

            assertEquals(t.size(), copy.size());

            for (int i = 0; i < 26; i++) {
                String lower = Character.toString((char) ('a' + i));
                assertEquals(t.contains(lower), copy.contains(lower));

                String big = Character.toString((char) ('A' + i));
                assertEquals(t.contains(big), copy.contains(big));
            }

            for (int i = 0; i < 26; i++) {
                String small = Character.toString((char) ('a' + i));
                t.remove(small);
                copy.remove(small);

                String big = Character.toString((char) ('A' + i));
                t.remove(big);
                copy.remove(big);
            }
            assertEquals(t.size(), copy.size());
        } catch (IllegalArgumentException e) {
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }
}



