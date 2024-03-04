package ru.maconconsulting.library.services.parameters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.maconconsulting.library.models.parameters.KeyWord;
import ru.maconconsulting.library.repositories.parameters.KeyWordsRepository;

import java.util.List;
import java.util.Optional;

import static ru.maconconsulting.library.util.parameters.KeyWordsTestData.*;

@SpringBootTest
@AutoConfigureMockMvc
class KeyWordsServiceTest {

    @InjectMocks
    private KeyWordsService keyWordsService;

    @Mock
    private KeyWordsRepository keyWordsRepository;

    @Test
    void findAll() {
        List<KeyWord> expectedKeyWords = List.of(PROJECT_KEY_WORD_1, PROJECT_KEY_WORD_2, PROJECT_KEY_WORD_3);
        Mockito.when(keyWordsRepository.findAll()).thenReturn(expectedKeyWords);
        List<KeyWord> actualKeyWords = keyWordsService.findAll();

        Mockito.verify(keyWordsRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(actualKeyWords);
        Assertions.assertEquals(expectedKeyWords.size(), actualKeyWords.size());
        Assertions.assertEquals(expectedKeyWords, actualKeyWords);
    }

    @Test
    void findByName() {
        String name = PROJECT_KEY_WORD_1.getName();
        Mockito.when(keyWordsRepository.findByName(name)).thenReturn(Optional.of(PROJECT_KEY_WORD_1));
        Optional<KeyWord> actualProjectKeyWord = keyWordsService.findByName(name);

        Mockito.verify(keyWordsRepository, Mockito.times(1)).findByName(name);
        Assertions.assertNotNull(actualProjectKeyWord.orElse(null));
        Assertions.assertEquals(PROJECT_KEY_WORD_1, actualProjectKeyWord.orElse(null));
    }

    @Test
    void save() {
        KeyWord newKeyWord = new KeyWord("Новое ключевое слово");
        Mockito.when(keyWordsRepository.save(newKeyWord)).thenReturn(newKeyWord);

        keyWordsService.save(newKeyWord);
        Mockito.verify(keyWordsRepository, Mockito.times(1)).save(newKeyWord);
    }

    @Test
    void update() {
        KeyWord updatedKeyWord = PROJECT_KEY_WORD_1;
        String name = updatedKeyWord.getName();
        updatedKeyWord.setName("Новое имя ключевого слова");
        Mockito.when(keyWordsRepository.findByName("Новое имя ключевого слова")).thenReturn(Optional.of(updatedKeyWord));
        keyWordsService.update(name, updatedKeyWord);

        Mockito.verify(keyWordsRepository, Mockito.times(1)).save(updatedKeyWord);
        Assertions.assertNotNull(keyWordsService.findByName("Новое имя ключевого слова").orElse(null));
        Assertions.assertNull(keyWordsService.findByName(name).orElse(null));
    }

    @Test
    void delete() {
        String name = PROJECT_KEY_WORD_1.getName();
        keyWordsService.delete(name);

        Mockito.verify(keyWordsRepository, Mockito.times(1)).deleteByName(name);
        Assertions.assertNull(keyWordsService.findByName(name).orElse(null));
    }
}