package ru.maconconsulting.library.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.maconconsulting.library.models.projectfields.ProjectType;
import ru.maconconsulting.library.repositories.projectfields.ProjectTypesRepository;
import ru.maconconsulting.library.services.projectfields.ProjectTypesService;

import java.util.List;
import java.util.Optional;

import static ru.maconconsulting.library.util.ProjectTypesTestData.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectTypesServiceTest {

    @InjectMocks
    private ProjectTypesService projectTypesService;

    @Mock
    private ProjectTypesRepository projectTypesRepository;

    @Test
    void findAll() {
        List<ProjectType> expectedProjectTypes = List.of(PROJECT_TYPE_1, PROJECT_TYPE_2, PROJECT_TYPE_3);
        Mockito.when(projectTypesRepository.findAll()).thenReturn(expectedProjectTypes);
        List<ProjectType> actualProjectTypes = projectTypesService.findAll();

        Mockito.verify(projectTypesRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(actualProjectTypes);
        Assertions.assertEquals(expectedProjectTypes.size(), actualProjectTypes.size());
        Assertions.assertEquals(expectedProjectTypes, actualProjectTypes);
    }

    @Test
    void findByName() {
        String name = PROJECT_TYPE_1.getName();
        Mockito.when(projectTypesRepository.findByName(name)).thenReturn(Optional.of(PROJECT_TYPE_1));
        Optional<ProjectType> actualProjectType = projectTypesService.findByName(name);

        Mockito.verify(projectTypesRepository, Mockito.times(1)).findByName(name);
        Assertions.assertNotNull(actualProjectType.orElse(null));
        Assertions.assertEquals(PROJECT_TYPE_1, actualProjectType.orElse(null));
    }

    @Test
    void save() {
        ProjectType newType = new ProjectType("Новый тип");
        Mockito.when(projectTypesRepository.save(newType)).thenReturn(newType);

        projectTypesService.save(newType);
        Mockito.verify(projectTypesRepository, Mockito.times(1)).save(newType);
    }

    @Test
    void update() {
        ProjectType updatedProject = PROJECT_TYPE_1;
        String name = updatedProject.getName();
        updatedProject.setName("Новое имя типа");
        Mockito.when(projectTypesRepository.findByName("Новое имя типа")).thenReturn(Optional.of(updatedProject));
        projectTypesService.update(name, updatedProject);

        Mockito.verify(projectTypesRepository, Mockito.times(1)).save(updatedProject);
        Assertions.assertNotNull(projectTypesService.findByName("Новое имя типа").orElse(null));
        Assertions.assertNull(projectTypesService.findByName(name).orElse(null));
    }

    @Test
    void delete() {
        String name = PROJECT_TYPE_1.getName();
        projectTypesService.delete(name);

        Mockito.verify(projectTypesRepository, Mockito.times(1)).deleteByName(name);
        Assertions.assertNull(projectTypesService.findByName(name).orElse(null));
    }
}