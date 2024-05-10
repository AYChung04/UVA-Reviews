package edu.virginia.sde.reviews;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class SearchCourseTest {
    private SearchCourse searchCourse;
    private static final String DB_URL = "jdbc:sqlite:reviews.sqlite";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() throws SQLException {
        searchCourse = new SearchCourse();
        initializeDatabase();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() throws SQLException {
        clearDatabase();
        System.setOut(originalOut);
    }

    void initializeDatabase() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {
            String createTableSQL = """
                CREATE TABLE IF NOT EXISTS courses (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    subject TEXT NOT NULL,
                    number INTEGER NOT NULL,
                    title TEXT NOT NULL
                )
                """;
            statement.execute(createTableSQL);
        }
    }

    void clearDatabase() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {
            String clearTableSQL = "DELETE FROM courses";
            statement.execute(clearTableSQL);
        }
    }

    @Test
    void testAddCourseToDB_NewCourse() throws SQLException {
        SearchAddCourse newCourse = new SearchAddCourse("CS", 3140, "Advanced Java Programming");
        searchCourse.addCourseToDB(newCourse);

        assertTrue(isCourseInDatabase("CS", 3140, "Advanced Java Programming"));
        assertTrue(outContent.toString().contains("Course added successfully."));
    }

    @Test
    void testAddCourseToDB_DuplicateCourse() throws SQLException {
        // Adding the course first time
        SearchAddCourse newCourse = new SearchAddCourse("CS", 3140, "Advanced Java Programming");
        searchCourse.addCourseToDB(newCourse);

        // Clear output before the next call
        outContent.reset();

        // Adding the course second time
        searchCourse.addCourseToDB(newCourse);

        // Ensure that only one instance exists
        assertTrue(outContent.toString().contains("Course already exists in the database."));
    }

    boolean isCourseInDatabase(String subject, int number, String title) throws SQLException {
        DbDriver driver = new DbDriver();
        driver.connect();
        boolean exists = driver.courseExists(subject, number, title);
        driver.disconnect();
        return exists;
    }
}
