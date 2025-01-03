package ru.beauty_salon;

import java.security.SecureRandom;
import java.util.Base64;

public class SessionIdGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int ID_LENGTH = 32;

    public static String generateSessionId() {
        byte[] randomBytes = new byte[ID_LENGTH];
        RANDOM.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

}
