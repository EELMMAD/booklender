package se.lexicon.elmira.booklender.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LoanTest {
    LibraryUser testObject;
    Book book;
    Loan loanTaker;

    @BeforeEach
    void setUp() {
        book = new Book("Dictionary", 5, BigDecimal.valueOf(600), "reference source");
        book.setMaxLoanDays(3);
        testObject = new LibraryUser(LocalDate.of(2021, 1, 1), "Elmira", "elmiramadadi@gmail.com");

        loanTaker = new Loan(testObject, book, LocalDate.of(2021, 1, 3), true);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void if_days_less_than_bookMaxLoanDays_extend_loan() {
        Loan test = new Loan(testObject, book, LocalDate.now().minusDays(book.getMaxLoanDays() - 1), false);
        LocalDate prevLoanDate = test.getLoanDate();
        assertTrue(test.extendLoan(book.getMaxLoanDays() - 1));
        assertEquals(prevLoanDate.plusDays(book.getMaxLoanDays() - 1), test.getLoanDate());
    }

    @Test
    void if_days_more_than_bookMaxLoanDays_extend_loan() {
        Loan test = new Loan(testObject, book, LocalDate.now(), false);
        assertFalse(loanTaker.extendLoan(book.getMaxLoanDays() + 1));
    }

    @Test
    void getFine_with_isTerminated_True() {
        assertEquals(BigDecimal.ZERO, loanTaker.getFine());
    }

    @Test
    void getFine_with_days_Before_expiration_date() {
        Loan xyz = new Loan(testObject, book, LocalDate.of(2021, 1, 1), false);
        assertEquals(BigDecimal.ZERO, xyz.getFine());
    }

    @Test
    void extendLoan() {
    }

    @Test
    void created_loanTaker() {
        assertNotNull(loanTaker);
        assertTrue(loanTaker.getLoanId() == 0);
        assertEquals(LocalDate.of(2021, 1, 3), loanTaker.getLoanDate());
    }

    @Test
    void return_zero_if_loan_is_terminated() {
        Loan test = new Loan(testObject, book, LocalDate.now(), true);
        assertEquals(BigDecimal.ZERO, test.getFine());
    }

    @Test
    void return_zero_if_it_is_not_expired() {
        Loan test = new Loan(testObject, book, LocalDate.now(), false);
        assertEquals(BigDecimal.ZERO, test.getFine());
    }

    @Test
    void return_fine_if_lone_is_expired() {
        Loan test = new Loan(testObject, book, LocalDate.now().minusDays(4), false);
        assertEquals(book.getFinePerDay(), test.getFine());
    }

    @Test
    void return_fine_if_expired_loan_is_more_than_one() {
        Loan test = new Loan(testObject, book, LocalDate.now().minusDays(5), false);
        assertEquals(book.getFinePerDay().multiply(BigDecimal.valueOf(2)), test.getFine());
    }

    @Test
    void extend_with_negative_num_should_return_false() {
        Loan test = new Loan(testObject, book, LocalDate.now(), true);
        assertFalse(test.extendLoan(-2));
    }
}