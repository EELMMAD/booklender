package se.lexicon.elmira.booklender.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lexicon.elmira.booklender.entity.LibraryUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LibraryUserDtoTest {


    LibraryUser firstLibraryUser;
    LibraryUser secondLibraryUser2;
    List<LibraryUser> libraryUsers;
    LibraryUserDto libraryUserDto;

    @BeforeEach
    void setUp() {
        firstLibraryUser = new LibraryUser(LocalDate.now(), "Elmira", "elmiramadadi@gmail.com");
        secondLibraryUser2 = new LibraryUser(LocalDate.now().minusYears(1), "Lena", "LenaSadr@gmail.com");
        libraryUserDto = new LibraryUserDto(firstLibraryUser);
        libraryUsers = new ArrayList<>();
        libraryUsers.add(firstLibraryUser);
        libraryUsers.add(secondLibraryUser2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void converts_List_of_books_to_List_of_bookDtos() {
        List<LibraryUserDto> libraryUserDtos = LibraryUserDto.toLibraryUserDtos(libraryUsers);
        assertEquals(libraryUsers.size(), libraryUserDtos.size());
    }

    @Test
    void convert_libraryUser_toLibraryUserDto() {
        assertEquals(firstLibraryUser.getUserId(), libraryUserDto.getUserId());
        assertEquals(firstLibraryUser.getName(), libraryUserDto.getName());
        assertEquals(firstLibraryUser.getEmail(), libraryUserDto.getEmail());
        assertEquals(firstLibraryUser.getRegDate(), libraryUserDto.getRegDate());
    }

}