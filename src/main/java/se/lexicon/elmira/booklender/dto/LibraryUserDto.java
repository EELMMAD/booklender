package se.lexicon.elmira.booklender.dto;

import se.lexicon.elmira.booklender.entity.LibraryUser;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LibraryUserDto {
    public static final String EMAIL_REGEX_PATTERN = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    @Null(message = "User id is necessary.")
    int userId;

    @PastOrPresent(message = "The register date should be in the present or in the past.")
    LocalDate regDate;

    @NotBlank(message = "Name is necessary")
    @Size(min = 2)
    private String name;

    @NotBlank(message = "Email is necessary")
    @Email(regexp = EMAIL_REGEX_PATTERN,
            message = "Email is not acceptable")
    private String email;

    public LibraryUserDto() {
    }

    public LibraryUserDto(int userId, LocalDate regDate, String name, String email) {
        this.userId = userId;
        this.regDate = regDate;
        this.name = name;
        this.email = email;
    }

    public LibraryUserDto(LibraryUser entity){
        setUserId(entity.getUserId());
        setRegDate(entity.getRegDate());
        setName(entity.getName());
        setEmail(entity.getEmail());
    }

    public static List<LibraryUserDto> toLibraryUserDtos(List<LibraryUser> users) {
        List<LibraryUserDto> result = new ArrayList<>();
        for (LibraryUser user : users){
            LibraryUserDto libraryUserDto = new LibraryUserDto(user);
            result.add(libraryUserDto);
        }
        return result;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibraryUserDto that = (LibraryUserDto) o;
        return userId == that.userId &&
                Objects.equals(regDate, that.regDate) &&
                Objects.equals(name, that.name) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, regDate, name, email);
    }

    @Override
    public String toString() {
        return "LibraryUserDto{" +
                "userId=" + userId +
                ", regDate=" + regDate +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
