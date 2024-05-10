package edu.virginia.sde.reviews;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CourseSessionTest {
    @BeforeEach
    public void setUp() {
        // Clear the session before each test
        CourseSession.clearSession();
    }

    @Test
    public void testSingletonInstanceCreation() {
        // Create the singleton instance
        CourseSession session1 = CourseSession.getInstance(101, "CS", 3140, "Advanced Software Development");

        // Verify the instance properties
        assertEquals(101, session1.getCourseId());
        assertEquals("CS", session1.getSubjectMnemonic());
        assertEquals(3140, session1.getCourseNumber());
        assertEquals("Advanced Software Development", session1.getCourseTitle());

        // Retrieve the singleton instance again and verify that it's the same instance
        CourseSession session2 = CourseSession.getInstance();
        assertSame(session1, session2);
    }

    @Test
    public void testSingletonInstanceUpdate() {
        // Create the singleton instance
        CourseSession session1 = CourseSession.getInstance(101, "CS", 3140, "Advanced Software Development");

        // Update the singleton instance
        CourseSession session2 = CourseSession.getInstance(202, "MATH", 1010, "Discrete Mathematics");

        // Verify that both instances are the same
        assertSame(session1, session2);

        // Verify the updated properties
        assertEquals(202, session2.getCourseId());
        assertEquals("MATH", session2.getSubjectMnemonic());
        assertEquals(1010, session2.getCourseNumber());
        assertEquals("Discrete Mathematics", session2.getCourseTitle());
    }

    @Test
    public void testGetters() {
        // Create the singleton instance
        CourseSession session = CourseSession.getInstance(303, "ENGR", 2100, "Engineering Fundamentals");

        // Verify each getter method
        assertEquals(303, session.getCourseId());
        assertEquals("ENGR", session.getSubjectMnemonic());
        assertEquals(2100, session.getCourseNumber());
        assertEquals("Engineering Fundamentals", session.getCourseTitle());
    }

    @Test
    public void testClearSession() {
        // Create the singleton instance
        CourseSession.getInstance(101, "CS", 3140, "Advanced Software Development");

        // Clear the session
        CourseSession.clearSession();

        // Verify that the session is cleared (instance is null)
        assertNull(CourseSession.getInstance());
    }
}
