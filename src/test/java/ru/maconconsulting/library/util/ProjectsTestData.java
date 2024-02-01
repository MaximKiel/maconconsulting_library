package ru.maconconsulting.library.util;

import ru.maconconsulting.library.dto.ProjectTypeDTO;
import ru.maconconsulting.library.models.Project;
import ru.maconconsulting.library.models.ProjectType;

public class ProjectsTestData {

    public static final ProjectType PROJECT_TYPE = new ProjectType(3, "Маркетинг");

    public static final ProjectTypeDTO PROJECT_TYPE_DTO = new ProjectTypeDTO("Маркетинг");

    public static final Project PROJECT_1 = new Project("23101", 2023, "12.2023", "23101Р_Тестовый проект 1", "Клиент 1",
            "Россия", "Краснодарский край", "Анапа, Сочи", "МЖС, Гостиницы",
            PROJECT_TYPE, "Word", "Доверительное управление");

    public static final Project PROJECT_2 = new Project("23102", 2023, "12.2023", "23102Р_Тестовый проект 2", "Клиент 2",
            "Армения", "", "Ереван", "МЖС, Бизнес центры, Торговые центры",
            PROJECT_TYPE, "Word", "Бизнес парки, ВДНХ");

    public static final Project PROJECT_3 = new Project("23103", 2023, "12.2023", "23103Р_Тестовый проект 3", "Клиент 3",
            "Россия", "Республика Дагестан", "", "Винодельни",
            PROJECT_TYPE, "Word", "Винодельни, виноградники, энотуризм");
}
