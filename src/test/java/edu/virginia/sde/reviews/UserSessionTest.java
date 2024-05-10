package edu.virginia.sde.reviews;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserSessionTest {

    @BeforeEach
    void clearSession() {
        UserSession.clearSession();
    }

    @Test
    void testSingletonInstanceInitialization() {
        UserSession session1 = UserSession.getInstance(1, "testUser");
        assertNotNull(session1);
        assertEquals(1, session1.getUserId());
        assertEquals("testUser", session1.getUsername());

        UserSession session2 = UserSession.getInstance(2, "anotherUser");
        assertSame(session1, session2); // Should be the same instance
        assertEquals(1, session2.getUserId()); // Values should remain the same
        assertEquals("testUser", session2.getUsername());
    }

    @Test
    void testGetExistingInstance() {
        UserSession session1 = UserSession.getInstance(1, "testUser");
        UserSession session2 = UserSession.getInstance();
        assertSame(session1, session2);
    }

    @Test
    void testClearSession() {
        UserSession session1 = UserSession.getInstance(1, "testUser");
        assertNotNull(session1);

        UserSession.clearSession();
        UserSession session2 = UserSession.getInstance();
        assertNull(session2);
    }
}
