package ru.maconconsulting.library.services.parameters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.maconconsulting.library.models.parameters.TypeOfPublication;
import ru.maconconsulting.library.repositories.parameters.TypesOfPublicationRepository;

import java.util.List;
import java.util.Optional;

import static ru.maconconsulting.library.util.parameters.TypesOfPublicationTestData.*;

@SpringBootTest
@AutoConfigureMockMvc
class TypesOfPublicationServiceTest {

    @InjectMocks
    private TypesOfPublicationService service;

    @Mock
    private TypesOfPublicationRepository repository;

    @Test
    void findAll() {
        List<TypeOfPublication> expectedTypes = List.of(TYPE_OF_PUBLICATION_1, TYPE_OF_PUBLICATION_2, TYPE_OF_PUBLICATION_3);
        Mockito.when(repository.findAll()).thenReturn(expectedTypes);
        List<TypeOfPublication> actualTypes = service.findAll();

        Mockito.verify(repository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(actualTypes);
        Assertions.assertEquals(expectedTypes.size(), actualTypes.size());
        Assertions.assertEquals(expectedTypes, actualTypes);
    }

    @Test
    void findByName() {
        String name = TYPE_OF_PUBLICATION_1.getName();
        Mockito.when(repository.findByName(name)).thenReturn(Optional.of(TYPE_OF_PUBLICATION_1));
        Optional<TypeOfPublication> actualType = service.findByName(name);

        Mockito.verify(repository, Mockito.times(1)).findByName(name);
        Assertions.assertNotNull(actualType.orElse(null));
        Assertions.assertEquals(TYPE_OF_PUBLICATION_1, actualType.orElse(null));
    }

    @Test
    void save() {
        TypeOfPublication newType = new TypeOfPublication("Новый тип");
        Mockito.when(repository.save(newType)).thenReturn(newType);
        service.save(newType);

        Mockito.verify(repository, Mockito.times(1)).save(newType);
    }

    @Test
    void update() {
        TypeOfPublication updatedType = TYPE_OF_PUBLICATION_1;
        String name = updatedType.getName();
        updatedType.setName("Новое имя типа");
        Mockito.when(repository.findByName("Новое имя типа")).thenReturn(Optional.of(updatedType));
        service.update(name, updatedType);

        Mockito.verify(repository, Mockito.times(1)).save(updatedType);
        Assertions.assertNotNull(service.findByName("Новое имя типа").orElse(null));
        Assertions.assertNull(service.findByName(name).orElse(null));
    }

    @Test
    void delete() {
        String name = TYPE_OF_PUBLICATION_1.getName();
        service.delete(name);

        Mockito.verify(repository, Mockito.times(1)).deleteByName(name);
        Assertions.assertNull(repository.findByName(name).orElse(null));
    }
}