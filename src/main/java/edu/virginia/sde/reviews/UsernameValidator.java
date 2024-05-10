package edu.virginia.sde.reviews;

import java.sql.SQLException;
import java.util.List;

public class UsernameValidator {
    public void validateUsername(String username) throws SQLException {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null.");
        }
        if (username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }

        DbDriver dbDriver = createDbDriver();
        try {
            dbDriver.connect();
            List<String> existingUsernames = dbDriver.getUsernames();
            dbDriver.commit();
            dbDriver.disconnect();
            for (String s : existingUsernames) {
                if (username.equals(s)) {
                    throw new IllegalArgumentException("Username already exists.");
                }
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    // Factory method to create the DbDriver instance
    protected DbDriver createDbDriver() {
        return new DbDriver();
    }
}
