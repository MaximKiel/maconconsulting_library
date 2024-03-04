package ru.maconconsulting.library.util.parameters;

import ru.maconconsulting.library.dto.parameters.ProjectFormatDTO;
import ru.maconconsulting.library.models.parameters.ProjectFormat;

public class ProjectFormatsTestData {

    public static final ProjectFormat PROJECT_FORMAT_1 = new ProjectFormat("Word");
    public static final ProjectFormat PROJECT_FORMAT_2 = new ProjectFormat("Excel");
    public static final ProjectFormat PROJECT_FORMAT_3 = new ProjectFormat("Publisher");

    public static final ProjectFormatDTO PROJECT_FORMAT_DTO_1 = new ProjectFormatDTO("Word");
}
