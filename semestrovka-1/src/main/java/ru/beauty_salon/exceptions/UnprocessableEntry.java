package ru.beauty_salon.exceptions;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;

@Getter
public class UnprocessableEntry extends Exception {
    private int status = HttpServletResponse.SC_UNPROCESSABLE_CONTENT;

    public UnprocessableEntry(String field, String message) {
        super("{\"error\": {\"" + field + "\"}\": \"" + message + "\"}}");
    }
}
