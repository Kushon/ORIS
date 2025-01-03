package ru.beauty_salon;

import java.util.regex.Pattern;

import ru.beauty_salon.exceptions.EmptyField;
import ru.beauty_salon.exceptions.InvalidLength;

public class CredentalsValidator {

    private static final String emailRegex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

    public static boolean checkPassword(String password) throws EmptyField {
        if (password == null || password.equals(""))
            throw new EmptyField("password");
        return true;
    }

    public static boolean checkEmail(String email) throws EmptyField {
        if (email == null || email.equals("") || !Pattern.matches(emailRegex, email))
            throw new EmptyField("email");
        return true;
    }

    public static boolean checkConfirmPassword(String confirmPassword) throws EmptyField {
        if (confirmPassword == null || confirmPassword.equals(""))
            throw new EmptyField("confirm password");
        return true;
    }

    public static boolean matchPasswords(String password, String confirmPassword) throws EmptyField {
        if (!password.equals(confirmPassword))
            throw new EmptyField("passwords");
        return true;
    }

    public static boolean checkFirstName(String firstName) throws EmptyField, InvalidLength {
        if (firstName == null || firstName.equals(""))
            throw new EmptyField("firstName");
        if (firstName.length() > 50)
            throw new InvalidLength("firstName");
        return true;
    }

    public static boolean checkLastName(String lastName) throws EmptyField, InvalidLength {
        if (lastName == null || lastName.equals(""))
            throw new EmptyField("lastName");
        if (lastName.length() > 50)
            throw new InvalidLength("lastName");
        return true;
    }
}
