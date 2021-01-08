package se.lexicon.elmira.booklender.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lexicon.elmira.booklender.entity.Book;
import se.lexicon.elmira.booklender.entity.LibraryUser;
import se.lexicon.elmira.booklender.entity.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookDtoTest {
Book firstBook;
Book secondBook;
BookDto firstBookDto;
BookDto secondBookDto;
List<Book> bookList;

    @BeforeEach
    void setUp() {
        firstBook = new Book("firstBook", 5, BigDecimal.valueOf(300), "Description 1");
        secondBook = new Book("SecondBook", 3, BigDecimal.valueOf(100), "Description 2");
        firstBookDto = new BookDto(firstBook);
        secondBookDto = new BookDto(secondBook);
        bookList = new ArrayList<>();
        bookList.add(firstBook);
        bookList.add(secondBook);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void convert_book_to_bookDto() {
        assertEquals( firstBook.getTitle(), firstBookDto.getTitle());
        assertEquals( firstBook.getBookId(), firstBookDto.getBookId());
        assertEquals( firstBook.getDescription(), firstBookDto.getDescription());
        assertEquals( firstBook.getMaxLoanDays(), firstBookDto.getMaxLoanDays());
        assertEquals(firstBook.getFinePerDay(), firstBookDto.getFinePerDay());
    }

    @Test
    void covert_book_list_to_bookDto_list() {
        secondBook = new Book("secondBook", 3, BigDecimal.valueOf(200), "Description 2");
        List<Book> bookList = new ArrayList<>();
        bookList.add(firstBook);
        bookList.add(secondBook);

        List<BookDto> bookDtoList = BookDto.toBookDtos(bookList);
        assertEquals(bookList.size(), bookDtoList.size());
    }
}