package se.lexicon.elmira.booklender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import se.lexicon.elmira.booklender.dto.LibraryUserDto;
import se.lexicon.elmira.booklender.entity.LibraryUser;
import se.lexicon.elmira.booklender.repositoriesData.LibraryUserRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Configurable
public class LibraryUserServiceImpl implements LibraryUserService {

    LibraryUserRepo libraryUserRepo;

    @Autowired
         public LibraryUserServiceImpl(LibraryUserRepo libraryUserRepo) {
            this.libraryUserRepo = libraryUserRepo;
        }


    protected LibraryUser getLibraryUser(LibraryUserDto libraryUserDto){

        return new LibraryUser(libraryUserDto.getRegDate(), libraryUserDto.getName(), libraryUserDto.getEmail());
    }

    @Override
    public LibraryUserDto findById(int userId) throws RuntimeException {
        if (!libraryUserRepo.existsById(userId))
            throw new RuntimeException("There is no library user with id: " + userId);
        LibraryUser libraryUser = libraryUserRepo.findByUserId(userId);
        return new LibraryUserDto(libraryUser);
    }

    @Override
    public LibraryUserDto findByEmail(String email) throws IllegalArgumentException{
        if (email == null)
            throw new IllegalArgumentException("Email is necessary");
        if (libraryUserRepo.findByEmailIgnoreCase(email) == null)
            throw new IllegalArgumentException(email + " do not match with any user");
        LibraryUser libraryUser = libraryUserRepo.findByEmailIgnoreCase(email);
        return new LibraryUserDto(libraryUser);
    }

    @Override
    public List<LibraryUserDto> findAll() {
        List<LibraryUser> foundItems = libraryUserRepo.findAll();
        return LibraryUserDto.toLibraryUserDtos(foundItems);
    }

    @Override
    @Transactional
    public LibraryUserDto create(LibraryUserDto libraryUserDto) throws RuntimeException {
        if (libraryUserRepo.existsById(libraryUserDto.getUserId()))
            throw new RuntimeException("Duplication exist in library user");
        LibraryUser toCreate = new LibraryUser(libraryUserDto.getRegDate(), libraryUserDto.getName(), libraryUserDto.getEmail());
        return new LibraryUserDto(libraryUserRepo.save(toCreate));
    }

    @Override
    @Transactional
    public LibraryUserDto update(LibraryUserDto libraryUserDto) throws RuntimeException {
        if (!libraryUserRepo.existsById(libraryUserDto.getUserId()))
            throw new RuntimeException("Library user does not exist");
        LibraryUser user = libraryUserRepo.findById(libraryUserDto.getUserId()).orElseThrow(
                () -> new RuntimeException("User does not exist")
        );
        if (!user.getName().equals(libraryUserDto.getName()))
            user.setName(libraryUserDto.getName());
        if (!user.getEmail().equalsIgnoreCase(libraryUserDto.getEmail()))
            user.setEmail(libraryUserDto.getEmail());
        if (user.getRegDate() != libraryUserDto.getRegDate())
            user.setRegDate(libraryUserDto.getRegDate());
        return new LibraryUserDto(libraryUserRepo.save(user));
    }

    @Override
    @Transactional
    public boolean delete(int userId) throws IllegalArgumentException{
        if (!libraryUserRepo.findById(userId).isPresent())
            throw new IllegalArgumentException("User with " + userId + "does not exist");
        boolean deleted = false;
        if (libraryUserRepo.existsById(userId)){
            libraryUserRepo.delete(libraryUserRepo.findById(userId).get());
            deleted = true;
        }
        return deleted;
    }
}
