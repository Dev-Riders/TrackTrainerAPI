package dev.devriders.tracktrainerrestapiv2.utils;

import java.util.Random;

public class RandomCodeGenerator {
    public static String generateRandomCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}

