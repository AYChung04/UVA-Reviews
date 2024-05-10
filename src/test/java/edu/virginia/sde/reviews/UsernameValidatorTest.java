package edu.virginia.sde.reviews;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UsernameValidatorTest {
    private UsernameValidator usernameValidator;
    private DbDriver mockDbDriver;

    @BeforeEach
    public void setUp() {
        mockDbDriver = Mockito.mock(DbDriver.class);
        usernameValidator = new UsernameValidator() {
            @Override
            protected DbDriver createDbDriver() {
                return mockDbDriver;
            }
        };
    }

    @Test
    public void testValidateUsernameUnique() throws SQLException {
        when(mockDbDriver.getUsernames()).thenReturn(new ArrayList<>());
        doNothing().when(mockDbDriver).connect();
        doNothing().when(mockDbDriver).commit();
        doNothing().when(mockDbDriver).disconnect();

        assertDoesNotThrow(() -> usernameValidator.validateUsername("newUser"));

        verify(mockDbDriver).connect();
        verify(mockDbDriver).commit();
        verify(mockDbDriver).disconnect();
    }

    @Test
    public void testValidateUsernameExists() throws SQLException {
        List<String> usernames = new ArrayList<>();
        usernames.add("existingUser");
        when(mockDbDriver.getUsernames()).thenReturn(usernames);
        doNothing().when(mockDbDriver).connect();
        doNothing().when(mockDbDriver).commit();
        doNothing().when(mockDbDriver).disconnect();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usernameValidator.validateUsername("existingUser");
        });
        assertEquals("Username already exists.", exception.getMessage());

        verify(mockDbDriver).connect();
        verify(mockDbDriver).commit();
        verify(mockDbDriver).disconnect();
    }

    @Test
    public void testValidateUsernameSqlException() throws SQLException {
        doThrow(new SQLException("Connection error")).when(mockDbDriver).connect();

        SQLException exception = assertThrows(SQLException.class, () -> {
            usernameValidator.validateUsername("newUser");
        });
        assertEquals("Connection error", exception.getMessage());

        verify(mockDbDriver).connect();
        verify(mockDbDriver, never()).getUsernames();
        verify(mockDbDriver, never()).commit();
        verify(mockDbDriver, never()).disconnect();
    }

    @Test
    public void testValidateUsernameEmptyString() throws SQLException {
        when(mockDbDriver.getUsernames()).thenReturn(new ArrayList<>());
        doNothing().when(mockDbDriver).connect();
        doNothing().when(mockDbDriver).commit();
        doNothing().when(mockDbDriver).disconnect();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usernameValidator.validateUsername("");
        });
        assertEquals("Username cannot be empty.", exception.getMessage());
    }

    @Test
    public void testValidateUsernameNullString() throws SQLException {
        when(mockDbDriver.getUsernames()).thenReturn(new ArrayList<>());
        doNothing().when(mockDbDriver).connect();
        doNothing().when(mockDbDriver).commit();
        doNothing().when(mockDbDriver).disconnect();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usernameValidator.validateUsername(null);
        });
        assertEquals("Username cannot be null.", exception.getMessage());
    }
}

