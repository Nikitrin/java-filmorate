package ru.yandex.practicum.filmorate.exception;

public class InvalidInput extends RuntimeException{
    private final String message;
    public InvalidInput(String message) {
        super(message);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
