package ru.maconconsulting.library.services.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maconconsulting.library.models.content.Project;
import ru.maconconsulting.library.models.parameters.*;
import ru.maconconsulting.library.repositories.content.ProjectsRepository;
import ru.maconconsulting.library.services.parameters.*;
import ru.maconconsulting.library.utils.search.SearchProject;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProjectsService {

    private final ProjectsRepository projectsRepository;
    private final TypesService typesService;
    private final SegmentsService segmentsService;
    private final FormatsService formatsService;

    @Autowired
    public ProjectsService(ProjectsRepository projectsRepository, TypesService typesService, SegmentsService segmentsService, FormatsService formatsService) {
        this.projectsRepository = projectsRepository;
        this.typesService = typesService;
        this.segmentsService = segmentsService;
        this.formatsService = formatsService;
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
        project.setCreatedAt(LocalDateTime.now());
        project.setType(typesService.findByName(project.getType().getName()).orElseThrow());
        project.setSegments(enrichSegments(segmentsService, project));
        project.setFormats(enrichFormats(formatsService, project));
        projectsRepository.save(project);
    }

    @Transactional
    public void update(String number, Project updatedProject) {
        if (findByNumber(number).isPresent()) {
            updatedProject.setCreatedAt(findByNumber(number).get().getCreatedAt());
            updatedProject.setType(typesService.findByName(updatedProject.getType().getName()).orElseThrow());
            updatedProject.setSegments(enrichSegments(segmentsService, updatedProject));
            updatedProject.setFormats(enrichFormats(formatsService, updatedProject));
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
        if (!searchProject.getKeyWord().trim().equals("")) {
            result = searchElement(result, p -> p.getKeyWords().toLowerCase().contains(searchProject.getKeyWord().trim().toLowerCase()));
        }
        return result;
    }

    private List<Project> searchElement(List<Project> source, Predicate<Project> predicate) {
        return source.stream().filter(predicate).collect(Collectors.toList());
    }

    private List<Segment> enrichSegments(SegmentsService service, Project project) {
        List<Segment> entities = new ArrayList<>();
        for (Segment f : project.getSegments()) {
            entities.add(service.findByName(f.getName()).orElseThrow());
        }
        return entities;
    }

    private List<Format> enrichFormats(FormatsService service, Project project) {
        List<Format> entities = new ArrayList<>();
        for (Format f : project.getFormats()) {
            entities.add(service.findByName(f.getName()).orElseThrow());
        }
        return entities;
    }
}
