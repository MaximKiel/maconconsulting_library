package ru.maconconsulting.library.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.maconconsulting.library.models.MaconUser;
import ru.maconconsulting.library.repositories.MaconUsersRepository;

import java.util.List;
import java.util.Optional;

import static ru.maconconsulting.library.util.users.MaconUsersTestData.*;

@SpringBootTest
@AutoConfigureMockMvc
class MaconUsersServiceTest {

    @InjectMocks
    private MaconUsersService maconUsersService;

    @Mock
    private MaconUsersRepository maconUsersRepository;

    @Test
    void findAll() {
        List<MaconUser> expectedUsers = List.of(USER, MANAGER, ADMIN);
        Mockito.when(maconUsersRepository.findAll()).thenReturn(expectedUsers);

        List<MaconUser> actualUsers = maconUsersService.findAll();

        Assertions.assertNotNull(actualUsers);
        Assertions.assertEquals(expectedUsers.size(), actualUsers.size());
        Assertions.assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void findByEmail() {
        String userEmail = USER.getEmail();
        Mockito.when(maconUsersRepository.findByEmail(userEmail)).thenReturn(Optional.of(USER));

        Optional<MaconUser> actualUser = maconUsersService.findByEmail(userEmail);

        Assertions.assertNotNull(actualUser.get());
        Assertions.assertEquals(USER, actualUser.get());
    }

    @Test
    void findByLogin() {
        String userLogin = USER.getLogin();
        Mockito.when(maconUsersRepository.findByLogin(userLogin)).thenReturn(Optional.of(USER));

        MaconUser actualUser = maconUsersService.findByLogin(userLogin).get();

        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(USER, actualUser);
    }

    @Test
    void save() {
//        MaconUser newUser = new MaconUser("New user", "new", "new@mail.com", "new", Role.ROLE_USER);
//        Mockito.when(maconUsersRepository.save(newUser)).thenReturn(newUser);
//        maconUsersService.save(newUser);
//
//        MaconUser actualUser = maconUsersService.findByLogin("new").get();
//
//        Assertions.assertNotNull(actualUser);
//        Assertions.assertEquals(newUser, actualUser);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}