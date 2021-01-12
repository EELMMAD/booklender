package se.lexicon.elmira.booklender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import se.lexicon.elmira.booklender.dto.BookDto;
import se.lexicon.elmira.booklender.dto.LibraryUserDto;
import se.lexicon.elmira.booklender.dto.LoanDto;
import se.lexicon.elmira.booklender.entity.Book;
import se.lexicon.elmira.booklender.entity.LibraryUser;
import se.lexicon.elmira.booklender.entity.Loan;
import se.lexicon.elmira.booklender.repositoriesData.BookRepo;
import se.lexicon.elmira.booklender.repositoriesData.LibraryUserRepo;
import se.lexicon.elmira.booklender.repositoriesData.LoanRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Configurable
public class LoanServiceImpl implements LoanService {

    LoanRepo loanRepository;
    LibraryUserRepo libraryUserRepository;
    BookRepo bookRepository;

    @Autowired
    public LoanServiceImpl(LoanRepo loanRepo, LibraryUserRepo libraryUserRepo, BookRepo bookRepo) {
        this.loanRepository = loanRepo;
        this.libraryUserRepository = libraryUserRepo;
        this.bookRepository = bookRepo;
    }

    @Transactional
    public LibraryUser getLibraryUser(LibraryUserDto libraryUserDto) {

        return libraryUserRepository.findByUserId(libraryUserDto.getUserId());
    }

    @Transactional
    public Book getBook(BookDto bookDto) {
        return bookRepository.findById(bookDto.getBookId()).orElseThrow(() -> new IllegalArgumentException("Book does not exist"));
    }

    @Override
    public LoanDto findById(long loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException(
                loanId + " id does not match with and Loan"));
        return new LoanDto(loan);
    }

    @Override
    public List<LoanDto> findByBookId(int bookId) {
        List<Loan> foundItems = loanRepository.findAllByBook_BookId(bookId);
        return LoanDto.toLoanDtos(foundItems);
    }

    @Override
    public List<LoanDto> findByUserId(int userId) {
        List<Loan> foundItems = loanRepository.findAllByLoanTaker_UserId(userId);
        return LoanDto.toLoanDtos(foundItems);
    }

    @Override
    public List<LoanDto> findByTerminated(boolean terminated) {
        List<Loan> foundItems = loanRepository.findAllByTerminated(terminated);
        return LoanDto.toLoanDtos(foundItems);
    }

    @Override
    public List<LoanDto> findAll() {
        List<Loan> all = loanRepository.findAll();
        return LoanDto.toLoanDtos(all);
    }

    @Override
    @Transactional
    public LoanDto create(LoanDto loanDto) throws RuntimeException {
        if (loanRepository.existsById(loanDto.getLoanId()))
            throw new RuntimeException("Already exists");
        Loan loan = new Loan(getLibraryUser(loanDto.getLoanTaker()),
                getBook(loanDto.getBook()),
                loanDto.getLoanDate(),
                loanDto.isTerminated());

        return new LoanDto(loanRepository.save(loan));
    }

    @Override
    @Transactional
    public LoanDto update(LoanDto loanDto) throws RuntimeException {
        if (!loanRepository.existsById(loanDto.getLoanId()))
            throw new RuntimeException("Loan was not found");
        Loan loan = loanRepository.findById(loanDto.getLoanId()).get();
        if (!loan.getBook().equals(getBook(loanDto.getBook())))
            loan.setBook(getBook(loanDto.getBook()));
        if (loan.getLoanDate() != loanDto.getLoanDate())
            loan.setLoanDate(loanDto.getLoanDate());
        if (!loan.getLoanTaker().equals(getLibraryUser(loanDto.getLoanTaker())))
            loan.setLoanTaker(getLibraryUser(loanDto.getLoanTaker()));

        return new LoanDto(loanRepository.save(loan));
    }

    @Override
    @Transactional
    public boolean delete(int bookId) throws RuntimeException {
        boolean deleted = false;

        if (loanRepository.findAllByBook_BookId(bookId).isEmpty()) {
            throw new RuntimeException("Could not find any book with " + bookId + "id");
        } else {
            List<Loan> foundItems = loanRepository.findAllByBook_BookId(bookId);
            for (Loan l : foundItems) {
                loanRepository.delete(l);
                deleted = true;
            }
        }
        return deleted;
    }
}

