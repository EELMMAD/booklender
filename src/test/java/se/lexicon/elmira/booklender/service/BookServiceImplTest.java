package se.lexicon.elmira.booklender.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.elmira.booklender.dto.BookDto;
import se.lexicon.elmira.booklender.entity.Book;
import se.lexicon.elmira.booklender.repositoriesData.BookRepo;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookServiceImplTest {
    BookServiceImpl testObject;

    Book firstBook;
    Book secondBook;

    BookDto firstBookDto = new BookDto();
    BookDto secondBookDto = new BookDto();

    @Autowired
    BookRepo bookRepo;

    @BeforeEach
    void setUp() {
        testObject = new BookServiceImpl(bookRepo);

        firstBook = new Book("First Book", 30, BigDecimal.valueOf(5), "Java");
        bookRepo.save(firstBook);

        secondBook = bookRepo.save(new Book("Second Book", 30, BigDecimal.valueOf(9), "React"));

        secondBookDto = new BookDto(secondBook);
        firstBookDto = new BookDto(firstBook);
    }

    @Test
    void should_created() {
        assertNotNull(firstBook);
        assertNotNull(firstBookDto);
        assertNotNull(secondBook);
        assertNotNull(secondBookDto);
    }

    @Test
    void should_find_by_reserved() {
        assertEquals(2, testObject.findByReserved(false).size());
        assertEquals(0, testObject.findByReserved(true).size());

        firstBook.setReserved(true);

        assertTrue(testObject.findByReserved(true).contains(new BookDto(firstBook)));
        assertFalse(testObject.findByReserved(true).contains(new BookDto(secondBook)));
    }

    @Test
    void should_find_by_title() {
        String firstTitle = "first book";
        String secondTitle = "sEcOnD bOoK";

        assertEquals(1, testObject.findByTitle(firstTitle).size());
        assertEquals(1, testObject.findByTitle(secondTitle).size());
        assertTrue(testObject.findByTitle(firstTitle).contains(firstBookDto));
        assertFalse(testObject.findByTitle(firstTitle).contains(secondBookDto));
        assertFalse(testObject.findByTitle(secondTitle).contains(firstBookDto));
        assertTrue(testObject.findByTitle(secondTitle).contains(secondBookDto));
    }

    @Test
    void should_find_by_available() {
        assertEquals(2, testObject.findByAvailable(false).size());
        assertEquals(0, testObject.findByAvailable(true).size());

        secondBookDto.setAvailable(true);
        secondBookDto = testObject.update(secondBookDto);
        assertEquals(1, testObject.findByAvailable(true).size());
        assertEquals(1, testObject.findByAvailable(false).size());
        assertTrue( testObject.findByAvailable(true).contains(secondBookDto));
        assertFalse( testObject.findByAvailable(true).contains(firstBookDto));
    }

    @Test
    void should_find_by_Id() {
        assertEquals(firstBookDto, testObject.findById(firstBookDto.getBookId()));
        assertEquals(secondBookDto, testObject.findById(secondBookDto.getBookId()));
    }

    @Test
    void should_find_All() {
        assertEquals(2, testObject.findAll().size());
        assertTrue(testObject.findAll().contains(firstBookDto));
        assertTrue(testObject.findAll().contains(secondBookDto));
    }

    @Test
    void should_create_bookDto() {
        BookDto thirdBookDto = new BookDto();
        thirdBookDto.setTitle("Literature");
        thirdBookDto.setBookId(1010);
        thirdBookDto.setMaxLoanDays(15);
        thirdBookDto.setFinePerDay(BigDecimal.valueOf(8));
        thirdBookDto.setDescription("English");
        thirdBookDto = testObject.create(thirdBookDto);

        assertEquals(3, testObject.findAll().size());
        assertTrue(testObject.findAll().contains(thirdBookDto));
        assertEquals(3, thirdBookDto.getBookId());
        assertTrue(testObject.findAll().contains(thirdBookDto));
    }

    @Test
    void should_update_bookDto() {
        firstBookDto.setTitle("Java For Dummies");
        firstBookDto.setDescription("Worlds most popular programming.");
        testObject.update(firstBookDto);

        assertEquals("Java For Dummies", testObject.findById(firstBook.getBookId()).getTitle());
        assertEquals("Worlds most popular programming.", testObject.findById(firstBookDto.getBookId()).getDescription());
    }

    @Test
    void should_delete() {
        assertTrue(testObject.delete(firstBook.getBookId()));
        assertFalse(testObject.findAll().contains(firstBookDto));
        assertEquals(1, testObject.findAll().size());
        assertTrue(testObject.findAll().contains(secondBookDto));
    }
}