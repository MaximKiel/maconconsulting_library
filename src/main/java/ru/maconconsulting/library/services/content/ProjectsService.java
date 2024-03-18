package ru.maconconsulting.library.services.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maconconsulting.library.models.content.Project;
import ru.maconconsulting.library.models.parameters.*;
import ru.maconconsulting.library.repositories.content.ProjectsRepository;
import ru.maconconsulting.library.services.parameters.FormatsService;
import ru.maconconsulting.library.services.parameters.KeyWordsService;
import ru.maconconsulting.library.services.parameters.SegmentsService;
import ru.maconconsulting.library.services.parameters.TypesService;
import ru.maconconsulting.library.utils.search.SearchProject;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.maconconsulting.library.services.content.CommonContentService.*;

@Service
@Transactional(readOnly = true)
public class ProjectsService {

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
        if (findByNumber(number).isPresent() && currentType.isPresent()) {
            updatedProject.setCreatedAt(findByNumber(number).get().getCreatedAt());
            updatedProject.setType(currentType.get());
            updatedProject.setSegments(enrichListField(segmentsService, updatedProject));
            updatedProject.setFormats(enrichListField(formatsService, updatedProject));
            if (updatedProject.getKeyWords() != null) {
                updatedProject.setKeyWords(enrichListField(keyWordsService, updatedProject));
            } else {
                updatedProject.setKeyWords(null);
            }
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
        if (!searchProject.getRelevance().trim().equals("")) {
            result = searchElement(result, p -> p.getRelevance() != null && !p.getRelevance().equals("") && p.getRelevance().toLowerCase().contains(searchProject.getRelevance().trim().toLowerCase()));
        }
        if (!searchProject.getTitle().trim().equals("")) {
            result = searchElement(result, p -> p.getTitle().toLowerCase().contains(searchProject.getTitle().trim().toLowerCase()));
        }
        if (!searchProject.getClient().trim().equals("")) {
            result = searchElement(result, p -> p.getClient().toLowerCase().contains(searchProject.getClient().trim().toLowerCase()));
        }
        if (!searchProject.getLocation().trim().equals("")) {
            result = searchElement(result, p -> p.getLocation().toLowerCase().contains(searchProject.getLocation().trim().toLowerCase()));
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

    private void enrichProject(Project project) {
        project.setCreatedAt(LocalDateTime.now());
        project.setType(typesService.findByName(project.getType().getName()).orElseThrow());
        project.setSegments(enrichListField(segmentsService, project));
        project.setFormats(enrichListField(formatsService, project));
        project.setKeyWords(enrichListField(keyWordsService, project));
    }
}
