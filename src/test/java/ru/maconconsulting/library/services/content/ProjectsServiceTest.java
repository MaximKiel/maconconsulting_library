package ru.maconconsulting.library.services.content;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.maconconsulting.library.models.content.Project;
import ru.maconconsulting.library.models.parameters.*;
import ru.maconconsulting.library.repositories.content.ProjectsRepository;
import ru.maconconsulting.library.services.parameters.*;
import ru.maconconsulting.library.utils.search.SearchProject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static ru.maconconsulting.library.util.parameters.FormatsTestData.*;
import static ru.maconconsulting.library.util.parameters.SegmentsTestData.*;
import static ru.maconconsulting.library.util.parameters.TypesTestData.*;
import static ru.maconconsulting.library.util.content.ProjectsTestData.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectsServiceTest {

    @InjectMocks
    private ProjectsService projectsService;

    @Mock
    private TypesService typesService;

    @Mock
    private SegmentsService segmentsService;

    @Mock
    private FormatsService formatsService;

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
    void findById() {
        Integer id = PROJECT_1.getId();
        Mockito.when(projectsRepository.findById(id)).thenReturn(Optional.of(PROJECT_1));
        Optional<Project> actualProject = projectsService.findById(id);

        Mockito.verify(projectsRepository, Mockito.times(1)).findById(id);
        Assertions.assertNotNull(actualProject.orElse(null));
        Assertions.assertEquals(PROJECT_1, actualProject.orElse(null));
    }

    @Test
    void findByTitle() {
        String title = PROJECT_1.getTitle();
        Mockito.when(projectsRepository.findByTitle(title)).thenReturn(Optional.of(PROJECT_1));
        Optional<Project> actualProject = projectsService.findByTitle(title);

        Mockito.verify(projectsRepository, Mockito.times(1)).findByTitle(title);
        Assertions.assertNotNull(actualProject.orElse(null));
        Assertions.assertEquals(PROJECT_1, actualProject.orElse(null));
    }

    @Test
    void save() {
        Project newProject = new Project(2023, "12.2023", "23200_New", "Client new",
                "Россия, Край, Город", Set.of(TYPE_1), Set.of(SEGMENT_1), Set.of(FORMAT_1),
                "Доверительное управление", "Анализ оценок ЕРЗ");
        Mockito.when(typesService.findByName(TYPE_1.getName())).thenReturn(Optional.of(TYPE_1));
        Mockito.when(segmentsService.findByName(SEGMENT_1.getName())).thenReturn(Optional.of(SEGMENT_1));
        Mockito.when(formatsService.findByName(FORMAT_1.getName())).thenReturn(Optional.of(FORMAT_1));
        Mockito.when(projectsRepository.save(newProject)).thenReturn(newProject);
        projectsService.save(newProject);

        Mockito.verify(projectsRepository, Mockito.times(1)).save(newProject);
    }

    @Test
    void update() {
        Project updatedProject = PROJECT_1;
        Integer id = updatedProject.getId();
        PROJECT_1.setCreatedAt(LocalDateTime.now());
        PROJECT_1.setTitle("Updated PROJECT_1");
        Mockito.when(projectsRepository.findById(id)).thenReturn(Optional.of(PROJECT_1));
        Mockito.when(projectsRepository.findByTitle("Updated PROJECT_1")).thenReturn(Optional.of(updatedProject));
        for (Type type : updatedProject.getTypes()) {
            Mockito.when(typesService.findByName(type.getName())).thenReturn(Optional.of(type));
        }
        for (Segment segment : updatedProject.getSegments()) {
            Mockito.when(segmentsService.findByName(segment.getName())).thenReturn(Optional.of(segment));
        }
        for (Format format : updatedProject.getFormats()) {
            Mockito.when(formatsService.findByName(format.getName())).thenReturn(Optional.of(format));
        }
        projectsService.update(id, updatedProject);

        Mockito.verify(projectsRepository, Mockito.times(1)).save(updatedProject);
        Assertions.assertNotNull(projectsService.findByTitle("Updated PROJECT_1").orElse(null));
        Assertions.assertNull(projectsService.findByTitle("23101Р_Тестовый проект 1").orElse(null));
    }

    @Test
    void delete() {
        Integer id = PROJECT_1.getId();
        projectsService.delete(id);

        Mockito.verify(projectsRepository, Mockito.times(1)).deleteById(id);
        Assertions.assertNull(projectsService.findById(id).orElse(null));
    }

    @Test
    void searchProject() {
        SearchProject searchProject = new SearchProject(0, "", "", "", "",
                null, null, null, "Доверительное управление", "");
        List<Project> expectedProjects = List.of(PROJECT_2, PROJECT_3);
        Mockito.when(projectsRepository.findAll()).thenReturn(expectedProjects);
        List<Project> actualProjects = projectsService.searchProject(searchProject);

        Mockito.verify(projectsRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(actualProjects);
        Assertions.assertEquals(expectedProjects.size(), actualProjects.size());
        Assertions.assertEquals(expectedProjects, actualProjects);
    }

    @Test
    void searchMethodology() {
        SearchProject searchProject = new SearchProject(0, null, null, "", "",
                null, null, null, "", "");
        List<Project> expectedProjects = List.of(PROJECT_2);
        Mockito.when(projectsRepository.findAll()).thenReturn(expectedProjects);
        List<Project> actualProjects = projectsService.searchMethodology(searchProject);

        Mockito.verify(projectsRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(actualProjects);
        Assertions.assertEquals(expectedProjects.size(), actualProjects.size());
        Assertions.assertEquals(expectedProjects, actualProjects);
    }
}