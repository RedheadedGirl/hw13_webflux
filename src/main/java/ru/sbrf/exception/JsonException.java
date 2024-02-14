package ru.sbrf.exception;

public class JsonException extends RuntimeException {
    public JsonException(String message, Exception e) {
        super(message, e);
    }
}
