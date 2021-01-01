package se.lexicon.elmira.booklender.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LibraryUserTest {

    LibraryUser testObject;
    @Test
    void testEquals() {
        LibraryUser copy = new LibraryUser(LocalDate.of(2021,1,1), "Elmira", "elmiramadadi@gmail.com");
        assertTrue(testObject.equals(copy));
    }

    @Test
    void testToString() {
        String toString = testObject.toString();

        assertTrue(toString.contains(Integer.toString(testObject.getUserId())));
        assertTrue(toString.contains(testObject.getName()));
        assertTrue(toString.contains(testObject.getEmail()));
    }

    @BeforeEach
    void setUp() {
        testObject = new LibraryUser(LocalDate.of(2021,1,1), "Elmira", "elmiramadadi@gmail.com");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void successfully_created(){
        assertNotNull(testObject);
        assertTrue(testObject.getUserId() == 0);
        assertEquals("Elmira", testObject.getName());
        assertEquals("elmiramadadi@gmail.com", testObject.getEmail());
    }

    @Test
    void testHashcode() {
        LibraryUser copy = new LibraryUser(LocalDate.of(2021,1,1), "Elmira", "elmiramadadi@gmail.com");
        assertEquals(copy.hashCode(), testObject.hashCode());
    }


}