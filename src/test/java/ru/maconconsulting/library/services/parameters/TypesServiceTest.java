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
        List<Type> expectedTypes = List.of(PROJECT_TYPE_1, PROJECT_TYPE_2, PROJECT_TYPE_3);
        Mockito.when(typesRepository.findAll()).thenReturn(expectedTypes);
        List<Type> actualTypes = typesService.findAll();

        Mockito.verify(typesRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(actualTypes);
        Assertions.assertEquals(expectedTypes.size(), actualTypes.size());
        Assertions.assertEquals(expectedTypes, actualTypes);
    }

    @Test
    void findByName() {
        String name = PROJECT_TYPE_1.getName();
        Mockito.when(typesRepository.findByName(name)).thenReturn(Optional.of(PROJECT_TYPE_1));
        Optional<Type> actualProjectType = typesService.findByName(name);

        Mockito.verify(typesRepository, Mockito.times(1)).findByName(name);
        Assertions.assertNotNull(actualProjectType.orElse(null));
        Assertions.assertEquals(PROJECT_TYPE_1, actualProjectType.orElse(null));
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
        Type updatedProject = PROJECT_TYPE_1;
        String name = updatedProject.getName();
        updatedProject.setName("Новое имя типа");
        Mockito.when(typesRepository.findByName("Новое имя типа")).thenReturn(Optional.of(updatedProject));
        typesService.update(name, updatedProject);

        Mockito.verify(typesRepository, Mockito.times(1)).save(updatedProject);
        Assertions.assertNotNull(typesService.findByName("Новое имя типа").orElse(null));
        Assertions.assertNull(typesService.findByName(name).orElse(null));
    }

    @Test
    void delete() {
        String name = PROJECT_TYPE_1.getName();
        typesService.delete(name);

        Mockito.verify(typesRepository, Mockito.times(1)).deleteByName(name);
        Assertions.assertNull(typesService.findByName(name).orElse(null));
    }
}