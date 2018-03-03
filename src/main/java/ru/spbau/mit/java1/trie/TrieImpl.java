package ru.spbau.mit.java1.trie;

public class TrieImpl implements Trie {
    private static int ALPHABET_SIZE = 26;
    private Vertex root;
    private int size;

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
            Vertex new_vx = new Vertex();
            cur.setNode(element.charAt(ix), new_vx);
            cur = new_vx;
            ix++;
        }
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
            if (cur.getNode(element.charAt(ix)) != null) {
                if (cur.getStartsWithThis() == 1) {
                    cur.setNode(element.charAt(ix), null);
                    break;
                }
                cur.decStartsWithThis();
                cur = cur.getNode(element.charAt(ix));
                ix++;
            } else {
                break;
            }
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
        if (contains(prefix)) {
            return 0;
        }

        int ix = 0;
        Vertex cur = root;

        while (ix < prefix.length()) {
            if (cur.getNode(prefix.charAt(ix)) != null) {
                cur = cur.getNode(prefix.charAt(ix));
                ix++;
            }
        }
        return cur.getStartsWithThis();
    }

    class Vertex {
        private Vertex upperNext[];
        private Vertex lowerNext[];
        private boolean isEndOfWord;
        private int startsWithThis; // how Many Words Starts With Prefix, which end in this vertex

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

            return null;
        }

        /**
         * Set new child in node, overide existed
         */
        void setNode(char c, Vertex vx) {
            assert (Character.isLetter(c));

            if (Character.isUpperCase(c)) {
                upperNext[c - 'A'] = vx;
            } else if (Character.isLowerCase(c)) {
                lowerNext[c - 'a'] = vx;
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
