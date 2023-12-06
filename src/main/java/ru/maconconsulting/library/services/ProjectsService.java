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

    public Optional<Project> findByNumber(int number) {
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
    public void update(int number, Project updatedProject) {
        if (findByNumber(number).isPresent()) {
            updatedProject.setCreatedAt(findByNumber(number).get().getCreatedAt());
            projectsRepository.save(updatedProject);
        }
    }

    @Transactional
    public void delete(int number) {
        projectsRepository.deleteById(number);
    }

    public List<Project> search(SearchProject searchProject) {
        List<Project> result = findAll();
        if (searchProject.getNumber() != 0) {
            result = searchElement(result, p -> p.getNumber().equals(searchProject.getNumber()));
        }
        if (searchProject.getYear() != 0) {
            result = searchElement(result, p -> p.getYear().equals(searchProject.getYear()));
        }
        if (!searchProject.getTitle().equals("")) {
            result = searchElement(result, p -> p.getTitle().equalsIgnoreCase(searchProject.getTitle()));
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
        if (!searchProject.getType().equals("")) {
            result = searchElement(result, p -> p.getType().equalsIgnoreCase(searchProject.getType()));
        }
        if (!searchProject.getTag().equals("")) {
            result = searchElement(result, p -> searchPluralString(p.getTags(), searchProject.getTag()));
        }
        return result;
    }

    private void enrichProject(Project project) {
        project.setCreatedAt(LocalDateTime.now());
    }

    private List<Project> searchElement(List<Project> source, Predicate<Project> predicate) {
        return source.stream().filter(predicate).collect(Collectors.toList());
    }

    private Boolean searchPluralString(String pluralString, String searchString) {
        String[] strings = pluralString.split(SPLIT_FOR_SEARCH);
        for (String s : strings) {
            if (s.equalsIgnoreCase(searchString)) {
                return true;
            }
        }
        return false;
    }
}
