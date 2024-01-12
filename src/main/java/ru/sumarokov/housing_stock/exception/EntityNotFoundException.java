package ru.sumarokov.housing_stock.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?> classEntity, String name) {
        super(String.format("Object of class %s with name = %s was not found", classEntity.getName(), name));
    }

    public EntityNotFoundException(Class<?> classEntity, Long id) {
        super(String.format("Object of class %s with id = %d not found", classEntity.getName(), id));
    }
}
