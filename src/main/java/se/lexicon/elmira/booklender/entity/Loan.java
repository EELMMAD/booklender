package se.lexicon.elmira.booklender.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long loanId;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE,CascadeType.ALL})
    private LibraryUser loanTaker;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE,CascadeType.ALL})
    private Book book;

    private LocalDate loanDate;
    private boolean terminated;


    public Loan() {
    }

    public Loan(LibraryUser loanTaker, Book book, LocalDate loanDate, boolean isTerminated) {
        this.loanTaker = loanTaker;
        this.book=book;
        this.loanDate = loanDate;
        this.terminated = isTerminated;
    }

    public long getLoanId() {
        return loanId;
    }

    public LibraryUser getLoanTaker() {
        return loanTaker;
    }

    public void setLoanTaker(LibraryUser loanTaker) {
        this.loanTaker = loanTaker;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isOverdue(){
        return LocalDate.now().isAfter(loanDate.plusDays(book.getMaxLoanDays()));
    }

    public BigDecimal getFine(){
        if(isTerminated())
            return BigDecimal.ZERO;

        if(LocalDate.now().isBefore(getLoanDate().plusDays(book.getMaxLoanDays())))
        return BigDecimal.ZERO;

        LocalDate from = getLoanDate().plusDays(book.getMaxLoanDays());
        int numDays = Period.between(from, LocalDate.now()).getDays();
        BigDecimal diffDays = BigDecimal.valueOf(numDays);
        return book.getFinePerDay().multiply(diffDays);
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

    public boolean extendLoan(int days) throws RuntimeException{
        if (days <= book.getMaxLoanDays() && days > 0){
            if (!book.isReserved() && !isOverdue()){
                setLoanDate(getLoanDate().plusDays(days));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return loanId == loan.loanId &&
                terminated == loan.terminated &&
                Objects.equals(loanTaker, loan.loanTaker) &&
                Objects.equals(book, loan.book) &&
                Objects.equals(loanDate, loan.loanDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanId, loanTaker, book, loanDate, terminated);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanId=" + loanId +
                ", loanTaker=" + loanTaker +
                ", book=" + book +
                ", loanDate=" + loanDate +
                ", expired=" + terminated +
                '}';
    }
}
