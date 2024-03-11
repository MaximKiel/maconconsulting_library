package ru.maconconsulting.library.util.content;

import ru.maconconsulting.library.models.content.Project;

import java.util.List;

import static ru.maconconsulting.library.util.parameters.FormatsTestData.PROJECT_FORMAT_1;
import static ru.maconconsulting.library.util.parameters.KeyWordsTestData.PROJECT_KEY_WORD_1;
import static ru.maconconsulting.library.util.parameters.SegmentsTestData.PROJECT_SEGMENT_1;
import static ru.maconconsulting.library.util.parameters.TypesTestData.PROJECT_TYPE_1;

public class ProjectsTestData {

    public static final Project PROJECT_1 = new Project("23101", 2023, "12.2023", "23101Р_Тестовый проект 1", "Клиент 1",
            "Россия, Краснодарский край, Анапа, Сочи", List.of(PROJECT_SEGMENT_1),
            PROJECT_TYPE_1, List.of(PROJECT_FORMAT_1), List.of(PROJECT_KEY_WORD_1));

    public static final Project PROJECT_2 = new Project("23102", 2023, "12.2023", "23102Р_Тестовый проект 2", "Клиент 2",
            "Армения, Ереван", List.of(PROJECT_SEGMENT_1),
            PROJECT_TYPE_1, List.of(PROJECT_FORMAT_1), List.of(PROJECT_KEY_WORD_1));

    public static final Project PROJECT_3 = new Project("23103", 2023, "12.2023", "23103Р_Тестовый проект 3", "Клиент 3",
            "Россия, Республика Дагестан", List.of(PROJECT_SEGMENT_1),
            PROJECT_TYPE_1, List.of(PROJECT_FORMAT_1), List.of(PROJECT_KEY_WORD_1));
}
