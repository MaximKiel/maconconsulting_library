package ru.maconconsulting.library.util.content;

import ru.maconconsulting.library.models.content.Publication;

import java.util.Set;

import static ru.maconconsulting.library.util.parameters.FormatsTestData.FORMAT_1;
import static ru.maconconsulting.library.util.parameters.SegmentsTestData.SEGMENT_1;
import static ru.maconconsulting.library.util.parameters.TypesOfPublicationTestData.TYPE_OF_PUBLICATION_1;

public class PublicationsTestData {

    public static final Publication PUBLICATION_1 = new Publication("Тестовый материал 1", "relatedProjectTitle", "test annotation",
            "test source", 2024, "01.2024", "/test/publ/1", "Россия, Краснодарский край, Краснодар",
            Set.of(TYPE_OF_PUBLICATION_1), Set.of(SEGMENT_1), Set.of(FORMAT_1));

    public static final Publication PUBLICATION_2 = new Publication("Тестовый материал 2", "relatedProjectTitle", "test annotation",
            "test source", 2024, "01.2024", "/test/publ/2", "Россия, Краснодарский край, Краснодар",
            Set.of(TYPE_OF_PUBLICATION_1), Set.of(SEGMENT_1), Set.of(FORMAT_1));

    public static final Publication PUBLICATION_3 = new Publication("Тестовый материал 3", "relatedProjectTitle", "test annotation",
            "test source", 2023, "12.2023", "/test/publ/3", "Россия, Краснодарский край, Краснодар",
            Set.of(TYPE_OF_PUBLICATION_1), Set.of(SEGMENT_1), Set.of(FORMAT_1));
}
