package ru.spbau.mit.java1.trie;

import java.io.*;

/**
 * Class for implementation of trie task.
 */
public class TrieImpl implements Trie, StreamSerializable {
    private static final int ALPHABET_SIZE = 26;
    private Vertex root;
    private int size; // number of words in tree

    TrieImpl() {
        root = new Vertex();
        size = 0;
    }

    /**
     * Expected complexity: O(|element|)
     *
     * @param element
     * @return <tt>true</tt> if this set did not already contain the specified
     * element
     */
    @Override
    public boolean add(String element) throws IllegalArgumentException {
        if (element == null) {
            throw new IllegalArgumentException("element argument is null");
        }
        if (contains(element)) {
            return false;
        }

        size++;
        Vertex cur = root;
        cur.incStartsWithThis(); // count data for howManyStartsWithPrefix

        for (char curChar : element.toCharArray()) {
            if (cur.getNext(curChar) != null) {
                cur = cur.getNext(curChar);
                cur.incStartsWithThis();
            } else { //insert new elements if need
                Vertex newVertex = new Vertex();
                cur.setNext(curChar, newVertex);
                cur = newVertex;
                cur.incStartsWithThis();
            }
        }

        cur.setEndOfWord(true);


        return true;
    }

    /**
     * Expected complexity: O(|element|)
     *
     * @param element
     */
    @Override
    public boolean contains(String element) throws IllegalArgumentException {
        if (element == null) {
            throw new IllegalArgumentException("element argument is null");
        }

        int charProccessed = 0;
        Vertex cur = root;

        for (char curChar : element.toCharArray()) {
            if (cur.getNext(curChar) != null) {
                cur = cur.getNext(curChar);
                charProccessed++;
            } else {
                break;
            }
        }
        return charProccessed == element.length() && cur.isEndOfWord();
    }

    /**
     * Expected complexity: O(|element|)
     *
     * @param element
     * @return <tt>true</tt> if this set contained the specified element
     */
    @Override
    public boolean remove(String element) throws IllegalArgumentException {
        if (element == null) {
            throw new IllegalArgumentException("element argument is null");
        }
        if (!contains(element)) {
            return false;
        }

        size--;
        Vertex cur = root;

        // find first node, which child is bamboo - and cut off bamboo
        for (char curChar : element.toCharArray()) {
            if (cur != null) {
                cur.decStartsWithThis();
                Vertex next = cur.getNext(curChar);

                // check that last child is bamboo - if it is - cut it and return
                if (next != null && next.getStartsWithThis() == 1) {
                    cur.setNext(curChar, null);
                    return true;
                }

                cur = cur.getNext(curChar);
            } else {
                break;
            }
        }

        // if u have not cut any bamboo - ex, we delete "abc" from trie {"abc", "abcd"}
        // so we not delete any node, but must mark it aa not end of word
        if (cur != null && cur.isEndOfWord) { // delete or not ??
            cur.setEndOfWord(false);
        }

        return true;
    }

    /**
     * Expected complexity: O(1)
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Expected complexity: O(|prefix|)
     *
     * @param prefix
     */
    @Override
    public int howManyStartsWithPrefix(String prefix) throws IllegalArgumentException {
        if (prefix == null) {
            throw new IllegalArgumentException("element argument is null");
        }
        int ix = 0;
        Vertex cur = root;

        while (ix < prefix.length()) {
            if (cur != null) {
                cur = cur.getNext(prefix.charAt(ix));
                ix++;
            } else {
                return 0;
            }
        }

        if (cur != null)
            return cur.getStartsWithThis();
        return 0;
    }

    @Override
    public void serialize(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeInt(size);
        root.serializeWith(dos);
    }

    /**
     * Replace current state with data from input stream
     */
    @Override
    public void deserialize(InputStream in) throws IOException {
        DataInputStream dis = new DataInputStream(in);
        size = dis.readInt();
        root = Vertex.deserailizeWith(dis);
    }

    static class Vertex {
        private Vertex upperNext[]; // for uppercase letters
        private Vertex lowerNext[]; // for lowercase letters
        private boolean isEndOfWord;
        // how Many Words Starts With Prefix, which ends in this vertex
        private int startsWithThis;

        Vertex() {
            upperNext = new Vertex[ALPHABET_SIZE];
            lowerNext = new Vertex[ALPHABET_SIZE];
            isEndOfWord = false;
            startsWithThis = 0;
        }

        static Vertex deserailizeWith(DataInputStream dis) throws IOException {
            Vertex vertex = new Vertex();

            vertex.isEndOfWord = dis.readBoolean();
            vertex.startsWithThis = dis.readInt();

            // get boolean arrays of existed verticies for easy restoring
            boolean[] lowerVerticiesExisted = new boolean[ALPHABET_SIZE];
            boolean[] upperVerticiesExisted = new boolean[ALPHABET_SIZE];

            for (int i = 0; i < ALPHABET_SIZE; i++) {
                lowerVerticiesExisted[i] = dis.readBoolean();
            }
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                upperVerticiesExisted[i] = dis.readBoolean();
            }

            // deserialize verticies for existed recursively
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                if (lowerVerticiesExisted[i]) {
                    vertex.lowerNext[i] = deserailizeWith(dis);
                }
            }
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                if (upperVerticiesExisted[i]) {
                    vertex.upperNext[i] = deserailizeWith(dis);
                }
            }

            return vertex;
        }

        // recursively serialize all nodes
        void serializeWith(DataOutputStream dos) throws IOException {
            // serialize object metadata
            dos.writeBoolean(isEndOfWord);
            dos.writeInt(startsWithThis);

            //serialize array / links
            // write boolean array, bool for every node in upperNext -- true if link exists
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                dos.writeBoolean(lowerNext[i] != null);
            }
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                dos.writeBoolean(upperNext[i] != null);
            }

            // now really serialize nodes
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                if (lowerNext[i] != null) {
                    lowerNext[i].serializeWith(dos);
                }
            }
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                if (upperNext[i] != null) {
                    upperNext[i].serializeWith(dos);
                }
            }
        }

        boolean isEndOfWord() {
            return isEndOfWord;
        }

        void setEndOfWord(boolean endOfWord) {
            isEndOfWord = endOfWord;
        }

        Vertex getNext(char c) throws IllegalArgumentException {
            if (Character.isUpperCase(c)) {
                return upperNext[c - 'A'];
            } else if (Character.isLowerCase(c)) {
                return lowerNext[c - 'a'];
            }

            throw new IllegalArgumentException("unsupported character");
        }

        /**
         * Set new child in node, override existed
         */
        void setNext(char c, Vertex vertex) throws IllegalArgumentException {
            if (Character.isUpperCase(c)) {
                upperNext[c - 'A'] = vertex;
            } else if (Character.isLowerCase(c)) {
                lowerNext[c - 'a'] = vertex;
            } else {
                throw new IllegalArgumentException("unsupported character");
            }
        }

        int getStartsWithThis() {
            return startsWithThis;
        }

        void incStartsWithThis() {
            startsWithThis++;
        }

        void decStartsWithThis() {
            startsWithThis--;
            assert startsWithThis >= 0;
        }
    }
}
