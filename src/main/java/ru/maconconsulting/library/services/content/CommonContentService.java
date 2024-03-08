package ru.maconconsulting.library.services.content;

import ru.maconconsulting.library.models.content.Project;
import ru.maconconsulting.library.models.content.Publication;
import ru.maconconsulting.library.models.parameters.Format;
import ru.maconconsulting.library.models.parameters.KeyWord;
import ru.maconconsulting.library.models.parameters.Segment;
import ru.maconconsulting.library.services.parameters.FormatsService;
import ru.maconconsulting.library.services.parameters.KeyWordsService;
import ru.maconconsulting.library.services.parameters.SegmentsService;

import java.util.ArrayList;
import java.util.List;

public class CommonContentService {

    public static final String SPLIT_FOR_SEARCH = ", ";

    public static List<Segment> enrichListField(SegmentsService service, Publication publication) {
        List<Segment> segments = new ArrayList<>();
        for (Segment s : publication.getSegments()) {
            segments.add(service.findByName(s.getName()).orElseThrow());
        }
        return segments;
    }

    public static List<Format> enrichListField(FormatsService service, Publication publication) {
        List<Format> formats = new ArrayList<>();
        for (Format f : publication.getFormats()) {
            formats.add(service.findByName(f.getName()).orElseThrow());
        }
        return formats;
    }

    public static List<KeyWord> enrichListField(KeyWordsService service, Publication publication) {
        List<KeyWord> keyWords = new ArrayList<>();
        for (KeyWord k : publication.getKeyWords()) {
            keyWords.add(service.findByName(k.getName()).orElseThrow());
        }
        return keyWords;
    }

    public static List<Segment> enrichListField(SegmentsService service, Project project) {
        List<Segment> entities = new ArrayList<>();
        for (Segment f : project.getSegments()) {
            entities.add(service.findByName(f.getName()).orElseThrow());
        }
        return entities;
    }

    public static List<Format> enrichListField(FormatsService service, Project project) {
        List<Format> entities = new ArrayList<>();
        for (Format f : project.getFormats()) {
            entities.add(service.findByName(f.getName()).orElseThrow());
        }
        return entities;
    }

    public static List<KeyWord> enrichListField(KeyWordsService service, Project project) {
        List<KeyWord> entities = new ArrayList<>();
        for (KeyWord k : project.getKeyWords()) {
            entities.add(service.findByName(k.getName()).orElseThrow());
        }
        return entities;
    }

    public static Boolean searchPluralString(String pluralString, String searchString) {
        if (pluralString == null) {
            return false;
        }
        String[] strings = pluralString.split(SPLIT_FOR_SEARCH);
        for (String s : strings) {
            if (s.equalsIgnoreCase(searchString)) {
                return true;
            }
        }
        return false;
    }
}
