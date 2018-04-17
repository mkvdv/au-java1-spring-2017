package ru.spbau.mit.java1.dict;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;

public class DictionaryImpl implements Dictionary {
    private static final double MIN_FILL_FACTOR = 0.25; // пусть заполнена не менее чем на 25%
    private static final int DEFAULT_MAX_SIZE = 64; // 2 ** 7, минимальный размер таблицы
    private final int maxChainLength;
    private int maxSize = DEFAULT_MAX_SIZE; // greater or equal to DEFAULT_MAX_SIZE
    private int size = 0;
    private ArrayList<LinkedList<Node>> table = new ArrayList<>(maxSize);

    DictionaryImpl(int maxChainLength) {
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
        int indexInList = myHash(key);
        int indexInChain = getKeyIndexInChain(table.get(indexInList), key);
        return indexInChain != -1;
    }

    @Override
    public String get(@NotNull String key) {
        int indexInList = myHash(key);
        int indexInChain = getKeyIndexInChain(table.get(indexInList), key);

        if (indexInChain != -1) {
            return table.get(indexInList).get(indexInChain).value;
        } else {
            return null;
        }
    }

    @Override
    public String put(@NotNull String key, @NotNull String value) {
        LinkedList<Node> chain = table.get(myHash(key));
        int indexInChain = getKeyIndexInChain(chain, key);

        String prev = null;
        if (indexInChain != -1) { // contains
            prev = chain.get(indexInChain).value;
            chain.set(indexInChain, new Node(key, value));
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
        int indexInChain = getKeyIndexInChain(chain, key);

        String value = null;
        if (indexInChain != -1) { // contains
            value = chain.get(indexInChain).value; // save for returning

            if ((double) size / maxSize < MIN_FILL_FACTOR && size > DEFAULT_MAX_SIZE) {
                rehash(size / 2); // reduce size
            }
            chain = table.get(myHash(key)); // they can change after rehash
            indexInChain = getKeyIndexInChain(chain, key);

            chain.remove(indexInChain);
            size--;
        }

        return value;
    }

    private void rehash(int newMaxSize) {
        assert newMaxSize > 0;

        maxSize = newMaxSize; // so hash will be another
        ArrayList<LinkedList<Node>> newTable = new ArrayList<>(newMaxSize);

        for (int i = 0; i < maxSize; i++) {
            newTable.add(new LinkedList<>());
        }

        for (LinkedList<Node> oldChain : table) {
            for (Node oldNode : oldChain) {
                LinkedList<Node> newChain = newTable.get(myHash(oldNode.key)); // new hash
                newChain.add(oldNode);
            }
        }

        table = newTable;
    }

    @Override
    public void clear() {
        for (LinkedList<Node> chain : table) {
            chain.clear();
        }
        size = 0;
    }

    private int getKeyIndexInChain(LinkedList<Node> chain, String key) {
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
            int hashValue = key.hashCode();
            if (hashValue == Integer.MIN_VALUE) {
                return 0;
            }
            return Math.abs(hashValue);
        }

        public boolean equals(@NotNull Node other) {
            return this.key.equals(other.key);
        }

        boolean equals(@NotNull String otherKey) {
            return this.key.equals(otherKey);
        }

        public String toString() {
            return "(" + key + ", " + value + ")";
        }
    }

}
