package se.lexicon.elmira.booklender.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.elmira.booklender.dto.LibraryUserDto;
import se.lexicon.elmira.booklender.entity.LibraryUser;
import se.lexicon.elmira.booklender.repositoriesData.LibraryUserRepo;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LibraryUserServiceImplTest {
    LibraryUserServiceImpl testObject;

    LibraryUser firstLibraryUser;
    LibraryUser secondLibraryUser;
    LibraryUserDto firstUserDto;
    LibraryUserDto secondLibraryUserDto;

    @Autowired
    LibraryUserRepo libraryUserRepository;

    @BeforeEach
    void setUp() {
        testObject = new LibraryUserServiceImpl(libraryUserRepository);

        firstLibraryUser = new LibraryUser(LocalDate.now().minusYears(10), "Elmira", "elmiramadado@gmail.com");
        libraryUserRepository.save(firstLibraryUser);
        firstUserDto = new LibraryUserDto(firstLibraryUser);

        secondLibraryUser = libraryUserRepository.save(new LibraryUser(LocalDate.now().minusYears(20), "Lena", "lenasadr@gmail.com"));
        secondLibraryUserDto = new LibraryUserDto(secondLibraryUser);
    }

    @Test
    void should_created() {
        assertNotNull(firstLibraryUser);
        assertNotNull(secondLibraryUser);
        assertNotNull(firstUserDto);
        assertNotNull(secondLibraryUserDto);
    }

    @Test
    void should_find_by_id() {
        assertEquals(firstUserDto, testObject.findById(firstLibraryUser.getUserId()));
        assertEquals(secondLibraryUserDto, testObject.findById(secondLibraryUser.getUserId()));
    }

    @Test
    void should_find_by_email() {
        assertEquals(firstUserDto, testObject.findByEmail("ElmiRamaDaDo@gmail.com"));
        assertEquals(secondLibraryUserDto, testObject.findByEmail("lEnaSadr@gmail.com"));
    }

    @Test
    void should_find_all() {
        assertEquals(2, testObject.findAll().size());
        assertTrue(testObject.findAll().contains(firstUserDto));
        assertTrue(testObject.findAll().contains(secondLibraryUserDto));
    }

    @Test
    void should_create_libraryUserDto() {
        LibraryUserDto thirdLibraryUserDto = new LibraryUserDto();
        thirdLibraryUserDto.setName("Madeline");
        thirdLibraryUserDto.setEmail("Madeline@gmail.com");
        thirdLibraryUserDto.setUserId(888);
        thirdLibraryUserDto.setRegDate(LocalDate.now());

        thirdLibraryUserDto = testObject.create(thirdLibraryUserDto);

        assertEquals(3, testObject.findAll().size());
        assertEquals("Madeline@gmail.com", testObject.findById(thirdLibraryUserDto.getUserId()).getEmail());
        assertEquals(thirdLibraryUserDto, testObject.findById(thirdLibraryUserDto.getUserId()));
    }

    @Test
    void should_update_libraryUserDto() {
        secondLibraryUserDto.setName("My name");
        secondLibraryUserDto.setEmail("myname@gmail.com");
        testObject.update(secondLibraryUserDto);

        assertEquals(secondLibraryUserDto, testObject.findById(secondLibraryUserDto.getUserId()));
        assertEquals("myname@gmail.com", testObject.findById(secondLibraryUserDto.getUserId()).getEmail());
        assertEquals("My name", testObject.findById(secondLibraryUserDto.getUserId()).getName());
    }

    @Test
    void should_delete() {
        assertTrue(testObject.delete(firstUserDto.getUserId()));
        assertEquals(1, testObject.findAll().size());

        assertTrue(testObject.findAll().contains(secondLibraryUserDto));
        assertFalse(testObject.findAll().contains(firstUserDto));
    }

}