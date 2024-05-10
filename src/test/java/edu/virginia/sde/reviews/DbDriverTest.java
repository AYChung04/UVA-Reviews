package edu.virginia.sde.reviews;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DbDriverTest {

    private DbDriver dbDriver;

    @BeforeAll
    public void setUpBeforeAll() throws SQLException {
        dbDriver = new DbDriver();
        dbDriver.connect();
        dbDriver.createNewTables();
    }

    @BeforeEach
    public void setUp() throws SQLException {
        dbDriver.commit();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        dbDriver.rollback();
    }

    @AfterAll
    public void tearDownAfterAll() throws SQLException {
        dbDriver.disconnect();
    }

    @Test
    public void testConnect() {
        // Disconnect first to ensure a clean state
        assertDoesNotThrow(() -> {
            if (dbDriver != null) {
                dbDriver.disconnect();
            }
        });

        assertDoesNotThrow(() -> dbDriver.connect(), "Should not throw any exception while connecting");
    }

    @Test
    public void testCreateNewTables() {
        assertDoesNotThrow(() -> dbDriver.createNewTables(), "Should not throw any exception while creating tables");
    }

    @Test
    public void testSearchBySubject() {
        dbDriver.addCourseToDatabase("CS", 3140, "Software Development Essentials");
        List<Course> courses = dbDriver.searchBySubject("CS");
        assertFalse(courses.isEmpty(), "Courses should be found by subject");
        assertEquals("CS", courses.get(0).getSubjectMnemonic(), "Subject mnemonic should match the search term");
    }

    @Test
    public void testSearchByTitle() {
        dbDriver.addCourseToDatabase("CS", 3140, "Software Development Essentials");
        List<Course> courses = dbDriver.searchByTitle("Software");
        assertFalse(courses.isEmpty(), "Courses should be found by title");
        assertTrue(courses.get(0).getCourseTitle().contains("Software"), "Course title should contain the search term");
    }

    @Test
    public void testSearchByNumber() {
        dbDriver.addCourseToDatabase("CS", 3140, "Software Development Essentials");
        List<Course> courses = dbDriver.searchByNumber(3140);
        assertFalse(courses.isEmpty(), "Courses should be found by course number");
        assertEquals(3140, courses.get(0).getCourseNumber(), "Course number should match the search number");
    }

    @Test
    public void testGetAllCourses() {
        dbDriver.addCourseToDatabase("CS", 3140, "Software Development Essentials");
        List<Course> courses = dbDriver.getAllCourses();
        assertFalse(courses.isEmpty(), "There should be at least one course");
    }

    @Test
    public void testSubmitReview() throws SQLException {
        dbDriver.registerUser("submit_user", "password3");
        dbDriver.addCourseToDatabase("CS", 3140, "Software Development Essentials");
        int userId = dbDriver.getUserIdFromUsername("submit_user");

        assertDoesNotThrow(() -> dbDriver.submitReview(userId, 3140, 5, "Great course!"), "Should not throw any exception");
    }

    @Test
    public void testGetUsernames() {
        dbDriver.registerUser("user7", "password8");
        List<String> usernames = dbDriver.getUsernames();
        assertTrue(usernames.contains("user7"), "The list should contain the registered username");
    }

    @Test
    public void testGetUserIdFromUsername() throws SQLException {
        dbDriver.registerUser("user10", "password10");
        int userId = dbDriver.getUserIdFromUsername("user10");
        assertNotEquals(-1, userId, "User ID should not be -1 for a registered user");
    }
}