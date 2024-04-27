package ru.maconconsulting.library.services.parameters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.maconconsulting.library.models.parameters.Type;
import ru.maconconsulting.library.repositories.parameters.TypesRepository;

import java.util.List;
import java.util.Optional;

import static ru.maconconsulting.library.util.parameters.TypesTestData.*;

@SpringBootTest
@AutoConfigureMockMvc
class TypesServiceTest {

    @InjectMocks
    private TypesService typesService;

    @Mock
    private TypesRepository typesRepository;

    @Test
    void findAll() {
        List<Type> expectedTypes = List.of(TYPE_1, TYPE_2, TYPE_3);
        Mockito.when(typesRepository.findAll()).thenReturn(expectedTypes);
        List<Type> actualTypes = typesService.findAll();

        Mockito.verify(typesRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(actualTypes);
        Assertions.assertEquals(expectedTypes.size(), actualTypes.size());
        Assertions.assertEquals(expectedTypes, actualTypes);
    }

    @Test
    void findByName() {
        String name = TYPE_1.getName();
        Mockito.when(typesRepository. findByName(name)).thenReturn(Optional.of(TYPE_1));
        Optional<Type> actualTypes = typesService.findByName(name);

        Mockito.verify(typesRepository, Mockito.times(1)).findByName(name);
        Assertions.assertNotNull(actualTypes.orElse(null));
        Assertions.assertEquals(TYPE_1, actualTypes.orElse(null));
    }

    @Test
    void save() {
        Type newType = new Type("Новый тип");
        Mockito.when(typesRepository.save(newType)).thenReturn(newType);
        typesService.save(newType);

        Mockito.verify(typesRepository, Mockito.times(1)).save(newType);
    }

    @Test
    void update() {
        Type updatedType = TYPE_1;
        String name = updatedType.getName();
        updatedType.setName("Новое имя типа");
        Mockito.when(typesRepository.findByName("Новое имя типа")).thenReturn(Optional.of(updatedType));
        typesService.update(name, updatedType);

        Mockito.verify(typesRepository, Mockito.times(1)).save(updatedType);
        Assertions.assertNotNull(typesService.findByName("Новое имя типа").orElse(null));
        Assertions.assertNull(typesService.findByName(name).orElse(null));
    }

    @Test
    void delete() {
        String name = TYPE_1.getName();
        typesService.delete(name);

        Mockito.verify(typesRepository, Mockito.times(1)).deleteByName(name);
        Assertions.assertNull(typesRepository.findByName(name).orElse(null));
    }
}