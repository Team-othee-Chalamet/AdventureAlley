package org.example.adventurealley.catalog.utility;

public class Hasher {

    public static String encrypt(String password) {
        StringBuilder sb = new StringBuilder();
        for (char c : password.toCharArray()) {
            // Take value of character and multiply by length of password
            sb.append((char)(c * password.length()));
        }
        return sb.toString();
    }

    public static String decrypt(String encrypted) {
        StringBuilder sb = new StringBuilder();
        for (char c : encrypted.toCharArray()) {
            sb.append((char)(c - 3));
        }
        return sb.toString();
    }
}
