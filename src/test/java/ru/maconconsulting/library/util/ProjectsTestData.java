package ru.maconconsulting.library.util;

import ru.maconconsulting.library.models.Project;

public class ProjectsTestData {

    public static final Project PROJECT_1 = new Project(23101, "23101Р_Тестовый проект 1", "Клиент 1",
            "Россия", "Краснодарский край", "Анапа, Сочи", "МЖС, Гостиницы",
            "Маркетинг", 2023, "Доверительное управление");

    public static final Project PROJECT_2 = new Project(23102, "23102Р_Тестовый проект 2", "Клиент 2",
            "Армения", "", "Ереван", "МЖС, Бизнес центры, Торговые центры",
            "Концепция", 2023, "Бизнес парки, ВДНХ");

    public static final Project PROJECT_3 = new Project(23103, "23103Р_Тестовый проект 3", "Клиент 3",
            "Россия", "Республика Дагестан", "", "Винодельни",
            "Концепция", 2023, "Винодельни, виноградники, энотуризм");
}
