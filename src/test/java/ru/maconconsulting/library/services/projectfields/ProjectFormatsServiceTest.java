package ru.maconconsulting.library.services.projectfields;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.maconconsulting.library.models.projectfields.ProjectFormat;
import ru.maconconsulting.library.repositories.projectfields.ProjectFormatsRepository;

import java.util.List;
import java.util.Optional;

import static ru.maconconsulting.library.util.projectfields.ProjectFormatsTestData.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectFormatsServiceTest {

    @InjectMocks
    private ProjectFormatsService projectFormatsService;

    @Mock
    private ProjectFormatsRepository projectFormatsRepository;

    @Test
    void findAllSorted() {
        List<ProjectFormat> expectedProjectFormats = List.of(PROJECT_FORMAT_1, PROJECT_FORMAT_2, PROJECT_FORMAT_3);
        Mockito.when(projectFormatsRepository.findAll()).thenReturn(expectedProjectFormats);
        List<ProjectFormat> actualProjectFormats = projectFormatsService.findAllSorted();

        Mockito.verify(projectFormatsRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(actualProjectFormats);
        Assertions.assertEquals(expectedProjectFormats.size(), actualProjectFormats.size());
        Assertions.assertEquals(expectedProjectFormats, actualProjectFormats);
    }

    @Test
    void findByName() {
        String name = PROJECT_FORMAT_1.getName();
        Mockito.when(projectFormatsRepository.findByName(name)).thenReturn(Optional.of(PROJECT_FORMAT_1));
        Optional<ProjectFormat> actualProjectFormat = projectFormatsService.findByName(name);

        Mockito.verify(projectFormatsRepository, Mockito.times(1)).findByName(name);
        Assertions.assertNotNull(actualProjectFormat.orElse(null));
        Assertions.assertEquals(PROJECT_FORMAT_1, actualProjectFormat.orElse(null));
    }

    @Test
    void save() {
        ProjectFormat newFormat = new ProjectFormat("Новый формат");
        Mockito.when(projectFormatsRepository.save(newFormat)).thenReturn(newFormat);

        projectFormatsService.save(newFormat);
        Mockito.verify(projectFormatsRepository, Mockito.times(1)).save(newFormat);
    }

    @Test
    void update() {
        ProjectFormat updatedFormat = PROJECT_FORMAT_1;
        String name = updatedFormat.getName();
        updatedFormat.setName("Новое имя формата");
        Mockito.when(projectFormatsRepository.findByName("Новое имя формата")).thenReturn(Optional.of(updatedFormat));
        projectFormatsService.update(name, updatedFormat);

        Mockito.verify(projectFormatsRepository, Mockito.times(1)).save(updatedFormat);
        Assertions.assertNotNull(projectFormatsService.findByName("Новое имя формата").orElse(null));
        Assertions.assertNull(projectFormatsService.findByName(name).orElse(null));
    }

    @Test
    void delete() {
        String name = PROJECT_FORMAT_1.getName();
        projectFormatsService.delete(name);

        Mockito.verify(projectFormatsRepository, Mockito.times(1)).deleteByName(name);
        Assertions.assertNull(projectFormatsService.findByName(name).orElse(null));
    }
}
