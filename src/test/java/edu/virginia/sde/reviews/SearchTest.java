package edu.virginia.sde.reviews;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {
    private Search defaultSearch;
    private Search customSearch;

    @BeforeEach
    void setUp() {
        // Initialize the default search object
        defaultSearch = new Search();

        // Initialize the custom search object
        customSearch = new Search("CS", "3140", "Advanced Java Programming");
    }

    @Test
    void testDefaultConstructor() {
        assertEquals("", defaultSearch.getSubject());
        assertEquals("", defaultSearch.getNumber());
        assertEquals("", defaultSearch.getTitle());
    }

    @Test
    void testCustomConstructor() {
        assertEquals("CS", customSearch.getSubject());
        assertEquals("3140", customSearch.getNumber());
        assertEquals("Advanced Java Programming", customSearch.getTitle());
    }

    @Test
    void testGettersAndSetters() {
        // Test getters and setters for the subject field
        defaultSearch.setSubject("MATH");
        assertEquals("MATH", defaultSearch.getSubject());

        // Test getters and setters for the number field
        defaultSearch.setNumber("1010");
        assertEquals("1010", defaultSearch.getNumber());

        // Test getters and setters for the title field
        defaultSearch.setTitle("Calculus I");
        assertEquals("Calculus I", defaultSearch.getTitle());
    }
}

