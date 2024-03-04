package ru.maconconsulting.library.util.parameters;

import ru.maconconsulting.library.dto.parameters.FormatDTO;
import ru.maconconsulting.library.models.parameters.Format;

public class FormatsTestData {

    public static final Format PROJECT_FORMAT_1 = new Format("Word");
    public static final Format PROJECT_FORMAT_2 = new Format("Excel");
    public static final Format PROJECT_FORMAT_3 = new Format("Publisher");

    public static final FormatDTO PROJECT_FORMAT_DTO_1 = new FormatDTO("Word");
}
