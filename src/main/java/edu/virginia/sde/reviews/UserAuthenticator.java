package edu.virginia.sde.reviews;

import java.sql.SQLException;
import java.util.Optional;

public class UserAuthenticator {

    public void authenticateUser(String username, String password) throws SQLException {
        DbDriver dbDriver = new DbDriver();
        try {
            dbDriver.connect();
            Optional<String> optionalPassword = dbDriver.getPasswordFromUsername(username);
            int userId = dbDriver.getUserIdFromUsername(username);
            UserSession.getInstance(userId, username);
            dbDriver.commit();
            dbDriver.disconnect();
            if (optionalPassword.isEmpty()) {
                throw new IllegalArgumentException("Username not recognized.");
            }
            if (!optionalPassword.get().equals(password)) {
                throw new IllegalArgumentException("Username or password is incorrect.");
            }
        } catch (SQLException e) {
            throw e;
        }
    }
}
