package ru.gb.springdemo.exception;

public class UserIssueLimitExceededException extends RuntimeException {
    public UserIssueLimitExceededException(String message) {
        super(message);
    }
}
