package ru.maconconsulting.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maconconsulting.library.models.Publication;
import ru.maconconsulting.library.models.parameters.AbstractParameterEntity;
import ru.maconconsulting.library.repositories.PublicationsRepository;
import ru.maconconsulting.library.services.parameters.FormatsService;
import ru.maconconsulting.library.services.parameters.KeyWordsService;
import ru.maconconsulting.library.services.parameters.SegmentsService;
import ru.maconconsulting.library.utils.SearchPublication;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.maconconsulting.library.services.CommonContentService.*;

@Service
@Transactional(readOnly = true)
public class PublicationsService {

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

    public List<Publication> search(SearchPublication searchPublication) {
        List<Publication> result = findAll().stream().sorted(Comparator.comparing(Publication::getTitle)).collect(Collectors.toList());
        if (!searchPublication.getTitle().equals("")) {
            result = searchElement(result, p -> p.getTitle().toLowerCase().contains(searchPublication.getTitle().toLowerCase()));
        }
        if (!searchPublication.getAnnotation().equals("")) {
            result = searchElement(result, p -> p.getAnnotation().toLowerCase().contains(searchPublication.getAnnotation().toLowerCase()));
        }
        if (!searchPublication.getSource().equals("")) {
            result = searchElement(result, p -> p.getSource().toLowerCase().contains(searchPublication.getSource().toLowerCase()));
        }
        if (searchPublication.getYear() != 0) {
            result = searchElement(result, p -> p.getYear().equals(searchPublication.getYear()));
        }
        if (!searchPublication.getRelevance().equals("")) {
            result = searchElement(result, p -> searchPluralString(p.getRelevance(), searchPublication.getRelevance()));
        }
        if (!searchPublication.getCountry().equals("")) {
            result = searchElement(result, p -> searchPluralString(p.getCountries(), searchPublication.getCountry()));
        }
        if (!searchPublication.getRegion().equals("")) {
            result = searchElement(result, p -> searchPluralString(p.getRegions(), searchPublication.getRegion()));
        }
        if (!searchPublication.getTown().equals("")) {
            result = searchElement(result, p -> searchPluralString(p.getTowns(), searchPublication.getTown()));
        }
        if (searchPublication.getSegment() != null && !searchPublication.getSegment().getName().equals("")) {
            result = searchElement(result, p -> {
                List<String> segmentNames = p.getSegments().stream().map(AbstractParameterEntity::getName).toList();
                return segmentNames.stream().anyMatch(n -> n.equals(searchPublication.getSegment().getName()));
            });
        }
        if (searchPublication.getFormat() != null && !searchPublication.getFormat().getName().equals("")) {
            result = searchElement(result, p -> {
                List<String> formatNames = p.getFormats().stream().map(AbstractParameterEntity::getName).toList();
                return formatNames.stream().anyMatch(n -> n.equals(searchPublication.getFormat().getName()));
            });
        }
        if (searchPublication.getKeyWord() != null && !searchPublication.getKeyWord().getName().equals("")) {
            result = searchElement(result, p -> {
                List<String> keyWordNames = p.getKeyWords().stream().map(AbstractParameterEntity::getName).toList();
                return keyWordNames.stream().anyMatch(n -> n.equals(searchPublication.getKeyWord().getName()));
            });
        }
        return result;
    }

    private List<Publication> searchElement(List<Publication> source, Predicate<Publication> predicate) {
        return source.stream().filter(predicate).collect(Collectors.toList());
    }

    private void enrichPublication(Publication publication) {
        publication.setCreatedAt(LocalDateTime.now());
        publication.setSegments(enrichListField(segmentsService, publication));
        publication.setFormats(enrichListField(formatsService, publication));
        publication.setKeyWords(enrichListField(keyWordsService, publication));

    }
}
