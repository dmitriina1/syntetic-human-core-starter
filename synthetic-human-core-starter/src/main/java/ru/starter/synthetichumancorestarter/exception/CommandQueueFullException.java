package ru.starter.synthetichumancorestarter.exception;

public class CommandQueueFullException extends RuntimeException {
    public CommandQueueFullException(String message) {
        super(message);
    }
}