package ru.maconconsulting.library.services.content;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.maconconsulting.library.models.content.Publication;
import ru.maconconsulting.library.models.parameters.*;
import ru.maconconsulting.library.repositories.content.PublicationsRepository;
import ru.maconconsulting.library.services.parameters.*;
import ru.maconconsulting.library.utils.search.SearchPublication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static ru.maconconsulting.library.util.content.PublicationsTestData.*;
import static ru.maconconsulting.library.util.parameters.FormatsTestData.*;
import static ru.maconconsulting.library.util.parameters.SegmentsTestData.*;

@SpringBootTest
@AutoConfigureMockMvc
class PublicationsServiceTest {

    @InjectMocks
    private PublicationsService publicationsService;

    @Mock
    private SegmentsService segmentsService;

    @Mock
    private FormatsService formatsService;

    @Mock
    private PublicationsRepository publicationsRepository;

    @Test
    void findAll() {
        List<Publication> expectedPublications = List.of(PUBLICATION_1, PUBLICATION_2, PUBLICATION_3);
        Mockito.when(publicationsRepository.findAll()).thenReturn(expectedPublications);
        List<Publication> actualPublications = publicationsService.findAll();

        Mockito.verify(publicationsRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(actualPublications);
        Assertions.assertEquals(expectedPublications.size(), actualPublications.size());
        Assertions.assertEquals(expectedPublications, actualPublications);
    }

    @Test
    void findById() {
        Integer id = PUBLICATION_1.getId();
        Mockito.when(publicationsRepository.findById(id)).thenReturn(Optional.of(PUBLICATION_1));
        Optional<Publication> actualPublication = publicationsService.findById(id);

        Mockito.verify(publicationsRepository, Mockito.times(1)).findById(id);
        Assertions.assertNotNull(actualPublication.orElse(null));
        Assertions.assertEquals(PUBLICATION_1, actualPublication.orElse(null));
    }

    @Test
    void findByTitle() {
        String title = PUBLICATION_1.getTitle();
        Mockito.when(publicationsRepository.findByTitle(title)).thenReturn(Optional.of(PUBLICATION_1));
        Optional<Publication> actualPublication = publicationsService.findByTitle(title);

        Mockito.verify(publicationsRepository, Mockito.times(1)).findByTitle(title);
        Assertions.assertNotNull(actualPublication.orElse(null));
        Assertions.assertEquals(PUBLICATION_1, actualPublication.orElse(null));
    }

    @Test
    void save() {
        Publication newPublication = new Publication("Новый материал", "new annotation",
                "new source", 2024, "01.2024", "/test/publ/new",
                "Россия, Краснодарский край, Краснодар",
                Set.of(SEGMENT_1), Set.of(FORMAT_1),  "Доверительное управление");
        Mockito.when(segmentsService.findByName(SEGMENT_1.getName())).thenReturn(Optional.of(SEGMENT_1));
        Mockito.when(formatsService.findByName(FORMAT_1.getName())).thenReturn(Optional.of(FORMAT_1));
        Mockito.when(publicationsRepository.save(newPublication)).thenReturn(newPublication);
        publicationsService.save(newPublication);

        Mockito.verify(publicationsRepository, Mockito.times(1)).save(newPublication);
    }

    @Test
    void update() {
        Publication updatedPublication = PUBLICATION_1;
        Integer id = PUBLICATION_1.getId();
        PUBLICATION_1.setCreatedAt(LocalDateTime.now());
        PUBLICATION_1.setTitle("Updated PUBLICATION_1");
        Mockito.when(publicationsRepository.findById(id)).thenReturn(Optional.of(PUBLICATION_1));
        Mockito.when(publicationsRepository.findByTitle("Updated PUBLICATION_1")).thenReturn(Optional.of(updatedPublication));
        for (Segment segment : updatedPublication.getSegments()) {
            Mockito.when(segmentsService.findByName(segment.getName())).thenReturn(Optional.of(segment));
        }
        for (Format format : updatedPublication.getFormats()) {
            Mockito.when(formatsService.findByName(format.getName())).thenReturn(Optional.of(format));
        }
        publicationsService.update(id, updatedPublication);

        Mockito.verify(publicationsRepository, Mockito.times(1)).save(updatedPublication);
        Assertions.assertNotNull(publicationsService.findByTitle("Updated PUBLICATION_1").orElse(null));
        Assertions.assertNull(publicationsService.findByTitle("Тестовый материал 1").orElse(null));
    }

    @Test
    void delete() {
        Integer id = PUBLICATION_1.getId();
        publicationsService.delete(id);

        Mockito.verify(publicationsRepository, Mockito.times(1)).deleteById(id);
        Assertions.assertNull(publicationsService.findById(id).orElse(null));
    }

    @Test
    void search() {
        SearchPublication searchPublication = new SearchPublication("", "",
                null, 2024, null, "", SEGMENT_DTO_1, FORMAT_DTO_1, "");
        List<Publication> expectedPublications = List.of(PUBLICATION_1, PUBLICATION_2);
        Mockito.when(publicationsRepository.findAll()).thenReturn(expectedPublications);
        List<Publication> actualPublications = publicationsService.search(searchPublication);

        Mockito.verify(publicationsRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(actualPublications);
        Assertions.assertEquals(expectedPublications.size(), actualPublications.size());
        Assertions.assertEquals(expectedPublications, actualPublications);
    }
}