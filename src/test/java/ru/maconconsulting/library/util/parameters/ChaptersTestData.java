package ru.maconconsulting.library.util.parameters;

import ru.maconconsulting.library.dto.parameters.ChapterDTO;
import ru.maconconsulting.library.models.parameters.Chapter;

public class ChaptersTestData {

    public static final Chapter CHAPTER_1 = new Chapter("Маркетинговый анализ");
    public static final Chapter CHAPTER_2 = new Chapter("Прогноз доходной части");
    public static final Chapter CHAPTER_3 = new Chapter("Разработка концепции");

    public static final ChapterDTO CHAPTER_DTO_1 = new ChapterDTO("Маркетинговый анализ");
}
