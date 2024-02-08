package ru.maconconsulting.library.util;

import ru.maconconsulting.library.models.Project;

import java.util.List;

import static ru.maconconsulting.library.util.projectfields.ProjectFormatsTestData.PROJECT_FORMAT_1;
import static ru.maconconsulting.library.util.projectfields.ProjectSegmentsTestData.PROJECT_SEGMENT_1;
import static ru.maconconsulting.library.util.projectfields.ProjectTypesTestData.PROJECT_TYPE_1;

public class ProjectsTestData {

    public static final Project PROJECT_1 = new Project("23101", 2023, "12.2023", "23101Р_Тестовый проект 1", "Клиент 1",
            "Россия", "Краснодарский край", "Анапа, Сочи", List.of(PROJECT_SEGMENT_1),
            PROJECT_TYPE_1, List.of(PROJECT_FORMAT_1), "Доверительное управление");

    public static final Project PROJECT_2 = new Project("23102", 2023, "12.2023", "23102Р_Тестовый проект 2", "Клиент 2",
            "Армения", "", "Ереван", List.of(PROJECT_SEGMENT_1),
            PROJECT_TYPE_1, List.of(PROJECT_FORMAT_1), "Бизнес парки, ВДНХ");

    public static final Project PROJECT_3 = new Project("23103", 2023, "12.2023", "23103Р_Тестовый проект 3", "Клиент 3",
            "Россия", "Республика Дагестан", "", List.of(PROJECT_SEGMENT_1),
            PROJECT_TYPE_1, List.of(PROJECT_FORMAT_1), "Винодельни, виноградники, энотуризм");
}
