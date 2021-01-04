package se.lexicon.elmira.booklender.repositoriesData;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.elmira.booklender.entity.LibraryUser;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LibraryUserRepositoryTest {

    LibraryUser firstUser;
    LibraryUser secondUser;
    LibraryUser thirdUser;

    @Autowired
    LibraryUserRepo libraryUserRepository;

    @BeforeEach
    void setUp() {
        firstUser = libraryUserRepository.save(new LibraryUser(LocalDate.now(), "Elmira", "elmiramadadi@gmail.com"));
        secondUser = libraryUserRepository.save(new LibraryUser(LocalDate.now(), "Lena", "lenasadr@gmail.com"));
        thirdUser = libraryUserRepository.save(new LibraryUser(LocalDate.now(), "Ali", "Ali@gmail.com"));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        assertEquals(3, libraryUserRepository.findAll().size());
    }

    @Test
    void findByEmailIgnoreCase() {
        LibraryUser foundUser1 = libraryUserRepository.findByEmailIgnoreCase("elmIraMadadi@gmail.com");
        assertEquals(firstUser.getUserId(), foundUser1.getUserId());
        assertEquals(firstUser.getName(), foundUser1.getName());
        assertEquals(firstUser.getEmail(), foundUser1.getEmail());

        LibraryUser foundUser2 = libraryUserRepository.findByEmailIgnoreCase("lenasadr@gmail.com");
        assertEquals(secondUser.getUserId(), foundUser2.getUserId());
        assertEquals(secondUser.getName(), foundUser2.getName());
        assertEquals(secondUser.getEmail(), foundUser2.getEmail());

        LibraryUser foundUser3 = libraryUserRepository.findByEmailIgnoreCase("Ali@gmail.com");
        assertEquals(thirdUser.getUserId(), foundUser3.getUserId());
        assertEquals(thirdUser.getName(), foundUser3.getName());
        assertEquals(thirdUser.getEmail(), foundUser3.getEmail());
    }

    @Test
    void findByUserId() {
        assertEquals(firstUser.getUserId(), libraryUserRepository.findByUserId(firstUser.getUserId()).getUserId());
        assertEquals(firstUser.getName(), libraryUserRepository.findByUserId(firstUser.getUserId()).getName());
        assertEquals(firstUser.getEmail(), libraryUserRepository.findByUserId(firstUser.getUserId()).getEmail());

        assertEquals(secondUser.getUserId(), libraryUserRepository.findByUserId(secondUser.getUserId()).getUserId());
        assertEquals(secondUser.getName(), libraryUserRepository.findByUserId(secondUser.getUserId()).getName());
        assertEquals(secondUser.getEmail(), libraryUserRepository.findByUserId(secondUser.getUserId()).getEmail());

        assertEquals(thirdUser.getUserId(), libraryUserRepository.findByUserId(thirdUser.getUserId()).getUserId());
        assertEquals(thirdUser.getName(), libraryUserRepository.findByUserId(thirdUser.getUserId()).getName());
        assertEquals(thirdUser.getEmail(), libraryUserRepository.findByUserId(thirdUser.getUserId()).getEmail());
    }
}