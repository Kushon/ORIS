package ru.beauty_salon.exceptions;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;

@Getter
public class InvalidCredentials extends Exception {
    private int status = HttpServletResponse.SC_UNAUTHORIZED;

    public InvalidCredentials() {
        super("{\"error\": \"invalid credentials\"}");
    }
}
