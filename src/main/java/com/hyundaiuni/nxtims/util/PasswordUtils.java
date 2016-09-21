package com.hyundaiuni.nxtims.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Pattern;

import com.hyundaiuni.nxtims.exception.MessageDigestException;

public class PasswordUtils {
    private static final String PASSWORD_REGEX = "^.*(?=.{10,})(?=.*[0-9])(?=.*[a-zA-Z])(?=[\\S]+$).*$";
    private static final int PASSWORD_LENGTH = 10;
    private static final String[] CANDIDATES = new String[] {"abcdefghijklmnopqrstuvwxyz", "0123456789"};

    public static String getMessageDigest(String password, String algorithm, String charsetName) throws MessageDigestException{
        return MessageDigestUtils.getMessageDigest(password, algorithm, charsetName);
    }

    public static boolean patternCheck(String password) {
        if(Pattern.matches(PASSWORD_REGEX, password)) {
            return true;
        }
        else {
            return false;
        }
    }

    public static String getRandomPassword() throws NoSuchAlgorithmException {
        char[] generated = new char[PASSWORD_LENGTH];
        SecureRandom secureRandom;

        secureRandom = SecureRandom.getInstance("SHA1PRNG");

        StringBuilder candidateAll = new StringBuilder();

        int watermark = 0;

        for(int i = 0; i < CANDIDATES.length; i++) {
            if(CANDIDATES[i] != null && !"".equals(CANDIDATES[i])) {
                generated[watermark++] = CANDIDATES[i].charAt(secureRandom.nextInt(CANDIDATES[i].length()));
                candidateAll.append(CANDIDATES[i]);
            }
        }

        for(int i = watermark; i < PASSWORD_LENGTH; i++) {
            generated[i] = candidateAll.toString().charAt(secureRandom.nextInt(candidateAll.length()));
        }

        for(int i = 100000; i >= 0; i--) {
            int x = secureRandom.nextInt(PASSWORD_LENGTH);
            int y = secureRandom.nextInt(PASSWORD_LENGTH);
            char tmp = generated[x];
            generated[x] = generated[y];
            generated[y] = tmp;
        }

        return new String(generated);
    }
}
