package ru.maconconsulting.library.util.content;

import ru.maconconsulting.library.models.content.Publication;

import java.util.List;

import static ru.maconconsulting.library.util.parameters.FormatsTestData.PROJECT_FORMAT_1;
import static ru.maconconsulting.library.util.parameters.KeyWordsTestData.PROJECT_KEY_WORD_1;
import static ru.maconconsulting.library.util.parameters.SegmentsTestData.PROJECT_SEGMENT_1;

public class PublicationsTestData {

    public static final Publication PUBLICATION_1 = new Publication("Тестовый материал 1", "test annotation",
            "test source", 2024, "01.2024", "/test/publ/1", "Россия, Краснодарский край, Краснодар",
            List.of(PROJECT_SEGMENT_1), List.of(PROJECT_FORMAT_1), List.of(PROJECT_KEY_WORD_1));

    public static final Publication PUBLICATION_2 = new Publication("Тестовый материал 2", "test annotation",
            "test source", 2024, "01.2024", "/test/publ/2", "Россия, Краснодарский край, Краснодар",
            List.of(PROJECT_SEGMENT_1), List.of(PROJECT_FORMAT_1), List.of(PROJECT_KEY_WORD_1));

    public static final Publication PUBLICATION_3 = new Publication("Тестовый материал 3", "test annotation",
            "test source", 2023, "12.2023", "/test/publ/3", "Россия, Краснодарский край, Краснодар",
            List.of(PROJECT_SEGMENT_1), List.of(PROJECT_FORMAT_1), List.of(PROJECT_KEY_WORD_1));
}
