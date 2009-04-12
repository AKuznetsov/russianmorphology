package org.apache.lucene.russian.morphology;


public class SuffixToLongException extends RuntimeException {

    public SuffixToLongException() {
    }

    public SuffixToLongException(String message) {
        super(message);
    }
}
