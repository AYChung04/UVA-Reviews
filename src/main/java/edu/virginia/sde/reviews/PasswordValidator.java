package edu.virginia.sde.reviews;

public class PasswordValidator {
    public void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
    }

    public void checkPasswordMatch(String password, String retypePassword) {
        if (password == null || retypePassword == null || !password.equals(retypePassword)) {
            throw new IllegalArgumentException("Passwords do not match.");
        }
    }
}
