package ru.maconconsulting.library.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.maconconsulting.library.models.MaconUser;
import ru.maconconsulting.library.models.Role;
import ru.maconconsulting.library.repositories.MaconUsersRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ru.maconconsulting.library.util.MaconUsersTestData.*;

@SpringBootTest
@AutoConfigureMockMvc
class MaconUsersServiceTest {

    @InjectMocks
    private MaconUsersService maconUsersService;

    @Mock
    private MaconUsersRepository maconUsersRepository;

//    Necessary for save and update tests
    @Mock
    private  PasswordEncoder passwordEncoder;

    @Test
    void findAll() {
        List<MaconUser> expectedUsers = List.of(USER, MANAGER, ADMIN);
        Mockito.when(maconUsersRepository.findAll()).thenReturn(expectedUsers);
        List<MaconUser> actualUsers = maconUsersService.findAllSorted();

        Mockito.verify(maconUsersRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(actualUsers);
        Assertions.assertEquals(expectedUsers.size(), actualUsers.size());
        Assertions.assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void findByEmail() {
        String userEmail = USER.getEmail();
        Mockito.when(maconUsersRepository.findByEmail(userEmail)).thenReturn(Optional.of(USER));
        Optional<MaconUser> actualUser = maconUsersService.findByEmail(userEmail);

        Mockito.verify(maconUsersRepository, Mockito.times(1)).findByEmail(userEmail);
        Assertions.assertNotNull(actualUser.orElse(null));
        Assertions.assertEquals(USER, actualUser.orElse(null));
    }

    @Test
    void findByLogin() {
        String userLogin = USER.getLogin();
        Mockito.when(maconUsersRepository.findByLogin(userLogin)).thenReturn(Optional.of(USER));
        MaconUser actualUser = maconUsersService.findByLogin(userLogin).orElse(null);

        Mockito.verify(maconUsersRepository, Mockito.times(1)).findByLogin(userLogin);
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(USER, actualUser);
    }

    @Test
    void save() {
        MaconUser newUser = new MaconUser("New user", "new", "new@mail.com", "new", Role.ROLE_USER);
        Mockito.when(maconUsersRepository.save(newUser)).thenReturn(newUser);

        maconUsersService.save(newUser);
        Mockito.verify(maconUsersRepository, Mockito.times(1)).save(newUser);
    }

    @Test
    void update() {
        MaconUser updatedUser = USER;
        String userLogin = updatedUser.getLogin();
        USER.setId(30);
        USER.setCreatedAt(LocalDateTime.now());
        USER.setEmail("user_update@mail.com");
        Mockito.when(maconUsersRepository.findByLogin(userLogin)).thenReturn(Optional.of(USER));
        Mockito.when(maconUsersRepository.findByEmail("user_update@mail.com")).thenReturn(Optional.of(updatedUser));
        maconUsersService.update(userLogin, updatedUser);

        Mockito.verify(maconUsersRepository, Mockito.times(1)).save(updatedUser);
        Assertions.assertNotNull(maconUsersService.findByEmail("user_update@mail.com").orElse(null));
        Assertions.assertNull(maconUsersService.findByEmail("user@mail.com").orElse(null));
    }

    @Test
    void delete() {
        String userLogin = USER.getLogin();
        maconUsersService.delete(userLogin);

        Mockito.verify(maconUsersRepository, Mockito.times(1)).deleteByLogin(userLogin);
        Assertions.assertNull(maconUsersService.findByLogin(userLogin).orElse(null));
    }
}