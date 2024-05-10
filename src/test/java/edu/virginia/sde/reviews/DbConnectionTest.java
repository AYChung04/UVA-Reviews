package edu.virginia.sde.reviews;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class DbConnectionTest {

    @Test
    public void testConnectReturnsValidConnection() {
        // Attempt to establish a connection
        Connection connection = DbConnection.connect();
        // Assert that the connection is not null
        assertNotNull(connection, "The database connection should not be null");
    }

    @Test
    public void testConnectionIsValid() {
        // Attempt to establish a connection
        try (Connection connection = DbConnection.connect()) {
            // Check if the connection is valid
            assertTrue(connection.isValid(2), "The connection should be valid");
        } catch (Exception e) {
            fail("Exception should not occur: " + e.getMessage());
        }
    }
}
