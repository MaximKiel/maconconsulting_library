package ru.maconconsulting.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maconconsulting.library.models.Project;
import ru.maconconsulting.library.repositories.ProjectsRepository;
import ru.maconconsulting.library.utils.SearchProject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProjectsService {

    public static final String SPLIT_FOR_SEARCH = ", ";

    private final ProjectsRepository projectsRepository;

    @Autowired
    public ProjectsService(ProjectsRepository projectsRepository) {
        this.projectsRepository = projectsRepository;
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
        projectsRepository.save(project);
    }

    @Transactional
    public void update(String number, Project updatedProject) {
        if (findByNumber(number).isPresent()) {
            updatedProject.setCreatedAt(findByNumber(number).get().getCreatedAt());
            updatedProject.setType(findByNumber(number).get().getType());
            projectsRepository.save(updatedProject);
        }
    }

    @Transactional
    public void delete(String number) {
        projectsRepository.deleteByNumber(number);
    }

    public List<Project> search(SearchProject searchProject) {
        List<Project> result = findAll();
        if (searchProject.getYear() != 0) {
            result = searchElement(result, p -> p.getYear().equals(searchProject.getYear()));
        }
        if (!searchProject.getRelevance().equals("")) {
            result = searchElement(result, p -> searchPluralString(p.getRelevance(), searchProject.getRelevance()));
        }
        if (!searchProject.getTitle().equals("")) {
            result = searchElement(result, p -> p.getTitle().equalsIgnoreCase(searchProject.getTitle()));
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
        if (!searchProject.getSegment().equals("")) {
            result = searchElement(result, p -> searchPluralString(p.getSegments(), searchProject.getSegment()));
        }
        if (searchProject.getType() != null) {
            result = searchElement(result, p -> p.getType().getName().equals(searchProject.getType().getName()));
        }
        if (!searchProject.getFormat().equals("")) {
            result = searchElement(result, p -> p.getFormats().equalsIgnoreCase(searchProject.getFormat()));
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
}
