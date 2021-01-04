package se.lexicon.elmira.booklender.repositoriesData;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.elmira.booklender.entity.Book;

import java.util.List;

public interface BookRepo extends CrudRepository<Book, Integer> {

    Book findByBookId(int bookId);

    List<Book> findAllByReserved(boolean ReservedStatus);

    List<Book> findAllByTitleContainingIgnoreCase(String title);

    List<Book> findAllByAvailable(boolean availableStatus);

    List<Book> findAll();
}