package ru.maconconsulting.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maconconsulting.library.models.Project;
import ru.maconconsulting.library.models.parameters.*;
import ru.maconconsulting.library.repositories.ProjectsRepository;
import ru.maconconsulting.library.services.parameters.FormatsService;
import ru.maconconsulting.library.services.parameters.KeyWordsService;
import ru.maconconsulting.library.services.parameters.SegmentsService;
import ru.maconconsulting.library.services.parameters.TypesService;
import ru.maconconsulting.library.utils.SearchProject;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProjectsService {

    public static final String SPLIT_FOR_SEARCH = ", ";

    private final ProjectsRepository projectsRepository;
    private final TypesService typesService;
    private final SegmentsService segmentsService;
    private final FormatsService formatsService;
    private final KeyWordsService keyWordsService;

    @Autowired
    public ProjectsService(ProjectsRepository projectsRepository, TypesService typesService, SegmentsService segmentsService, FormatsService formatsService, KeyWordsService keyWordsService) {
        this.projectsRepository = projectsRepository;
        this.typesService = typesService;
        this.segmentsService = segmentsService;
        this.formatsService = formatsService;
        this.keyWordsService = keyWordsService;
    }

    public List<Project> findAll() {
        return projectsRepository.findAll();
    }

    public Optional<Project> findByNumber(String number) {
        return projectsRepository.findByNumber(number);
    }

    public Optional<Project> findByTitle(String title) {
        return projectsRepository.findByTitle(title);
    }

    @Transactional
    public void save(Project project) {
        enrichProject(project);
        projectsRepository.save(project);
    }

    @Transactional
    public void update(String number, Project updatedProject) {
        Optional<Type> currentType = typesService.findByName(updatedProject.getType().getName());
        List<Segment> currentSegments = new ArrayList<>();
        for (Segment s : updatedProject.getSegments()) {
            currentSegments.add(segmentsService.findByName(s.getName()).get());
        }
        List<Format> currentFormats = new ArrayList<>();
        for (Format f : updatedProject.getFormats()) {
            currentFormats.add(formatsService.findByName(f.getName()).get());
        }
        if (updatedProject.getKeyWords() != null) {
            List<KeyWord> currentKeyWord = new ArrayList<>();
            for (KeyWord k : updatedProject.getKeyWords()) {
                currentKeyWord.add(keyWordsService.findByName(k.getName()).get());
            }
            updatedProject.setKeyWords(currentKeyWord);
        } else {
            updatedProject.setKeyWords(null);
        }
        if (findByNumber(number).isPresent() && currentType.isPresent()) {
            updatedProject.setCreatedAt(findByNumber(number).get().getCreatedAt());
            updatedProject.setType(currentType.get());
            updatedProject.setSegments(currentSegments);
            updatedProject.setFormats(currentFormats);
            projectsRepository.save(updatedProject);
        }
    }

    @Transactional
    public void delete(String number) {
        projectsRepository.deleteByNumber(number);
    }

    public List<Project> search(SearchProject searchProject) {
        List<Project> result = findAll().stream().sorted(Comparator.comparing(Project::getNumber)).collect(Collectors.toList());
        if (searchProject.getYear() != 0) {
            result = searchElement(result, p -> p.getYear().equals(searchProject.getYear()));
        }
        if (!searchProject.getRelevance().equals("")) {
            result = searchElement(result, p -> searchPluralString(p.getRelevance(), searchProject.getRelevance()));
        }
        if (!searchProject.getTitle().equals("")) {
            result = searchElement(result, p -> p.getTitle().toLowerCase().contains(searchProject.getTitle().toLowerCase()));
        }
        if (!searchProject.getClient().equals("")) {
            result = searchElement(result, p -> searchPluralString(p.getClient(), searchProject.getClient()));
        }
        if (!searchProject.getCountry().equals("")) {
            result = searchElement(result, p -> searchPluralString(p.getCountries(), searchProject.getCountry()));
        }
        if (!searchProject.getRegion().equals("")) {
            result = searchElement(result, p -> searchPluralString(p.getRegions(), searchProject.getRegion()));
        }
        if (!searchProject.getTown().equals("")) {
            result = searchElement(result, p -> searchPluralString(p.getTowns(), searchProject.getTown()));
        }
        if (searchProject.getSegment() != null && !searchProject.getSegment().getName().equals("")) {
            result = searchElement(result, p -> {
                List<String> segmentNames = p.getSegments().stream().map(AbstractParameterEntity::getName).toList();
                return segmentNames.stream().anyMatch(n -> n.equals(searchProject.getSegment().getName()));
            });
        }
        if (searchProject.getType() != null && !searchProject.getType().getName().equals("")) {
            result = searchElement(result, p -> p.getType().getName().equals(searchProject.getType().getName()));
        }
        if (searchProject.getFormat() != null && !searchProject.getFormat().getName().equals("")) {
            result = searchElement(result, p -> {
                List<String> formatNames = p.getFormats().stream().map(AbstractParameterEntity::getName).toList();
                return formatNames.stream().anyMatch(n -> n.equals(searchProject.getFormat().getName()));
            });
        }
        if (searchProject.getKeyWord() != null && !searchProject.getKeyWord().getName().equals("")) {
            result = searchElement(result, p -> {
                List<String> keyWordNames = p.getKeyWords().stream().map(AbstractParameterEntity::getName).toList();
                return keyWordNames.stream().anyMatch(n -> n.equals(searchProject.getKeyWord().getName()));
            });
        }
        return result;
    }

    private List<Project> searchElement(List<Project> source, Predicate<Project> predicate) {
        return source.stream().filter(predicate).collect(Collectors.toList());
    }

    private Boolean searchPluralString(String pluralString, String searchString) {
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

    private void enrichProject(Project project) {
        project.setCreatedAt(LocalDateTime.now());
        project.setType(typesService.findByName(project.getType().getName()).orElseThrow());
        project.setSegments(enrichListField(segmentsService, project));
        project.setFormats(enrichListField(formatsService, project));
        project.setKeyWords(enrichListField(keyWordsService, project));
    }

    private List<Segment> enrichListField(SegmentsService service, Project project) {
        List<Segment> entities = new ArrayList<>();
        for (Segment f : project.getSegments()) {
            entities.add(service.findByName(f.getName()).orElseThrow());
        }
        return entities;
    }

    private List<Format> enrichListField(FormatsService service, Project project) {
        List<Format> entities = new ArrayList<>();
        for (Format f : project.getFormats()) {
            entities.add(service.findByName(f.getName()).orElseThrow());
        }
        return entities;
    }

    private List<KeyWord> enrichListField(KeyWordsService service, Project project) {
        List<KeyWord> entities = new ArrayList<>();
        for (KeyWord k : project.getKeyWords()) {
            entities.add(service.findByName(k.getName()).orElseThrow());
        }
        return entities;
    }
}
