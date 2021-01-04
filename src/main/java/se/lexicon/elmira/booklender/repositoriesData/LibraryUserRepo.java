package se.lexicon.elmira.booklender.repositoriesData;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.elmira.booklender.entity.LibraryUser;

import java.util.List;

public interface LibraryUserRepo extends CrudRepository<LibraryUser, Integer> {
    List<LibraryUser> findAll();

    LibraryUser findByEmailIgnoreCase(String email);

    LibraryUser findByUserId(int userId);
}