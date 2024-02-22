package ru.maconconsulting.library.services.projectfields;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.maconconsulting.library.models.projectfields.ProjectSegment;
import ru.maconconsulting.library.repositories.projectfields.ProjectSegmentsRepository;

import java.util.List;
import java.util.Optional;

import static ru.maconconsulting.library.util.projectfields.ProjectSegmentsTestData.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectSegmentsServiceTest {

    @InjectMocks
    private ProjectSegmentsService projectSegmentsService;

    @Mock
    private ProjectSegmentsRepository projectSegmentsRepository;

    @Test
    void findAll() {
        List<ProjectSegment> expectedProjectSegments = List.of(PROJECT_SEGMENT_1, PROJECT_SEGMENT_2, PROJECT_SEGMENT_3);
        Mockito.when(projectSegmentsRepository.findAll()).thenReturn(expectedProjectSegments);
        List<ProjectSegment> actualProjectSegments = projectSegmentsService.findAll();

        Mockito.verify(projectSegmentsRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(actualProjectSegments);
        Assertions.assertEquals(expectedProjectSegments.size(), actualProjectSegments.size());
        Assertions.assertEquals(expectedProjectSegments, actualProjectSegments);
    }

    @Test
    void findByName() {
        String name = PROJECT_SEGMENT_1.getName();
        Mockito.when(projectSegmentsRepository.findByName(name)).thenReturn(Optional.of(PROJECT_SEGMENT_1));
        Optional<ProjectSegment> actualProjectSegment = projectSegmentsService.findByName(name);

        Mockito.verify(projectSegmentsRepository, Mockito.times(1)).findByName(name);
        Assertions.assertNotNull(actualProjectSegment.orElse(null));
        Assertions.assertEquals(PROJECT_SEGMENT_1, actualProjectSegment.orElse(null));
    }

    @Test
    void save() {
        ProjectSegment newSegment = new ProjectSegment("Новый сегмент");
        Mockito.when(projectSegmentsRepository.save(newSegment)).thenReturn(newSegment);

        projectSegmentsService.save(newSegment);
        Mockito.verify(projectSegmentsRepository, Mockito.times(1)).save(newSegment);
    }

    @Test
    void update() {
        ProjectSegment updatedSegment = PROJECT_SEGMENT_1;
        String name = updatedSegment.getName();
        updatedSegment.setName("Новое имя сегмента");
        Mockito.when(projectSegmentsRepository.findByName("Новое имя сегмента")).thenReturn(Optional.of(updatedSegment));
        projectSegmentsService.update(name, updatedSegment);

        Mockito.verify(projectSegmentsRepository, Mockito.times(1)).save(updatedSegment);
        Assertions.assertNotNull(projectSegmentsService.findByName("Новое имя сегмента").orElse(null));
        Assertions.assertNull(projectSegmentsService.findByName(name).orElse(null));
    }

    @Test
    void delete() {
        String name = PROJECT_SEGMENT_1.getName();
        projectSegmentsService.delete(name);

        Mockito.verify(projectSegmentsRepository, Mockito.times(1)).deleteByName(name);
        Assertions.assertNull(projectSegmentsService.findByName(name).orElse(null));
    }
}