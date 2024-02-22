package ru.maconconsulting.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maconconsulting.library.models.Project;
import ru.maconconsulting.library.models.projectfields.AbstractProjectFieldEntity;
import ru.maconconsulting.library.models.projectfields.ProjectFormat;
import ru.maconconsulting.library.models.projectfields.ProjectSegment;
import ru.maconconsulting.library.models.projectfields.ProjectType;
import ru.maconconsulting.library.repositories.ProjectsRepository;
import ru.maconconsulting.library.services.projectfields.ProjectFormatsService;
import ru.maconconsulting.library.services.projectfields.ProjectSegmentsService;
import ru.maconconsulting.library.services.projectfields.ProjectTypesService;
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
    private final ProjectTypesService projectTypesService;
    private final ProjectSegmentsService projectSegmentsService;
    private final ProjectFormatsService projectFormatsService;

    @Autowired
    public ProjectsService(ProjectsRepository projectsRepository, ProjectTypesService projectTypesService, ProjectSegmentsService projectSegmentsService, ProjectFormatsService projectFormatsService) {
        this.projectsRepository = projectsRepository;
        this.projectTypesService = projectTypesService;
        this.projectSegmentsService = projectSegmentsService;
        this.projectFormatsService = projectFormatsService;
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
        Optional<ProjectType> currentType = projectTypesService.findByName(updatedProject.getType().getName());
        List<ProjectSegment> currentSegments = new ArrayList<>();
        for (ProjectSegment s : updatedProject.getSegments()) {
            currentSegments.add(projectSegmentsService.findByName(s.getName()).get());
        }
        List<ProjectFormat> currentFormats = new ArrayList<>();
        for (ProjectFormat f : updatedProject.getFormats()) {
            currentFormats.add(projectFormatsService.findByName(f.getName()).get());
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
        List<Project> result = findAll();
        result.sort(Comparator.comparing(Project::getNumber));
        if (searchProject.getYear() != 0) {
            result = searchElement(result, p -> p.getYear().equals(searchProject.getYear()));
        }
        if (!searchProject.getRelevance().equals("")) {
            result = searchElement(result, p -> searchPluralString(p.getRelevance(), searchProject.getRelevance()));
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
                List<String> segmentNames = p.getSegments().stream().map(AbstractProjectFieldEntity::getName).toList();
                return segmentNames.stream().anyMatch(n -> n.equals(searchProject.getSegment().getName()));
            });
        }
        if (searchProject.getType() != null && !searchProject.getType().getName().equals("")) {
            result = searchElement(result, p -> p.getType().getName().equals(searchProject.getType().getName()));
        }
        if (searchProject.getFormat() != null && !searchProject.getFormat().getName().equals("")) {
            result = searchElement(result, p -> {
                List<String> formatNames = p.getFormats().stream().map(AbstractProjectFieldEntity::getName).toList();
                return formatNames.stream().anyMatch(n -> n.equals(searchProject.getFormat().getName()));
            });
        }
        if (!searchProject.getTag().equals("")) {
            result = searchElement(result, p -> searchPluralString(p.getTags(), searchProject.getTag()));
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
        project.setType(projectTypesService.findByName(project.getType().getName()).orElseThrow());
        project.setSegments(enrichListField(projectSegmentsService, project));
        project.setFormats(enrichListField(projectFormatsService, project));
    }

    private List<ProjectSegment> enrichListField(ProjectSegmentsService service, Project project) {
        List<ProjectSegment> entities = new ArrayList<>();
        for (ProjectSegment f : project.getSegments()) {
            entities.add(service.findByName(f.getName()).orElseThrow());
        }
        return entities;
    }

    private List<ProjectFormat> enrichListField(ProjectFormatsService service, Project project) {
        List<ProjectFormat> entities = new ArrayList<>();
        for (ProjectFormat f : project.getFormats()) {
            entities.add(service.findByName(f.getName()).orElseThrow());
        }
        return entities;
    }
}
