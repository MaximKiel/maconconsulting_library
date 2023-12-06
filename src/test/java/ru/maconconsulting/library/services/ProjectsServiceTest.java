package ru.maconconsulting.library.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.maconconsulting.library.models.Project;
import ru.maconconsulting.library.repositories.ProjectsRepository;

import java.util.List;

import static ru.maconconsulting.library.util.ProjectsTestData.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectsServiceTest {

    @InjectMocks
    private ProjectsService projectsService;

    @Mock
    private ProjectsRepository projectsRepository;

    @Test
    void findAll() {
        List<Project> expectedProjects = List.of(PROJECT_1, PROJECT_2, PROJECT_3);
        Mockito.when(projectsRepository.findAll()).thenReturn(expectedProjects);
        List<Project> actualProjects = projectsService.findAll();

        Mockito.verify(projectsRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(actualProjects);
        Assertions.assertEquals(expectedProjects.size(), actualProjects.size());
        Assertions.assertEquals(expectedProjects, actualProjects);
    }

    @Test
    void findByNumber() {
    }

    @Test
    void findByTitle() {
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

    @Test
    void search() {
    }
}