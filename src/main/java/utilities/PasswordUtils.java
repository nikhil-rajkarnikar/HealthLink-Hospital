/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 *
 * @author pukarsharma
 */
public class PasswordUtils {

    private static final int SALT_LENGTH = 16; // Length of the salt in bytes

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        byte[] salt = generateSalt();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        digest.update(salt);
        byte[] hash = digest.digest(password.getBytes());

        // Convert the hash and salt to Base64-encoded strings
        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String hashBase64 = Base64.getEncoder().encodeToString(hash);

        // Store the salt and hash in the database
        String storedPassword = saltBase64 + "$" + hashBase64;

        return storedPassword;
    }

    public static boolean verifyPassword(String storedPassword, String inputPassword) throws NoSuchAlgorithmException {
        String[] parts = storedPassword.split("\\$");
        if (parts.length != 2) {
            return false; // Invalid stored password format
        }

        String saltBase64 = parts[0];
        String storedHashBase64 = parts[1];

        byte[] salt = Base64.getDecoder().decode(saltBase64);
        byte[] inputHash = hashPasswordWithSalt(inputPassword, salt);

        String inputHashBase64 = Base64.getEncoder().encodeToString(inputHash);

        return inputHashBase64.equals(storedHashBase64);
    }

    public static byte[] hashPasswordWithSalt(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        digest.update(salt);
        return digest.digest(password.getBytes());
    }

    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }
}
