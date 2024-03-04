package ru.maconconsulting.library.util.parameters;

import ru.maconconsulting.library.dto.parameters.TypeDTO;
import ru.maconconsulting.library.models.parameters.Type;

public class TypesTestData {

    public static final Type PROJECT_TYPE_1 = new Type("Маркетинг");
    public static final Type PROJECT_TYPE_2 = new Type("Бизнес план");
    public static final Type PROJECT_TYPE_3 = new Type("Разработка концепции");

    public static final TypeDTO PROJECT_TYPE_DTO_1 = new TypeDTO("Маркетинг");
}
