package ru.maconconsulting.library.util.content;

import ru.maconconsulting.library.models.content.Project;

import java.util.List;

import static ru.maconconsulting.library.util.parameters.ChaptersTestData.CHAPTER_1;
import static ru.maconconsulting.library.util.parameters.FormatsTestData.FORMAT_1;
import static ru.maconconsulting.library.util.parameters.SegmentsTestData.SEGMENT_1;

public class ProjectsTestData {

    public static final Project PROJECT_1 = new Project(2023, "12.2023", "23101Р_Тестовый проект 1", "Клиент 1",
            "Россия, Краснодарский край, Анапа, Сочи", List.of(CHAPTER_1), List.of(SEGMENT_1),
            List.of(FORMAT_1), "Доверительное управление", "Анализ оценок ЕРЗ");

    public static final Project PROJECT_2 = new Project(2023, "12.2023", "23102Р_Тестовый проект 2", "Клиент 2",
            "Армения, Ереван", List.of(CHAPTER_1), List.of(SEGMENT_1),
            List.of(FORMAT_1), "Доверительное управление", "Анализ оценок ЕРЗ");

    public static final Project PROJECT_3 = new Project(2023, "12.2023", "23103Р_Тестовый проект 3", "Клиент 3",
            "Россия, Республика Дагестан", List.of(CHAPTER_1), List.of(SEGMENT_1),
            List.of(FORMAT_1), "Доверительное управление", "Анализ оценок ЕРЗ");
}
