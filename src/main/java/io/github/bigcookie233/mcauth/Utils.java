package io.github.bigcookie233.mcauth;

import org.apache.commons.codec.binary.Base32;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    public static boolean validateMinecraftId(String id) {
        if (id.length() < 3 || id.length() > 16) {
            return false;
        }
        return id.matches("^[a-zA-Z0-9_]+$");
    }

    public static boolean validateCode(String code) {
        return code.matches("^\\d{6}$");
    }

    public static String calculateSHA256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA256");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            Base32 encoder = new Base32();
            return encoder.encodeAsString(digest);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            return "";
        }
    }
}
