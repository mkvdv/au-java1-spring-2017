package ru.spbau.mit.java1.trie;

import ru.spbau.mit.java1.trie.exceptions.IncorrectInputException;

/**
 * Class for trie task.
 */
public interface Trie {

    /**
     * Expected complexity: O(|element|)
     *
     * @return <tt>true</tt> if this set did not already contain the specified
     * element
     */
    boolean add(String element) throws IncorrectInputException;

    /**
     * Expected complexity: O(|element|)
     */
    boolean contains(String element) throws IncorrectInputException;

    /**
     * Expected complexity: O(|element|)
     *
     * @return <tt>true</tt> if this set contained the specified element
     */
    boolean remove(String element) throws IncorrectInputException;

    /**
     * Expected complexity: O(1)
     */
    int size();

    /**
     * Expected complexity: O(|prefix|)
     */
    int howManyStartsWithPrefix(String prefix) throws IncorrectInputException;
}