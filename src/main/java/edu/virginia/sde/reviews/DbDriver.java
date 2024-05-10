package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbDriver {

    private Connection connection;
    private static final String URL = "jdbc:sqlite:reviews.sqlite";

    public void connect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            throw new IllegalStateException("The connection is already opened");
        }
        connection = DriverManager.getConnection(URL);
        //the next line enables foreign key enforcement - do not delete/comment out
        connection.createStatement().execute("PRAGMA foreign_keys = ON");
        //the next line disables auto-commit - do not delete/comment out
        connection.setAutoCommit(false);
    }

    /**
     * Commit all changes since the connection was opened OR since the last commit/rollback
     */
    public void commit() throws SQLException {
        connection.commit();
    }

    /**
     * Rollback to the last commit, or when the connection was opened
     */
    public void rollback() throws SQLException {
        connection.rollback();
    }

    /**
     * Ends the connection to the database
     */
    public void disconnect() throws SQLException {
        connection.close();
    }

    public void createNewTables() {
        String sqlUsers = "CREATE TABLE IF NOT EXISTS users ("
                + "	user_id integer PRIMARY KEY AUTOINCREMENT,"
                + "	username text NOT NULL UNIQUE,"
                + "	password text NOT NULL"
                + ");";

        String sqlCourses = "CREATE TABLE IF NOT EXISTS courses ("
                + "	course_id integer PRIMARY KEY AUTOINCREMENT,"
                + "	subject_mnemonic text NOT NULL,"
                + "	course_number integer NOT NULL,"
                + "	course_title text NOT NULL"
                + ");";

        String sqlReviews = "CREATE TABLE IF NOT EXISTS reviews ("
                + "	review_id integer PRIMARY KEY AUTOINCREMENT,"
                + "	user_id integer NOT NULL,"
                + "	course_id integer NOT NULL,"
                + "	rating integer NOT NULL,"
                + "	comment text,"
                + "	timestamp text NOT NULL,"
                + "	FOREIGN KEY (user_id) REFERENCES users (user_id),"
                + "	FOREIGN KEY (course_id) REFERENCES courses (course_id)"
                + ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sqlUsers);
            stmt.execute(sqlCourses);
            stmt.execute(sqlReviews);
            System.out.println("Tables created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public boolean registerUser(String username, String password) {
        String sql = "INSERT INTO users(username, password) VALUES(?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            commit();
            return true;
        } catch (SQLException e) {
            System.out.println("Registration error: " + e.getMessage());
            return false;
        }
    }

    public List<Course> searchBySubject(String subject) {
        String sql = "SELECT * FROM courses WHERE subject_mnemonic LIKE ? ";
        List<Course> courses = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, "%" + subject + "%");
            ResultSet rs = pstmt.executeQuery();

            System.out.println(rs);

            while (rs.next()) {
                courses.add(new Course(rs.getInt("course_id"), rs.getString("subject_mnemonic"),
                        rs.getInt("course_number"), rs.getString("course_title")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courses;
    }

    public List<Course> searchByTitle(String title) {
        String sql = "SELECT * FROM courses WHERE course_title LIKE ?";
        List<Course> courses = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, "%" + title + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                courses.add(new Course(rs.getInt("course_id"), rs.getString("subject_mnemonic"),
                        rs.getInt("course_number"), rs.getString("course_title")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courses;
    }

    public List<Course> searchByNumber(int number) {
        String sql = "SELECT * FROM courses WHERE course_number = ?";
        List<Course> courses = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, number);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                courses.add(new Course(rs.getInt("course_id"), rs.getString("subject_mnemonic"),
                        rs.getInt("course_number"), rs.getString("course_title")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courses;
    }

    public List<Course> getAllCourses() {
        String sql = "SELECT * FROM courses";
        List<Course> courses = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                courses.add(new Course(rs.getInt("course_id"), rs.getString("subject_mnemonic"),
                        rs.getInt("course_number"), rs.getString("course_title")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courses;
    }

    public List<Course> getCourseFromSearch(Search search) {
        String subject = search.getSubject();
        String title = search.getTitle();
        String number = search.getNumber();

        String sql = "SELECT * FROM courses WHERE " +
                "(subject_mnemonic LIKE ? OR ? IS NULL) AND " +
                "(CAST(course_number AS STRING) LIKE ? OR ? IS NULL) AND " +
                "(course_title LIKE ? OR ? IS NULL)";

        List<Course> courses = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, subject != null && !subject.isEmpty() ? "%" + subject + "%" : null);
            pstmt.setString(2, subject != null && !subject.isEmpty() ? "%" + subject + "%" : null);
            pstmt.setString(3, number != null && !number.isEmpty() ? "%" + number + "%" : null);
            pstmt.setString(4, number != null && !number.isEmpty() ? "%" + number + "%" : null);
            pstmt.setString(5, title != null && !title.isEmpty() ? "%" + title + "%" : null);
            pstmt.setString(6, title != null && !title.isEmpty() ? "%" + title + "%" : null);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                courses.add(new Course(rs.getInt("course_id"), rs.getString("subject_mnemonic"),
                        rs.getInt("course_number"), rs.getString("course_title")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courses;
    }


    protected void addCourseToDatabase(String subjectMnemonic, int courseNumber, String courseTitle) {
        String sql = "INSERT INTO courses(subject_mnemonic, course_number, course_title) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, subjectMnemonic);
            pstmt.setInt(2, courseNumber);
            pstmt.setString(3, courseTitle);
            pstmt.executeUpdate();
            commit();
        } catch (SQLException e) {
            System.out.println("Error adding course: " + e.getMessage());
        }
    }

    public boolean hasReviewed(int userId, int courseId) {
        String sql = "SELECT 1 FROM reviews WHERE user_id = ? AND course_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, courseId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // returns true if there is at least one row, which means review exists
            }
        } catch (SQLException e) {
            System.out.println("Database check for review failed: " + e.getMessage());
            return false;
        }
    }

    public void submitReview(int userId, int courseId, int rating, String comment) {
        String sql = "INSERT INTO reviews (user_id, course_id, rating, comment, timestamp) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, courseId);
            pstmt.setInt(3, rating);
            pstmt.setString(4, comment);
            pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
            commit();
        } catch (SQLException e) {
            System.out.println("Error submitting review: " + e.getMessage());
        }
    }

    //Stubs to implement
    //Gets the list of current existing usernames
    public List<String> getUsernames() {
        List<String> usernames = new ArrayList<>();
        String sql = "SELECT username FROM users";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                usernames.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving usernames: " + e.getMessage());
        }
        return usernames;
    }

    //I moved authenticate into its own class to follow MVC
    //Returns the password of the given username, returns Optional.empty if it doesn't exist
    public Optional<String> getPasswordFromUsername(String username) {
        String sql = "SELECT password FROM users WHERE username = ?";
        try (
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return Optional.of(storedPassword);
            }
            return Optional.empty();
        } catch (SQLException e) {
            System.out.println("Authentication error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public boolean deleteReview(int userId, int courseId) {
        String sql = "DELETE FROM reviews WHERE user_id = ? AND course_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, courseId);
            int affectedRows = pstmt.executeUpdate();
            commit();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting review: " + e.getMessage());
            return false;
        }
    }
    public boolean updateReview(int userId, int courseId, int rating, String comment) {
        String sql = "UPDATE reviews SET rating = ?, comment = ?, timestamp = ? WHERE user_id = ? AND course_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, rating);
            pstmt.setString(2, comment);
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(4, userId);
            pstmt.setInt(5, courseId);
            int affectedRows = pstmt.executeUpdate();
            commit();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating review: " + e.getMessage());
            return false;
        }
    }
    public double getAverageRating(int courseId) {
        String sql = "SELECT AVG(rating) FROM reviews WHERE course_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Error calculating average rating: " + e.getMessage());
        }
        return 0;
    }


    public boolean courseExists(String subject, int number, String title) throws SQLException {
        String sql = "SELECT 1 FROM courses WHERE subject_mnemonic = ? AND course_number = ? AND course_title = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, subject);
            pstmt.setInt(2, number);
            pstmt.setString(3, title);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if a course exists
        }
    }

    public List<Review> getReviewsByCourseId(int courseId) {
        String sql = "SELECT rating, timestamp, comment FROM reviews WHERE course_id = ?";
        List<Review> reviews = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                reviews.add(new Review(rs.getInt("rating"), rs.getString("timestamp"), rs.getString("comment")));
            }
        } catch (SQLException e) {
            System.out.println("Error loading reviews: " + e.getMessage());
        }
        return reviews;
    }

    public int getUserIdFromUsername(String username) throws SQLException {
        String sql = "SELECT user_id FROM users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_id");
            } else {
                return -1; // Return -1 if the user is not found
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user ID: " + e.getMessage());
            throw e;
        }
    }

    public ObservableList<Review> getReviewsByUserId(int userId) throws SQLException {
        ObservableList<Review> reviews = FXCollections.observableArrayList();
        String sql = "SELECT r.rating, r.timestamp, r.comment, c.subject_mnemonic, c.course_number, c.course_title, r.course_id " +
                "FROM reviews r JOIN courses c ON r.course_id = c.course_id WHERE r.user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String mnemonic = rs.getString("subject_mnemonic");
                int number = rs.getInt("course_number");
                String title = rs.getString("course_title");
                int id = rs.getInt("course_id");
                reviews.add(new Review(rs.getInt("rating"), rs.getString("timestamp"), rs.getString("comment"), new Course(id, mnemonic, number, title)));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user reviews: " + e.getMessage());
            throw e;
        }
        return reviews;
    }

    public Review getReviewByUserIdAndCourseId(int userId, int courseId) throws SQLException {
        String sql = "SELECT r.rating, r.timestamp, r.comment, c.course_id, c.subject_mnemonic, c.course_number, c.course_title " +
                "FROM reviews r JOIN courses c ON r.course_id = c.course_id WHERE r.user_id = ? AND r.course_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, courseId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Course course = new Course(
                        rs.getInt("course_id"),
                        rs.getString("subject_mnemonic"),
                        rs.getInt("course_number"),
                        rs.getString("course_title")
                );
                return new Review(
                        rs.getInt("rating"),
                        rs.getString("timestamp"),
                        rs.getString("comment"),
                        course
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching review: " + e.getMessage());
            throw e;
        }
    }

}
