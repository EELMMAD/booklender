package se.lexicon.elmira.booklender.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.elmira.booklender.dto.BookDto;
import se.lexicon.elmira.booklender.dto.LibraryUserDto;
import se.lexicon.elmira.booklender.dto.LoanDto;
import se.lexicon.elmira.booklender.entity.Book;
import se.lexicon.elmira.booklender.entity.LibraryUser;
import se.lexicon.elmira.booklender.entity.Loan;
import se.lexicon.elmira.booklender.repositoriesData.BookRepo;
import se.lexicon.elmira.booklender.repositoriesData.LibraryUserRepo;
import se.lexicon.elmira.booklender.repositoriesData.LoanRepo;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LoanServiceImplTest {
    LoanServiceImpl testObject;
    LibraryUserServiceImpl libraryUserService;
    BookServiceImpl bookService;

    @Autowired
    LoanRepo loanRepo;
    @Autowired
    BookRepo bookRepo;
    @Autowired
    LibraryUserRepo libraryUserRepo;

    Loan firstLoan;
    Loan secondLoan;
    LoanDto firstLoanDto;
    LoanDto secondLoanDto;
    Book firstBook;
    Book secondBook;
    BookDto firstBookDto;
    BookDto secondBookDto;
    LibraryUser firstUser;
    LibraryUser secondUser;
    LibraryUserDto firstLibraryUserDto;
    LibraryUserDto secondLibraryUserDto;


    @BeforeEach
    void setUp() {
        testObject = new LoanServiceImpl(loanRepo, libraryUserRepo, bookRepo);
        bookService = new BookServiceImpl(bookRepo);
        libraryUserService = new LibraryUserServiceImpl(libraryUserRepo);

        firstBook = new Book("My First Book", 30, BigDecimal.valueOf(10), "First Description");
        firstBook = bookRepo.save(firstBook);
        firstBookDto = new BookDto(firstBook);

        secondBook = new Book("My second Book", 30, BigDecimal.valueOf(10), "Second Description");
        secondBook = bookRepo.save(secondBook);
        secondBookDto = new BookDto(secondBook);

        firstUser = new LibraryUser(LocalDate.now(), "Elmira", "elmiramadadi@gmail.com");
        firstUser = libraryUserRepo.save(firstUser);
        firstLibraryUserDto = new LibraryUserDto(firstUser);

        secondUser = new LibraryUser(LocalDate.of(2020, 2, 2), "Lena", "Lenasadr@gmail.com");
        secondUser = libraryUserRepo.save(secondUser);
        secondLibraryUserDto = new LibraryUserDto(secondUser);

        firstLoan = new Loan(firstUser, firstBook, LocalDate.of(2020, 10, 9), false);
        firstLoan = loanRepo.save(firstLoan);
        firstLoanDto = new LoanDto(firstLoan);

        secondLoan = new Loan(secondUser, secondBook, LocalDate.of(2021, 2, 2), true);
        secondLoan = loanRepo.save(secondLoan);
        secondLoanDto = new LoanDto(secondLoan);
    }

    @Test
    void should_create() {
        assertNotNull(firstBook);
        assertNotNull(secondBook);
        assertNotNull(firstBookDto);
        assertNotNull(secondBookDto);
        assertNotNull(firstUser);
        assertNotNull(secondUser);
        assertNotNull(firstLibraryUserDto);
        assertNotNull(secondLibraryUserDto);
        assertNotNull(firstLoan);
        assertNotNull(secondLoan);
        assertNotNull(firstLoanDto);
        assertNotNull(secondLoanDto);
    }

    @Test
    void should_find_By_Id() {
        assertEquals(firstLoanDto, testObject.findById(firstLoanDto.getLoanId()));
        assertEquals(secondLoanDto, testObject.findById(secondLoanDto.getLoanId()));
    }

    @Test
    void should_find_by_bookId() {

        assertEquals(1, testObject.findByBookId(firstBookDto.getBookId()).size());
        assertTrue(testObject.findByBookId(firstBookDto.getBookId()).contains(firstLoanDto));
        assertFalse(testObject.findByBookId(firstBookDto.getBookId()).contains(secondLoanDto));

        assertEquals(1, testObject.findByBookId(secondBookDto.getBookId()).size());
        assertTrue(testObject.findByBookId(secondBookDto.getBookId()).contains(secondLoanDto));
        assertFalse(testObject.findByBookId(secondBookDto.getBookId()).contains(firstLoanDto));
    }

    @Test
    void shold_find_by_userId() {
        assertEquals(1, testObject.findByUserId(firstLoanDto.getLoanTaker().getUserId()).size());
        assertTrue(testObject.findByUserId(firstLoanDto.getLoanTaker().getUserId()).contains(firstLoanDto));
        assertFalse(testObject.findByUserId(firstLoanDto.getLoanTaker().getUserId()).contains(secondLoanDto));

        assertEquals(1, testObject.findByUserId(secondLoanDto.getLoanTaker().getUserId()).size());
        assertTrue(testObject.findByUserId(secondLoanDto.getLoanTaker().getUserId()).contains(secondLoanDto));
        assertFalse(testObject.findByUserId(secondLoanDto.getLoanTaker().getUserId()).contains(firstLoanDto));
    }

    @Test
    void shold_find_by_terminated() {

        assertEquals(1, testObject.findByTerminated(false).size());

        firstLoan.setTerminated(true);
        firstLoanDto = new LoanDto(firstLoan);
        assertEquals(0, testObject.findByTerminated(false).size());
        assertFalse(testObject.findByTerminated(false).contains(secondLoanDto));
        assertFalse(testObject.findByTerminated(false).contains(firstLoanDto));

        assertEquals(2, testObject.findByTerminated(true).size());
        assertTrue(testObject.findByTerminated(true).contains(firstLoanDto));
        assertTrue(testObject.findByTerminated(true).contains(secondLoanDto));
    }

    @Test
    void should_find_all() {
        assertEquals(2, testObject.findAll().size());
    }

    @Test
    void create() {
        LoanDto thirdLoanDto = new LoanDto();
        thirdLoanDto.setLoanTaker(firstLibraryUserDto);
        thirdLoanDto.setBook(secondBookDto);
        thirdLoanDto.setLoanId(222);
        thirdLoanDto.setLoanDate(LocalDate.of(2021, 1, 12));
        thirdLoanDto.setTerminated(true);

        thirdLoanDto = testObject.create(thirdLoanDto);

        assertEquals(3, testObject.findAll().size());
        assertTrue(testObject.findAll().contains(thirdLoanDto));
    }

    @Test
    void update() {
        secondLoanDto.setLoanDate(LocalDate.of(2016, 6, 8));
        secondLoanDto = testObject.update(secondLoanDto);
        assertEquals(LocalDate.of(2016, 6, 8), secondLoanDto.getLoanDate());

        firstLoanDto.setBook(new BookDto(secondBook));
        firstLoanDto = testObject.update(firstLoanDto);
        assertEquals(secondBookDto, firstLoanDto.getBook());

        secondLoanDto.setLoanTaker(firstLibraryUserDto);
        secondLoanDto = testObject.update(secondLoanDto);
        assertEquals(firstLibraryUserDto, secondLoanDto.getLoanTaker());
    }

    @Test
    void delete() {
        testObject.delete(firstLoanDto.getBook().getBookId());
        assertEquals(1, testObject.findAll().size());
        assertFalse(testObject.findAll().contains(firstLoanDto));
    }
}