package ru.beauty_salon.exceptions;

public class InvalidLength extends UnprocessableEntry {
    public InvalidLength(String field) {
        super(field, "invalid length");
    }
}
