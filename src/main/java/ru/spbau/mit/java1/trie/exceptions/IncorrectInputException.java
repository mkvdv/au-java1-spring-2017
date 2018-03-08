package ru.spbau.mit.java1.trie.exceptions;

/**
 * Exception for TrieImpl ops. Throw, if input string is wrong
 */
public class IncorrectInputException extends Exception {
    public IncorrectInputException(String s) {
        super(s);
    }
}
