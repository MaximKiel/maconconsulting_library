package ru.maconconsulting.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maconconsulting.library.models.Publication;
import ru.maconconsulting.library.models.parameters.Format;
import ru.maconconsulting.library.models.parameters.KeyWord;
import ru.maconconsulting.library.models.parameters.Segment;
import ru.maconconsulting.library.repositories.PublicationsRepository;
import ru.maconconsulting.library.services.parameters.FormatsService;
import ru.maconconsulting.library.services.parameters.KeyWordsService;
import ru.maconconsulting.library.services.parameters.SegmentsService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PublicationsService {

    public static final String SPLIT_FOR_SEARCH = ", ";
    private final PublicationsRepository publicationsRepository;
    private final SegmentsService segmentsService;
    private final FormatsService formatsService;
    private final KeyWordsService keyWordsService;

    @Autowired
    public PublicationsService(PublicationsRepository publicationsRepository, SegmentsService segmentsService, FormatsService formatsService, KeyWordsService keyWordsService) {
        this.publicationsRepository = publicationsRepository;
        this.segmentsService = segmentsService;
        this.formatsService = formatsService;
        this.keyWordsService = keyWordsService;
    }

    public List<Publication> findAll() {
        return publicationsRepository.findAll();
    }

    public Optional<Publication> findByTitle(String title) {
        return publicationsRepository.findByTitle(title);
    }

    @Transactional
    public void save(Publication publication) {
        enrichPublication(publication);
        publicationsRepository.save(publication);
    }

    @Transactional
    public void update(String title, Publication updatedPublication) {
        if (findByTitle(title).isPresent()) {
            updatedPublication.setId(findByTitle(title).get().getId());
            updatedPublication.setCreatedAt(findByTitle(title).get().getCreatedAt());
            if (updatedPublication.getSegments() != null) {
                updatedPublication.setSegments(enrichListField(segmentsService, updatedPublication));
            } else {
                updatedPublication.setSegments(null);
            }

            if (updatedPublication.getFormats() != null) {
                updatedPublication.setFormats(enrichListField(formatsService, updatedPublication));
            } else {
                updatedPublication.setFormats(null);
            }

            if (updatedPublication.getKeyWords() != null) {
                updatedPublication.setKeyWords(enrichListField(keyWordsService, updatedPublication));
            } else {
                updatedPublication.setKeyWords(null);
            }
            publicationsRepository.save(updatedPublication);
        }
    }

    @Transactional
    public void delete(String title) {
        publicationsRepository.deleteByTitle(title);
    }

    private void enrichPublication(Publication publication) {
        publication.setCreatedAt(LocalDateTime.now());
        publication.setSegments(enrichListField(segmentsService, publication));
        publication.setFormats(enrichListField(formatsService, publication));
        publication.setKeyWords(enrichListField(keyWordsService, publication));

    }

    private List<Segment> enrichListField(SegmentsService service, Publication publication) {
        List<Segment> segments = new ArrayList<>();
        for (Segment s : publication.getSegments()) {
            segments.add(service.findByName(s.getName()).orElseThrow());
        }
        return segments;
    }

    private List<Format> enrichListField(FormatsService service, Publication publication) {
        List<Format> formats = new ArrayList<>();
        for (Format f : publication.getFormats()) {
            formats.add(service.findByName(f.getName()).orElseThrow());
        }
        return formats;
    }

    private List<KeyWord> enrichListField(KeyWordsService service, Publication publication) {
        List<KeyWord> keyWords = new ArrayList<>();
        for (KeyWord k : publication.getKeyWords()) {
            keyWords.add(service.findByName(k.getName()).orElseThrow());
        }
        return keyWords;
    }
}
