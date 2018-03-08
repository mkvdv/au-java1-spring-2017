package ru.spbau.mit.java1.trie;

import ru.spbau.mit.java1.trie.exceptions.IncorrectInputException;

/**
 * Class for implementation of trie task.
 */
public class TrieImpl implements Trie {
    private static final int ALPHABET_SIZE = 26;
    private final Vertex root;
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
    public boolean add(String element) throws IncorrectInputException {
        if (element == null) {
            throw new IncorrectInputException("element argument is null");
        }
        if (contains(element)) {
            return false;
        }
        int ix = 0;
        Vertex cur = root;

        while (ix < element.length()) {
            if (cur.getNext(element.charAt(ix)) != null) {
                cur.incStartsWithThis(); // count data for howManyStartsWithPrefix

                cur = cur.getNext(element.charAt(ix));
                ix++;
            } else {
                break;
            }
        }

        //insert new elements if need
        while (ix < element.length()) {
            cur.incStartsWithThis();

            Vertex newVertex = new Vertex();
            cur.setNext(element.charAt(ix), newVertex);

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
    public boolean contains(String element) throws IncorrectInputException {
        if (element == null) {
            throw new IncorrectInputException("element argument is null");
        }

        int ix = 0;
        Vertex cur = root;

        while (ix < element.length()) {
            if (cur.getNext(element.charAt(ix)) != null) {
                cur = cur.getNext(element.charAt(ix));
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
    public boolean remove(String element) throws IncorrectInputException {
        if (element == null) {
            throw new IncorrectInputException("element argument is null");
        }
        if (!contains(element)) {
            return false;
        }

        size--;
        int ix = 0;
        Vertex cur = root;

        // find first node, which child is bamboo - and cut off bamboo
        while (ix < element.length()) {
            if (cur != null) {
                cur.decStartsWithThis();
                Vertex next = cur.getNext(element.charAt(ix));

                // check that last child is bamboo - if it is - cut it and return
                if (next != null && next.getStartsWithThis() == 1) {
                    cur.setNext(element.charAt(ix), null);
                    return true;
                }

                cur = cur.getNext(element.charAt(ix));
                ix++;
            } else {
                break;
            }
        }

        // if u have not cut any bamboo - ex, we delete "abc" from trie {"abc", "abcd"}
        // so we not delete any node, but must mark it aa not end of word
        if (ix == element.length() && cur != null && cur.isEndOfWord) { // delete or not ??
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
    public int howManyStartsWithPrefix(String prefix) throws IncorrectInputException {
        if (prefix == null) {
            throw new IncorrectInputException("element argument is null");
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

        Vertex getNext(char c) throws IncorrectInputException {
            assert (Character.isLetter(c));

            if (Character.isUpperCase(c)) {
                return upperNext[c - 'A'];
            } else if (Character.isLowerCase(c)) {
                return lowerNext[c - 'a'];
            }

            throw new IncorrectInputException("unsupported character");
        }

        /**
         * Set new child in node, override existed
         */
        void setNext(char c, Vertex vertex) throws IncorrectInputException {
            assert (Character.isLetter(c));

            if (Character.isUpperCase(c)) {
                upperNext[c - 'A'] = vertex;
            } else if (Character.isLowerCase(c)) {
                lowerNext[c - 'a'] = vertex;
            } else {
                throw new IncorrectInputException("unsupported character");
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
