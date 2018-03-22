package ru.spbau.mit.java1.dict;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;

public class DictionaryImpl implements Dictionary {
    private static final double MIN_FILL_FACTOR = 0.05; // пусть заполнена не менее чем на 5%
    private static final int DEFAULT_MAX_SIZE = 64; // 2 ** 7
    private final int maxChainLength;
    private int maxSize = DEFAULT_MAX_SIZE;
    private int size = 0;
    private ArrayList<LinkedList<Node>> table = new ArrayList<>(maxSize);

    public DictionaryImpl(int maxChainLength) {
        if (maxChainLength < 0) {
            throw new IllegalArgumentException();
        }
        this.maxChainLength = maxChainLength;

        for (int i = 0; i < maxSize; i++) {
            table.add(new LinkedList<>());
        }

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(@NotNull String key) {
        int list_ix = myHash(key);
        return find_key(table.get(list_ix), key) != -1;
    }

    @Override
    public String get(@NotNull String key) {
        int listIx = myHash(key);
        int stringIx = find_key(table.get(listIx), key);

        if (stringIx != -1) {
            return table.get(listIx).get(stringIx).value;
        } else {
            return null;
        }
    }

    @Override
    public String put(@NotNull String key, @NotNull String value) {
        LinkedList<Node> chain = table.get(myHash(key));
        int string_ix = find_key(chain, key);

        String prev = null;
        if (string_ix != -1) { // contains
            prev = chain.get(string_ix).value;
            chain.set(string_ix, new Node(key, value));
        } else {
            if (chain.size() < maxChainLength && size < maxSize) {
                chain.add(new Node(key, value));
            } else {
                rehash(size * 2); // increase size
                chain = table.get(myHash(key));
                chain.add(new Node(key, value));
            }
            size++;
        }

        return prev;
    }

    @Override
    public String remove(@NotNull String key) {
        LinkedList<Node> chain = table.get(myHash(key));
        int string_ix = find_key(chain, key);

        String value = null;
        if (string_ix != -1) { // contains
            value = chain.get(string_ix).value; // save for returning

            if ((double) size / maxSize < MIN_FILL_FACTOR) {
                rehash(size / 2); // reduce size
            }
            chain = table.get(myHash(key));
            chain.remove(string_ix);
            size--;
        }

        return value;
    }

    private void rehash(int new_max_size) {
        assert new_max_size > 0;

        maxSize = new_max_size; // so hash will be another
        ArrayList<LinkedList<Node>> new_table = new ArrayList<>(new_max_size);

        for (int i = 0; i < maxSize; i++) {
            new_table.add(new LinkedList<>());
        }

        for (LinkedList<Node> old_chain : table) {
            for (Node old_node : old_chain) {
                LinkedList<Node> new_chain = new_table.get(myHash(old_node.key)); // new hash
                new_chain.add(old_node);
            }
        }

        table = new_table;
    }

    @Override
    public void clear() {
        for (LinkedList<Node> chain : table) {
            chain.clear();
        }
        size = 0;
    }

    // return ix of node with this key in chain, else -1 if not exist
    private int find_key(LinkedList<Node> chain, String key) {
        for (int ix = 0; ix < chain.size(); ix++) {
            Node node = chain.get(ix);
            if (node.equals(key)) {
                return ix;
            }
        }

        return -1; // not found
    }

    private int myHash(@NotNull String key) {
        return key.hashCode() % maxSize;
    }

    private static class Node {
        String key;
        String value;

        Node(@NotNull String key, @NotNull String value) {
            this.key = key;
            this.value = value;
        }

        public int hashCode() {
            return key.hashCode();
        }

        public boolean equals(@NotNull Node other) {
            return this.key.equals(other.key);
        }

        public boolean equals(@NotNull String other_key) {
            return this.key.equals(other_key);
        }

        public String toString() {
            return "(" + key + ", " + value + ")";
        }
    }

}
