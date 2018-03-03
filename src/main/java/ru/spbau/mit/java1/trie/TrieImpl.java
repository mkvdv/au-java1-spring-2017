package ru.spbau.mit.java1.trie;

/**
 * Class for implementation of trie task.
 */
public class TrieImpl implements Trie {
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
    public boolean add(String element) {
        if (contains(element)) {
            return false;
        }
        int ix = 0;
        Vertex cur = root;

        while (ix < element.length()) {
            if (cur.getNode(element.charAt(ix)) != null) {
                // count data for howManyStartsWithPrefix
                cur.incStartsWithThis();
                cur = cur.getNode(element.charAt(ix));
                ix++;
            } else {
                break;
            }
        }

        //insert new elements if need
        while (ix < element.length()) {
            cur.incStartsWithThis();
            Vertex newVertex = new Vertex();
            cur.setNode(element.charAt(ix), newVertex);
            cur = newVertex;
            ix++;
        }
        cur.incStartsWithThis();
        cur.setEndOfWord(true);

        size++;
        return true;
    }

    /**
     * Expected complexity: O(|element|)
     *
     * @param element
     */
    @Override
    public boolean contains(String element) {
        int ix = 0;
        Vertex cur = root;

        while (ix < element.length()) {
            if (cur.getNode(element.charAt(ix)) != null) {
                cur = cur.getNode(element.charAt(ix));
                ix++;
            } else {
                break;
            }
        }
        return ix == element.length() && cur.isEndOfWord();
    }

    /**
     * Expected complexity: O(|element|)
     *
     * @param element
     * @return <tt>true</tt> if this set contained the specified element
     */
    @Override
    public boolean remove(String element) {
        if (!contains(element)) {
            return false;
        }

        int ix = 0;
        Vertex cur = root;

        while (ix < element.length()) {
            if (cur != null) {
                cur.decStartsWithThis();
                Vertex next = cur.getNode(element.charAt(ix));
                if (next != null && next.getStartsWithThis() == 1) {
                    cur.setNode(element.charAt(ix), null);
                    break;
                }
                cur = cur.getNode(element.charAt(ix));
                ix++;
            } else {
                break;
            }
        }

        if (ix == element.length() && cur != null && cur.isEndOfWord) {
            cur.setEndOfWord(false);
        }

        size--;
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
    public int howManyStartsWithPrefix(String prefix) {
        int ix = 0;
        Vertex cur = root;

        while (ix < prefix.length()) {
            if (cur != null) {
                cur = cur.getNode(prefix.charAt(ix));
                ix++;
            } else {
                return 0;
            }
        }

        if (cur != null)
            return cur.getStartsWithThis();
        return 0;
    }

    class Vertex {
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

        boolean isEndOfWord() {
            return isEndOfWord;
        }

        void setEndOfWord(boolean endOfWord) {
            isEndOfWord = endOfWord;
        }

        Vertex getNode(char c) {
            assert (Character.isLetter(c));

            if (Character.isUpperCase(c)) {
                return upperNext[c - 'A'];
            } else if (Character.isLowerCase(c)) {
                return lowerNext[c - 'a'];
            }

            assert false; // unsupported symbols, terminated
            return null;
        }

        /**
         * Set new child in node, overide existed
         */
        void setNode(char c, Vertex vertex) {
            assert (Character.isLetter(c));

            if (Character.isUpperCase(c)) {
                upperNext[c - 'A'] = vertex;
            } else if (Character.isLowerCase(c)) {
                lowerNext[c - 'a'] = vertex;
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
        }
    }
}
