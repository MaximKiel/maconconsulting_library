package ru.maconconsulting.library.util.content;

import ru.maconconsulting.library.models.content.Project;

import java.util.Set;

import static ru.maconconsulting.library.util.parameters.TypesTestData.*;
import static ru.maconconsulting.library.util.parameters.FormatsTestData.*;
import static ru.maconconsulting.library.util.parameters.SegmentsTestData.*;

public class ProjectsTestData {

    public static final Project PROJECT_1 = new Project(2021, "06.2021", "21101Р_Тестовый проект 1", "Клиент 1",
            "Россия, Краснодарский край, Анапа, Сочи", Set.of(TYPE_2), Set.of(SEGMENT_2),
            Set.of(FORMAT_2), "Эксплуатируемая кровля", "");

    public static final Project PROJECT_2 = new Project(2022, "12.2022", "22102Р_Тестовый проект 2", "Клиент 2",
            "Армения, Ереван", Set.of(TYPE_1), Set.of(SEGMENT_1),
            Set.of(FORMAT_1), "Доверительное управление", "Анализ оценок ЕРЗ");

    public static final Project PROJECT_3 = new Project(2023, "12.2023", "23103Р_Тестовый проект 3", "Клиент 3",
            "Россия, Республика Дагестан", Set.of(TYPE_1), Set.of(SEGMENT_1),
            Set.of(FORMAT_1), "Доверительное управление", "");
}
