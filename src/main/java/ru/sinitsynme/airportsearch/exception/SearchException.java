package ru.sinitsynme.airportsearch.exception;

public class SearchException extends RuntimeException {

    public SearchException() {
    }

    public SearchException(String message) {
        super(message);
    }
}
