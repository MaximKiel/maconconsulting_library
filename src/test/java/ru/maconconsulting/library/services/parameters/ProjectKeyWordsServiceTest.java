package ru.maconconsulting.library.services.parameters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.maconconsulting.library.models.parameters.ProjectKeyWord;
import ru.maconconsulting.library.repositories.parameters.ProjectKeyWordsRepository;

import java.util.List;
import java.util.Optional;

import static ru.maconconsulting.library.util.parameters.ProjectKeyWordsTestData.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectKeyWordsServiceTest {

    @InjectMocks
    private ProjectKeyWordsService projectKeyWordsService;

    @Mock
    private ProjectKeyWordsRepository projectKeyWordsRepository;

    @Test
    void findAll() {
        List<ProjectKeyWord> expectedProjectKeyWords = List.of(PROJECT_KEY_WORD_1, PROJECT_KEY_WORD_2, PROJECT_KEY_WORD_3);
        Mockito.when(projectKeyWordsRepository.findAll()).thenReturn(expectedProjectKeyWords);
        List<ProjectKeyWord> actualProjectKeyWords = projectKeyWordsService.findAll();

        Mockito.verify(projectKeyWordsRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(actualProjectKeyWords);
        Assertions.assertEquals(expectedProjectKeyWords.size(), actualProjectKeyWords.size());
        Assertions.assertEquals(expectedProjectKeyWords, actualProjectKeyWords);
    }

    @Test
    void findByName() {
        String name = PROJECT_KEY_WORD_1.getName();
        Mockito.when(projectKeyWordsRepository.findByName(name)).thenReturn(Optional.of(PROJECT_KEY_WORD_1));
        Optional<ProjectKeyWord> actualProjectKeyWord = projectKeyWordsService.findByName(name);

        Mockito.verify(projectKeyWordsRepository, Mockito.times(1)).findByName(name);
        Assertions.assertNotNull(actualProjectKeyWord.orElse(null));
        Assertions.assertEquals(PROJECT_KEY_WORD_1, actualProjectKeyWord.orElse(null));
    }

    @Test
    void save() {
        ProjectKeyWord newKeyWord = new ProjectKeyWord("Новое ключевое слово");
        Mockito.when(projectKeyWordsRepository.save(newKeyWord)).thenReturn(newKeyWord);

        projectKeyWordsService.save(newKeyWord);
        Mockito.verify(projectKeyWordsRepository, Mockito.times(1)).save(newKeyWord);
    }

    @Test
    void update() {
        ProjectKeyWord updatedKeyWord = PROJECT_KEY_WORD_1;
        String name = updatedKeyWord.getName();
        updatedKeyWord.setName("Новое имя ключевого слова");
        Mockito.when(projectKeyWordsRepository.findByName("Новое имя ключевого слова")).thenReturn(Optional.of(updatedKeyWord));
        projectKeyWordsService.update(name, updatedKeyWord);

        Mockito.verify(projectKeyWordsRepository, Mockito.times(1)).save(updatedKeyWord);
        Assertions.assertNotNull(projectKeyWordsService.findByName("Новое имя ключевого слова").orElse(null));
        Assertions.assertNull(projectKeyWordsService.findByName(name).orElse(null));
    }

    @Test
    void delete() {
        String name = PROJECT_KEY_WORD_1.getName();
        projectKeyWordsService.delete(name);

        Mockito.verify(projectKeyWordsRepository, Mockito.times(1)).deleteByName(name);
        Assertions.assertNull(projectKeyWordsService.findByName(name).orElse(null));
    }
}