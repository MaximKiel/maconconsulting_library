package ru.maconconsulting.library.util.parameters;

import ru.maconconsulting.library.dto.parameters.TypeOfPublicationDTO;
import ru.maconconsulting.library.models.parameters.TypeOfPublication;

public class TypesOfPublicationTestData {

    public static final TypeOfPublication TYPE_OF_PUBLICATION_1 = new TypeOfPublication("Архитектурная концепция");
    public static final TypeOfPublication TYPE_OF_PUBLICATION_2 = new TypeOfPublication("Бизнес-план");
    public static final TypeOfPublication TYPE_OF_PUBLICATION_3 = new TypeOfPublication("Маркетинговое исследование");

    public static final TypeOfPublicationDTO TYPE_OF_PUBLICATION_DTO_1 = new TypeOfPublicationDTO("Архитектурная концепция");
}