package edu.virginia.sde.reviews;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CourseTest {
    private Course course;

    @BeforeEach
    public void setUp() {
        course = new Course(101, "CS", 3140, "Advanced Software Development");
    }

    @Test
    public void testGetCourseId() {
        assertEquals(101, course.getCourseId());
    }

    @Test
    public void testGetSubjectMnemonic() {
        assertEquals("CS", course.getSubjectMnemonic());
    }

    @Test
    public void testGetCourseNumber() {
        assertEquals(3140, course.getCourseNumber());
    }

    @Test
    public void testGetCourseTitle() {
        assertEquals("Advanced Software Development", course.getCourseTitle());
    }

    @Test
    public void testSetCourseId() {
        course.setCourseId(202);
        assertEquals(202, course.getCourseId());
    }

    @Test
    public void testSetSubjectMnemonic() {
        course.setSubjectMnemonic("MATH");
        assertEquals("MATH", course.getSubjectMnemonic());
    }

    @Test
    public void testSetCourseNumber() {
        course.setCourseNumber(1010);
        assertEquals(1010, course.getCourseNumber());
    }

    @Test
    public void testSetCourseTitle() {
        course.setCourseTitle("Discrete Mathematics");
        assertEquals("Discrete Mathematics", course.getCourseTitle());
    }

    @Test
    public void testToString() {
        assertEquals("CS 3140 - Advanced Software Development", course.toString());

        // Update course attributes and check again
        course.setCourseId(202);
        course.setSubjectMnemonic("MATH");
        course.setCourseNumber(1010);
        course.setCourseTitle("Discrete Mathematics");

        assertEquals("MATH 1010 - Discrete Mathematics", course.toString());
    }
}