package ru.maconconsulting.library.services.content;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.maconconsulting.library.models.content.Project;
import ru.maconconsulting.library.models.parameters.Format;
import ru.maconconsulting.library.models.parameters.KeyWord;
import ru.maconconsulting.library.models.parameters.Segment;
import ru.maconconsulting.library.repositories.content.ProjectsRepository;
import ru.maconconsulting.library.services.parameters.FormatsService;
import ru.maconconsulting.library.services.parameters.KeyWordsService;
import ru.maconconsulting.library.services.parameters.SegmentsService;
import ru.maconconsulting.library.services.parameters.TypesService;
import ru.maconconsulting.library.utils.search.SearchProject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ru.maconconsulting.library.util.parameters.FormatsTestData.*;
import static ru.maconconsulting.library.util.parameters.KeyWordsTestData.PROJECT_KEY_WORD_1;
import static ru.maconconsulting.library.util.parameters.KeyWordsTestData.PROJECT_KEY_WORD_DTO_1;
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
    private KeyWordsService keyWordsService;

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
        Project newProject = new Project("23200", 2023, "12.2023", "23200_New", "Client new", "Россия",
                "Край", "Город", List.of(PROJECT_SEGMENT_1), PROJECT_TYPE_1, List.of(PROJECT_FORMAT_1), List.of(PROJECT_KEY_WORD_1));
        Mockito.when(typesService.findByName(newProject.getType().getName())).thenReturn(Optional.of(newProject.getType()));
        Mockito.when(segmentsService.findByName(newProject.getSegments().get(0).getName())).thenReturn(Optional.of(newProject.getSegments().get(0)));
        Mockito.when(formatsService.findByName(newProject.getFormats().get(0).getName())).thenReturn(Optional.of(newProject.getFormats().get(0)));
        Mockito.when(keyWordsService.findByName(newProject.getKeyWords().get(0).getName())).thenReturn(Optional.of(newProject.getKeyWords().get(0)));
        Mockito.when(projectsRepository.save(newProject)).thenReturn(newProject);

        projectsService.save(newProject);
        Mockito.verify(projectsRepository, Mockito.times(1)).save(newProject);
    }

    @Test
    void update() {
        Project updatedProject = PROJECT_1;
        String number = updatedProject.getNumber();
        PROJECT_1.setCreatedAt(LocalDateTime.now());
        PROJECT_1.setTitle("Update PROJECT_1");
        Mockito.when(typesService.findByName(updatedProject.getType().getName())).thenReturn(Optional.of(updatedProject.getType()));
        Mockito.when(projectsRepository.findByNumber(number)).thenReturn(Optional.of(PROJECT_1));
        Mockito.when(projectsRepository.findByTitle("Update PROJECT_1")).thenReturn(Optional.of(updatedProject));
        for (Segment segment : updatedProject.getSegments()) {
            Mockito.when(segmentsService.findByName(segment.getName())).thenReturn(Optional.of(segment));
        }
        for (Format format : updatedProject.getFormats()) {
            Mockito.when(formatsService.findByName(format.getName())).thenReturn(Optional.of(format));
        }
        for (KeyWord keyWord : updatedProject.getKeyWords()) {
            Mockito.when(keyWordsService.findByName(keyWord.getName())).thenReturn(Optional.of(keyWord));
        }

        projectsService.update(number, updatedProject);

        Mockito.verify(projectsRepository, Mockito.times(1)).save(updatedProject);
        Assertions.assertNotNull(projectsService.findByTitle("Update PROJECT_1").orElse(null));
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
        SearchProject searchProject = new SearchProject(2023, "", "", "", "", "", "", PROJECT_SEGMENT_DTO_1, PROJECT_TYPE_DTO_1, PROJECT_FORMAT_DTO_1, PROJECT_KEY_WORD_DTO_1);
        List<Project> expectedProjects = List.of(PROJECT_1, PROJECT_2);
        Mockito.when(projectsRepository.findAll()).thenReturn(expectedProjects);
        List<Project> actualProjects = projectsService.search(searchProject);

        Mockito.verify(projectsRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(actualProjects);
        Assertions.assertEquals(expectedProjects.size(), actualProjects.size());
        Assertions.assertEquals(expectedProjects, actualProjects);
    }
}