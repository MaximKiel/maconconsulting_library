package ru.maconconsulting.library.services.parameters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.maconconsulting.library.models.parameters.Chapter;
import ru.maconconsulting.library.repositories.parameters.ChaptersRepository;

import java.util.List;
import java.util.Optional;

import static ru.maconconsulting.library.util.parameters.ChaptersTestData.*;

@SpringBootTest
@AutoConfigureMockMvc
class ChaptersServiceTest {

    @InjectMocks
    private ChaptersService chaptersService;

    @Mock
    private ChaptersRepository chaptersRepository;

    @Test
    void findAll() {
        List<Chapter> expectedChapters = List.of(CHAPTER_1, CHAPTER_2, CHAPTER_3);
        Mockito.when(chaptersRepository.findAll()).thenReturn(expectedChapters);
        List<Chapter> actualChapters = chaptersService.findAll();

        Mockito.verify(chaptersRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(actualChapters);
        Assertions.assertEquals(expectedChapters.size(), actualChapters.size());
        Assertions.assertEquals(expectedChapters, actualChapters);
    }

    @Test
    void findByName() {
        String name = CHAPTER_1.getName();
        Mockito.when(chaptersRepository. findByName(name)).thenReturn(Optional.of(CHAPTER_1));
        Optional<Chapter> actualChapter = chaptersService.findByName(name);

        Mockito.verify(chaptersRepository, Mockito.times(1)).findByName(name);
        Assertions.assertNotNull(actualChapter.orElse(null));
        Assertions.assertEquals(CHAPTER_1, actualChapter.orElse(null));
    }

    @Test
    void save() {
        Chapter newChapter = new Chapter("Новый раздел");
        Mockito.when(chaptersRepository.save(newChapter)).thenReturn(newChapter);
        chaptersService.save(newChapter);

        Mockito.verify(chaptersRepository, Mockito.times(1)).save(newChapter);
    }

    @Test
    void update() {
        Chapter updatedChapter = CHAPTER_1;
        String name = updatedChapter.getName();
        updatedChapter.setName("Новое имя раздела");
        Mockito.when(chaptersRepository.findByName("Новое имя раздела")).thenReturn(Optional.of(updatedChapter));
        chaptersService.update(name, updatedChapter);

        Mockito.verify(chaptersRepository, Mockito.times(1)).save(updatedChapter);
        Assertions.assertNotNull(chaptersService.findByName("Новое имя раздела").orElse(null));
        Assertions.assertNull(chaptersService.findByName(name).orElse(null));
    }

    @Test
    void delete() {
        String name = CHAPTER_1.getName();
        chaptersService.delete(name);

        Mockito.verify(chaptersRepository, Mockito.times(1)).deleteByName(name);
        Assertions.assertNull(chaptersService.findByName(name).orElse(null));
    }
}