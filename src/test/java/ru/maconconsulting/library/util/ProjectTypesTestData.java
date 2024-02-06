package ru.maconconsulting.library.util;

import ru.maconconsulting.library.dto.projectfields.ProjectTypeDTO;
import ru.maconconsulting.library.models.projectfields.ProjectType;

public class ProjectTypesTestData {

    public static final ProjectType PROJECT_TYPE_1 = new ProjectType("Маркетинг");
    public static final ProjectType PROJECT_TYPE_2 = new ProjectType("Бизнес план");
    public static final ProjectType PROJECT_TYPE_3 = new ProjectType("Разработка концепции");

    public static final ProjectTypeDTO PROJECT_TYPE_DTO_1 = new ProjectTypeDTO("Маркетинг");
}
