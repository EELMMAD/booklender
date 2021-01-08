package se.lexicon.elmira.booklender.dto;

import se.lexicon.elmira.booklender.entity.Loan;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoanDto {
    @Null(message = "Loan id is necessary")
    private Long loanId;

    @NotNull(message = "Loan taker is necessary")
    private LibraryUserDto loanTaker;

    @NotNull(message = "Loan book is not set")
    private BookDto book;

    @PastOrPresent(message = "The loan date should be in the present or in the past.")
    private LocalDate loanDate;

    private boolean terminated;

    public LoanDto() {
    }

    public LoanDto(long loanId, LibraryUserDto loanTaker, BookDto book, LocalDate loanDate, boolean isTerminated) {
        this.loanId = loanId;
        this.loanTaker = loanTaker;
        this.book = book;
        this.loanDate = loanDate;
        this.terminated = isTerminated;
    }

    public LoanDto(Loan entity){
        setLoanId(entity.getLoanId());
        setLoanTaker(new LibraryUserDto(entity.getLoanTaker()));
        setBook(new BookDto(entity.getBook()));
        setLoanDate(entity.getLoanDate());
    }

    public static List<LoanDto> toLoanDtos(List<Loan> loans) {
        List<LoanDto> result = new ArrayList<>();
        for (Loan loan : loans) {
            LoanDto loanDto = new LoanDto(loan);
            result.add(loanDto);
        }
        return result;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public LibraryUserDto getLoanTaker() {
        return loanTaker;
    }

    public void setLoanTaker(LibraryUserDto loanTaker) {
        this.loanTaker = loanTaker;
    }

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public boolean isTerminated() {
        return terminated;
    }

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanDto loanDto = (LoanDto) o;
        return loanId == loanDto.loanId &&
                terminated == loanDto.terminated &&
                Objects.equals(loanTaker, loanDto.loanTaker) &&
                Objects.equals(book, loanDto.book) &&
                Objects.equals(loanDate, loanDto.loanDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanId, loanTaker, book, loanDate, terminated);
    }

    @Override
    public String toString() {
        return "LoanDto{" +
                "loanId=" + loanId +
                ", loanTaker=" + loanTaker +
                ", book=" + book +
                ", loanDate=" + loanDate +
                ", expired=" + terminated +
                '}';
    }
}

