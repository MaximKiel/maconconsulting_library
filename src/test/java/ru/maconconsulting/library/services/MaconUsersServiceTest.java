package ru.maconconsulting.library.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ru.maconconsulting.library.models.MaconUser;
import ru.maconconsulting.library.repositories.MaconUsersRepository;

import java.util.List;

import static ru.maconconsulting.library.util.users.MaconUsersTestData.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MaconUsersServiceTest {

    @Mock
    private MaconUsersRepository maconUsersRepository;

    @InjectMocks
    private MaconUsersService maconUsersService;

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
    }

    @Test
    void findByLogin() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}