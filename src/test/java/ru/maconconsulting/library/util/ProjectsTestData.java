package ru.maconconsulting.library.util;

import ru.maconconsulting.library.models.Project;

public class ProjectsTestData {

    public static final Project PROJECT_1 = new Project("23101", 2023, "12.2023", "23101Р_Тестовый проект 1", "Клиент 1",
            "Россия", "Краснодарский край", "Анапа, Сочи", "МЖС, Гостиницы",
            "Маркетинг", "Word", "Доверительное управление");

    public static final Project PROJECT_2 = new Project("23102", 2023, "12.2023", "23102Р_Тестовый проект 2", "Клиент 2",
            "Армения", "", "Ереван", "МЖС, Бизнес центры, Торговые центры",
            "Концепция", "Word", "Бизнес парки, ВДНХ");

    public static final Project PROJECT_3 = new Project("23103", 2023, "12.2023", "23103Р_Тестовый проект 3", "Клиент 3",
            "Россия", "Республика Дагестан", "", "Винодельни",
            "Концепция", "Word", "Винодельни, виноградники, энотуризм");
}
