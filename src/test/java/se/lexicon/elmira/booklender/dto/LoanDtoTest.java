package se.lexicon.elmira.booklender.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lexicon.elmira.booklender.entity.Book;
import se.lexicon.elmira.booklender.entity.LibraryUser;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

class LoanDtoTest {
    BookDto firstBookDto;
    BookDto secondBookDto;
    LibraryUserDto firstLibraryUserDto;
    LibraryUserDto secondLibraryUserDto;
    LoanDto loanDto;

    @BeforeEach
    void setUp() {
        Book firstBook = new Book("firstBook", 5, BigDecimal.valueOf(6), "Description 1");
        Book secondBook = new Book("secondBook", 2, BigDecimal.valueOf(4), "Description 2");
        firstBookDto = new BookDto(firstBook);
        secondBookDto = new BookDto(secondBook);

        LibraryUser firstLibraryUser = new LibraryUser(LocalDate.now(),"Elmira","elmiraMadadi@gmail.com");
        LibraryUser secondLibraryUser = new LibraryUser(LocalDate.now().minusYears(1),"Lena","lenaSadr@gmail.com");

        firstLibraryUserDto = new LibraryUserDto(firstLibraryUser);
        secondLibraryUserDto = new LibraryUserDto(secondLibraryUser);
        loanDto = new LoanDto(1,firstLibraryUserDto,firstBookDto,LocalDate.now(),false);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void creating_new_LoanDto_object() {
        assertEquals(firstBookDto.getBookId(), loanDto.getBook().getBookId());
        assertEquals(firstBookDto.getTitle(), loanDto.getBook().getTitle());
        assertEquals(firstBookDto.getDescription(), loanDto.getBook().getDescription());
        assertEquals(firstBookDto.getMaxLoanDays(), loanDto.getBook().getMaxLoanDays());
        assertEquals(firstBookDto.getFinePerDay(), loanDto.getBook().getFinePerDay());
        assertEquals(firstLibraryUserDto.getUserId(), loanDto.getLoanTaker().getUserId());
        assertEquals(firstLibraryUserDto.getName(), loanDto.getLoanTaker().getName());
        assertEquals(firstLibraryUserDto.getEmail(), loanDto.getLoanTaker().getEmail());
        assertEquals(firstLibraryUserDto.getRegDate(), loanDto.getLoanTaker().getRegDate());
        assertEquals(1, loanDto.getLoanId());
    }
}