package se.lexicon.elmira.booklender.repositoriesData;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.elmira.booklender.entity.Loan;

import java.util.List;

public interface LoanRepo extends CrudRepository<Loan, Long> {

    List<Loan> findAllByBook_BookId(Integer bookId);

    List<Loan> findAllByLoanTaker_UserId(Integer userId);

    List<Loan> findAllByTerminated(boolean expiredStatus);

    List<Loan> findAll();

    Loan findByLoanId(long loanId);
}