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

import static ru.maconconsulting.library.util.parameters.FormatsTestData.*;
import static ru.maconconsulting.library.util.parameters.SegmentsTestData.*;
import static ru.maconconsulting.library.util.parameters.ChaptersTestData.*;
import static ru.maconconsulting.library.util.content.ProjectsTestData.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectsServiceTest {

    @InjectMocks
    private ProjectsService projectsService;

    @Mock
    private ChaptersService chaptersService;

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
    void findByNumber() {
        String number = PROJECT_1.getNumber();
        Mockito.when(projectsRepository.findByNumber(number)).thenReturn(Optional.of(PROJECT_1));
        Optional<Project> actualProject = projectsService.findByNumber(number);

        Mockito.verify(projectsRepository, Mockito.times(1)).findByNumber(number);
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
        Project newProject = new Project("23200", 2023, "12.2023", "23200_New", "Client new",
                "Россия, Край, Город", List.of(CHAPTER_1), List.of(SEGMENT_1), List.of(FORMAT_1),  "Доверительное управление");
        Mockito.when(chaptersService.findByName(newProject.getChapters().get(0).getName())).thenReturn(Optional.of(newProject.getChapters().get(0)));
        Mockito.when(segmentsService.findByName(newProject.getSegments().get(0).getName())).thenReturn(Optional.of(newProject.getSegments().get(0)));
        Mockito.when(formatsService.findByName(newProject.getFormats().get(0).getName())).thenReturn(Optional.of(newProject.getFormats().get(0)));
        Mockito.when(projectsRepository.save(newProject)).thenReturn(newProject);
        projectsService.save(newProject);

        Mockito.verify(projectsRepository, Mockito.times(1)).save(newProject);
    }

    @Test
    void update() {
        Project updatedProject = PROJECT_1;
        String number = updatedProject.getNumber();
        PROJECT_1.setCreatedAt(LocalDateTime.now());
        PROJECT_1.setTitle("Updated PROJECT_1");
        Mockito.when(projectsRepository.findByNumber(number)).thenReturn(Optional.of(PROJECT_1));
        Mockito.when(projectsRepository.findByTitle("Updated PROJECT_1")).thenReturn(Optional.of(updatedProject));
        for (Chapter chapter : updatedProject.getChapters()) {
            Mockito.when(chaptersService.findByName(chapter.getName())).thenReturn(Optional.of(chapter));
        }
        for (Segment segment : updatedProject.getSegments()) {
            Mockito.when(segmentsService.findByName(segment.getName())).thenReturn(Optional.of(segment));
        }
        for (Format format : updatedProject.getFormats()) {
            Mockito.when(formatsService.findByName(format.getName())).thenReturn(Optional.of(format));
        }
        projectsService.update(number, updatedProject);

        Mockito.verify(projectsRepository, Mockito.times(1)).save(updatedProject);
        Assertions.assertNotNull(projectsService.findByTitle("Updated PROJECT_1").orElse(null));
        Assertions.assertNull(projectsService.findByTitle("23101Р_Тестовый проект 1").orElse(null));
    }

    @Test
    void delete() {
        String number = PROJECT_1.getNumber();
        projectsService.delete(number);

        Mockito.verify(projectsRepository, Mockito.times(1)).deleteByNumber(number);
        Assertions.assertNull(projectsService.findByNumber(number).orElse(null));
    }

    @Test
    void search() {
        SearchProject searchProject = new SearchProject(2023, "", "", "", "", CHAPTER_DTO_1, SEGMENT_DTO_1, FORMAT_DTO_1, "");
        List<Project> expectedProjects = List.of(PROJECT_1, PROJECT_2);
        Mockito.when(projectsRepository.findAll()).thenReturn(expectedProjects);
        List<Project> actualProjects = projectsService.search(searchProject);

        Mockito.verify(projectsRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(actualProjects);
        Assertions.assertEquals(expectedProjects.size(), actualProjects.size());
        Assertions.assertEquals(expectedProjects, actualProjects);
    }
}