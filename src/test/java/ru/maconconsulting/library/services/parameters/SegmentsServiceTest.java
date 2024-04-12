package ru.maconconsulting.library.services.parameters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.maconconsulting.library.models.parameters.Segment;
import ru.maconconsulting.library.repositories.parameters.SegmentsRepository;

import java.util.List;
import java.util.Optional;

import static ru.maconconsulting.library.util.parameters.SegmentsTestData.*;

@SpringBootTest
@AutoConfigureMockMvc
class SegmentsServiceTest {

    @InjectMocks
    private SegmentsService segmentsService;

    @Mock
    private SegmentsRepository segmentsRepository;

    @Test
    void findAll() {
        List<Segment> expectedSegments = List.of(SEGMENT_1, SEGMENT_2, SEGMENT_3);
        Mockito.when(segmentsRepository.findAll()).thenReturn(expectedSegments);
        List<Segment> actualSegments = segmentsService.findAll();

        Mockito.verify(segmentsRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(actualSegments);
        Assertions.assertEquals(expectedSegments.size(), actualSegments.size());
        Assertions.assertEquals(expectedSegments, actualSegments);
    }

    @Test
    void findByName() {
        String name = SEGMENT_1.getName();
        Mockito.when(segmentsRepository.findByName(name)).thenReturn(Optional.of(SEGMENT_1));
        Optional<Segment> actualSegment = segmentsService.findByName(name);

        Mockito.verify(segmentsRepository, Mockito.times(1)).findByName(name);
        Assertions.assertNotNull(actualSegment.orElse(null));
        Assertions.assertEquals(SEGMENT_1, actualSegment.orElse(null));
    }

    @Test
    void save() {
        Segment newSegment = new Segment("Новый сегмент");
        Mockito.when(segmentsRepository.save(newSegment)).thenReturn(newSegment);
        segmentsService.save(newSegment);

        Mockito.verify(segmentsRepository, Mockito.times(1)).save(newSegment);
    }

    @Test
    void update() {
        Segment updatedSegment = SEGMENT_1;
        String name = updatedSegment.getName();
        updatedSegment.setName("Новое имя сегмента");
        Mockito.when(segmentsRepository.findByName("Новое имя сегмента")).thenReturn(Optional.of(updatedSegment));
        segmentsService.update(name, updatedSegment);

        Mockito.verify(segmentsRepository, Mockito.times(1)).save(updatedSegment);
        Assertions.assertNotNull(segmentsService.findByName("Новое имя сегмента").orElse(null));
        Assertions.assertNull(segmentsService.findByName(name).orElse(null));
    }

    @Test
    void delete() {
        String name = SEGMENT_1.getName();
        segmentsService.delete(name);

        Mockito.verify(segmentsRepository, Mockito.times(1)).deleteByName(name);
        Assertions.assertNull(segmentsService.findByName(name).orElse(null));
    }
}