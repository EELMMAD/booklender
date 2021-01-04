package se.lexicon.elmira.booklender.repositoriesData;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.elmira.booklender.entity.Book;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepoTest {
    Book firstBook;
    Book secondBook;

    @Autowired
    BookRepo bookRepository;

    @BeforeEach
    void setUp() {
        firstBook = new Book("firstBook", 5, BigDecimal.valueOf(300), "Description 1");
        firstBook.setReserved(true);
        firstBook.setAvailable(true);
        bookRepository.save(firstBook);
        secondBook = bookRepository.save(new Book("secondBook", 6, BigDecimal.valueOf(200), "Description 2"));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByBookId() {
        Book foundBook = bookRepository.findByBookId(firstBook.getBookId());
        assertEquals(firstBook.getBookId(), foundBook.getBookId());
        assertEquals(firstBook.getDescription(), foundBook.getDescription());
        assertEquals(firstBook.getTitle(), foundBook.getTitle());
        assertEquals(firstBook.getMaxLoanDays(), foundBook.getMaxLoanDays());
        assertEquals(firstBook.isReserved(), foundBook.isReserved());
        assertEquals(firstBook.isAvailable(), foundBook.isAvailable());
        assertTrue(firstBook.equals(foundBook));
    }

    @Test
    void findAllByReserved() {
        List<Book> foundBooks = bookRepository.findAllByReserved(true);
        assertEquals(1, foundBooks.size());
        assertTrue(firstBook.equals(foundBooks.get(0)));
    }

    @Test
    void findAllByTitleContainingIgnoreCase() {
        List<Book> foundBooks = bookRepository.findAllByTitleContainingIgnoreCase("firstBook");
        assertEquals(1, foundBooks.size());
        assertTrue(firstBook.equals(foundBooks.get(0)));
    }

    @Test
    void findAllByAvailable() {
        List<Book> foundBooks = bookRepository.findAllByAvailable(true);
        assertEquals(1, foundBooks.size());
        assertTrue(firstBook.equals(foundBooks.get(0)));
    }

    @Test
    void findAll() {
        assertEquals(2, bookRepository.findAll().size());
    }
}
