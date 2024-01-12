package ru.sumarokov.housing_stock.exception;

public class IllegalAuthorizationHeaderException extends RuntimeException {
    public IllegalAuthorizationHeaderException(String message) {
        super(message);
    }
}
