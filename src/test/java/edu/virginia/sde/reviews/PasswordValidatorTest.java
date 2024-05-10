package edu.virginia.sde.reviews;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {
    private PasswordValidator passwordValidator;

    @BeforeEach
    void setUp() {
        passwordValidator = new PasswordValidator();
    }

    // Test cases for validatePassword
    @Test
    void testValidatePassword_ValidLength() {
        assertDoesNotThrow(() -> passwordValidator.validatePassword("ValidPass1!"));
    }

    @Test
    void testValidatePassword_Exactly8Chars() {
        assertDoesNotThrow(() -> passwordValidator.validatePassword("Passwrd8"));
    }

    @Test
    void testValidatePassword_LessThan8Chars() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> passwordValidator.validatePassword("Short7!")
        );
        assertEquals("Password must be at least 8 characters long.", exception.getMessage());
    }

    // Edge case for exactly 7 characters
    @Test
    void testValidatePassword_7Chars() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> passwordValidator.validatePassword("Seven7!")
        );
        assertEquals("Password must be at least 8 characters long.", exception.getMessage());
    }

    // Test cases for checkPasswordMatch
    @Test
    void testCheckPasswordMatch_Success() {
        assertDoesNotThrow(() -> passwordValidator.checkPasswordMatch("ValidPass1!", "ValidPass1!"));
    }

    @Test
    void testCheckPasswordMatch_Mismatch() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> passwordValidator.checkPasswordMatch("Password1!", "DifferentPass1!")
        );
        assertEquals("Passwords do not match.", exception.getMessage());
    }

    // Edge cases for empty strings
    @Test
    void testCheckPasswordMatch_EmptyStringsMatch() {
        assertDoesNotThrow(() -> passwordValidator.checkPasswordMatch("", ""));
    }

    @Test
    void testCheckPasswordMatch_EmptyStringMismatch() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> passwordValidator.checkPasswordMatch("ValidPass1!", "")
        );
        assertEquals("Passwords do not match.", exception.getMessage());
    }

    // Edge case for null values
    @Test
    void testCheckPasswordMatch_NullPasswords() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> passwordValidator.checkPasswordMatch("ValidPass1!", null)
        );
        assertEquals("Passwords do not match.", exception.getMessage());
    }

    @Test
    void testCheckPasswordMatch_BothNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> passwordValidator.checkPasswordMatch(null, null)
        );
        assertEquals("Passwords do not match.", exception.getMessage());
    }
}

