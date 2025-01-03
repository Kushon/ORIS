package ru.beauty_salon.exceptions;

public class EmptyField extends UnprocessableEntry {

    public EmptyField(String field) {
        super(field, "empty");
    }
    
}
