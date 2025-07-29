package ru.starter.synthetichumancorestarter.exception;

public class CommandValidationException extends RuntimeException {
    public CommandValidationException(String message) {
        super(message);
    }
}
