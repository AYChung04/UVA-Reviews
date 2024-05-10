package edu.virginia.sde.reviews;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchAddCourseTest {
    private SearchAddCourse searchAddCourse;

    @BeforeEach
    void setUp() {
        searchAddCourse = new SearchAddCourse("CS", 3140, "Advanced Java Programming");
    }

    @Test
    void testConstructor() {
        assertEquals("CS", searchAddCourse.getSubjectInput());
        assertEquals(3140, searchAddCourse.getNumberInput());
        assertEquals("Advanced Java Programming", searchAddCourse.getTitleInput());
    }

    @Test
    void testGettersAndSetters() {
        // Test subjectInput field
        searchAddCourse.setSubjectInput("MATH");
        assertEquals("MATH", searchAddCourse.getSubjectInput());

        // Test numberInput field
        searchAddCourse.setNumberInput(1010);
        assertEquals(1010, searchAddCourse.getNumberInput());

        // Test titleInput field
        searchAddCourse.setTitleInput("Calculus I");
        assertEquals("Calculus I", searchAddCourse.getTitleInput());
    }
}
