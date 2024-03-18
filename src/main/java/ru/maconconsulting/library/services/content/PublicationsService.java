package ru.maconconsulting.library.services.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maconconsulting.library.models.content.Publication;
import ru.maconconsulting.library.models.parameters.AbstractParameterEntity;
import ru.maconconsulting.library.repositories.content.PublicationsRepository;
import ru.maconconsulting.library.services.parameters.FormatsService;
import ru.maconconsulting.library.services.parameters.KeyWordsService;
import ru.maconconsulting.library.services.parameters.SegmentsService;
import ru.maconconsulting.library.utils.search.SearchPublication;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.maconconsulting.library.services.content.CommonContentService.*;

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

    public Optional<Publication> findById(Integer id) {
        return publicationsRepository.findById(id);
    }

    @Transactional
    public void save(Publication publication) {
        enrichPublication(publication);
        publicationsRepository.save(publication);
    }

    @Transactional
    public void update(Integer id, Publication updatedPublication) {
        if (findById(id).isPresent()) {
            updatedPublication.setId(findById(id).get().getId());
            updatedPublication.setCreatedAt(findById(id).get().getCreatedAt());
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
    public void delete(Integer id) {
        publicationsRepository.deleteById(id);
    }

    public List<Publication> search(SearchPublication searchPublication) {
        List<Publication> result = findAll().stream().sorted(Comparator.comparing(Publication::getTitle)).collect(Collectors.toList());
        if (!searchPublication.getTitle().trim().equals("")) {
            result = searchElement(result, p -> p.getTitle().toLowerCase().contains(searchPublication.getTitle().trim().toLowerCase()));
        }
        if (!searchPublication.getAnnotation().trim().equals("")) {
            result = searchElement(result, p -> p.getAnnotation() != null && !p.getAnnotation().equals("") && p.getAnnotation().toLowerCase().contains(searchPublication.getAnnotation().trim().toLowerCase()));
        }
        if (!searchPublication.getSource().trim().equals("")) {
            result = searchElement(result, p -> p.getSource() != null && !p.getSource().equals("") && p.getSource().toLowerCase().contains(searchPublication.getSource().trim().toLowerCase()));
        }
        if (searchPublication.getYear() != null && searchPublication.getYear() != 0) {
            result = searchElement(result, p -> p.getYear() != null && p.getYear().equals(searchPublication.getYear()));
        }
        if (!searchPublication.getRelevance().trim().equals("")) {
            result = searchElement(result, p -> p.getRelevance() != null && !p.getRelevance().equals("") && p.getRelevance().toLowerCase().contains(searchPublication.getRelevance().trim().toLowerCase()));
        }
        if (!searchPublication.getLocation().trim().equals("")) {
            result = searchElement(result, p -> p.getLocation().toLowerCase().contains(searchPublication.getLocation().trim().toLowerCase()));
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
