package se.lexicon.elmira.booklender.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    Book testObject;
    @Test
    void testEquals() {
        Book copy = new Book("Dictionary",5, BigDecimal.valueOf(600),"reference source");
        assertTrue(testObject.equals(copy));
    }

    @Test
    void testToString() {
        String toString = testObject.toString();

        assertTrue(toString.contains(Integer.toString(testObject.getBookId())));
        assertTrue(toString.contains(testObject.getTitle()));
        assertTrue(toString.contains(testObject.getDescription()));
        assertTrue(toString.contains(testObject.getFinePerDay().toString()));
    }

    @BeforeEach
    void setUp() {
        testObject = new Book("Dictionary",5, BigDecimal.valueOf(600),"reference source");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void successfully_created(){
        assertNotNull(testObject);
        assertTrue(testObject.getBookId() == 0);
        assertEquals("Dictionary", testObject.getTitle());
        assertEquals("reference source", testObject.getDescription());
        assertEquals(BigDecimal.valueOf(600), testObject.getFinePerDay());
    }

    @Test
    void testHashcode() {
        Book copy = new Book("Dictionary",5, BigDecimal.valueOf(600),"reference source");
        assertEquals(copy.hashCode(), testObject.hashCode());
    }

}