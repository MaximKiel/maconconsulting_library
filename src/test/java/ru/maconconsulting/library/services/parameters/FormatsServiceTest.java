package ru.maconconsulting.library.services.parameters;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.maconconsulting.library.models.parameters.Format;
import ru.maconconsulting.library.repositories.parameters.FormatsRepository;

import java.util.List;
import java.util.Optional;

import static ru.maconconsulting.library.util.parameters.FormatsTestData.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FormatsServiceTest {

    @InjectMocks
    private FormatsService formatsService;

    @Mock
    private FormatsRepository formatsRepository;

    @Test
    void findAll() {
        List<Format> expectedFormats = List.of(FORMAT_1, FORMAT_2, FORMAT_3);
        Mockito.when(formatsRepository.findAll()).thenReturn(expectedFormats);
        List<Format> actualFormats = formatsService.findAll();

        Mockito.verify(formatsRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(actualFormats);
        Assertions.assertEquals(expectedFormats.size(), actualFormats.size());
        Assertions.assertEquals(expectedFormats, actualFormats);
    }

    @Test
    void findByName() {
        String name = FORMAT_1.getName();
        Mockito.when(formatsRepository.findByName(name)).thenReturn(Optional.of(FORMAT_1));
        Optional<Format> actualFormat = formatsService.findByName(name);

        Mockito.verify(formatsRepository, Mockito.times(1)).findByName(name);
        Assertions.assertNotNull(actualFormat.orElse(null));
        Assertions.assertEquals(FORMAT_1, actualFormat.orElse(null));
    }

    @Test
    void save() {
        Format newFormat = new Format("Новый формат");
        Mockito.when(formatsRepository.save(newFormat)).thenReturn(newFormat);
        formatsService.save(newFormat);

        Mockito.verify(formatsRepository, Mockito.times(1)).save(newFormat);
    }

    @Test
    void update() {
        Format updatedFormat = FORMAT_1;
        String name = updatedFormat.getName();
        updatedFormat.setName("Новое имя формата");
        Mockito.when(formatsRepository.findByName("Новое имя формата")).thenReturn(Optional.of(updatedFormat));
        formatsService.update(name, updatedFormat);

        Mockito.verify(formatsRepository, Mockito.times(1)).save(updatedFormat);
        Assertions.assertNotNull(formatsService.findByName("Новое имя формата").orElse(null));
        Assertions.assertNull(formatsService.findByName(name).orElse(null));
    }

    @Test
    void delete() {
        String name = FORMAT_1.getName();
        formatsService.delete(name);

        Mockito.verify(formatsRepository, Mockito.times(1)).deleteByName(name);
        Assertions.assertNull(formatsService.findByName(name).orElse(null));
    }
}
