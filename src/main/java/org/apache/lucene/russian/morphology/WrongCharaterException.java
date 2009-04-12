package org.apache.lucene.russian.morphology;


public class WrongCharaterException extends RuntimeException{
    public WrongCharaterException() {
    }

    public WrongCharaterException(String message) {
        super(message);
    }
}
