package se.lexicon.elmira.booklender.repositoriesData;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.elmira.booklender.entity.Book;
import se.lexicon.elmira.booklender.entity.LibraryUser;
import se.lexicon.elmira.booklender.entity.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LoanRepositoryTest {
    Book firstBook;
    Book secondBook;
    Book thirdBook;

    LibraryUser firstUser;
    LibraryUser secondUser;
    LibraryUser thirdUser;

    Loan firstLoan;
    Loan secondLoan;
    Loan thirdLoan;

    @Autowired
    BookRepo bookRepository;

    @Autowired
    LibraryUserRepo libraryUserRepository;

    @Autowired
    LoanRepo loanRepository;

    @BeforeEach
    void setUp() {
        firstBook = new Book("FirstBook", 3, BigDecimal.valueOf(300), "Description1");
        secondBook = bookRepository.save(new Book("SecondBook", 5, BigDecimal.valueOf(200), "Description2"));
        thirdBook = bookRepository.save(new Book("ThirdBook", 5, BigDecimal.valueOf(200), "Description3"));

        firstUser = libraryUserRepository.save(new LibraryUser(LocalDate.now(), "Elmira", "elmiramadadi@gmail.com"));
        secondUser = libraryUserRepository.save(new LibraryUser(LocalDate.now(), "Lena", "lenasadr@gmail.com"));
        thirdUser = libraryUserRepository.save(new LibraryUser(LocalDate.now(), "Ali", "Ali@gmail.com"));

        firstLoan = loanRepository.save(new Loan(firstUser, firstBook, LocalDate.now(), false));
        secondLoan = loanRepository.save(new Loan(secondUser, secondBook, LocalDate.now().minusDays(secondBook.getMaxLoanDays() + 1), true));
        thirdLoan = loanRepository.save(new Loan(thirdUser, thirdBook, LocalDate.now().minusDays(thirdBook.getMaxLoanDays() + 1), true));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAllByBook_BookId() {
        List<Loan> foundLoans = loanRepository.findAllByBook_BookId(firstLoan.getBook().getBookId());
        assertEquals(1, foundLoans.size());
        assertTrue(firstLoan.equals(foundLoans.get(0)));
    }

    @Test
    void findAllByLoanTaker_UserId() {
        List<Loan> foundLoans = loanRepository.findAllByLoanTaker_UserId(firstLoan.getLoanTaker().getUserId());
        assertEquals(1, foundLoans.size());
        assertTrue(firstLoan.equals(foundLoans.get(0)));
    }

    @Test
    void findAllByTerminated() {
        List<Loan> foundLoans = loanRepository.findAllByTerminated(true);
        assertEquals(2, foundLoans.size());
        assertTrue(secondLoan.equals(foundLoans.get(0)) || thirdLoan.equals(foundLoans.get(0)));
    }

    @Test
    void findAll() {
        assertEquals(3, loanRepository.findAll().size());
    }

    @Test
    void findByLoanId() {
        Loan found = loanRepository.findByLoanId(firstLoan.getLoanId());
        assertTrue(firstLoan.equals(found));
    }
}