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
    private final ChaptersService chaptersService;
    private final SegmentsService segmentsService;
    private final FormatsService formatsService;

    @Autowired
    public ProjectsService(ProjectsRepository projectsRepository, ChaptersService chaptersService, SegmentsService segmentsService, FormatsService formatsService) {
        this.projectsRepository = projectsRepository;
        this.chaptersService = chaptersService;
        this.segmentsService = segmentsService;
        this.formatsService = formatsService;
    }

    public List<Project> findAll() {
        return projectsRepository.findAll();
    }

    public Optional<Project> findByTitle(String title) {
        return projectsRepository.findByTitle(title);
    }

    public Optional<Project> findById(Integer id) {
        return projectsRepository.findById(id);
    }

    @Transactional
    public void save(Project project) {
        project.setCreatedAt(LocalDateTime.now());
        project.setChapters(enrichChapters(chaptersService, project));
        project.setSegments(enrichSegments(segmentsService, project));
        project.setFormats(enrichFormats(formatsService, project));
        projectsRepository.save(project);
    }

    @Transactional
    public void update(Integer id, Project updatedProject) {
        if (findById(id).isPresent()) {
            updatedProject.setId(findById(id).get().getId());
            updatedProject.setCreatedAt(findById(id).get().getCreatedAt());
            updatedProject.setChapters(updatedProject.getChapters() != null ?
                    enrichChapters(chaptersService, updatedProject) : null);
            updatedProject.setSegments(updatedProject.getSegments() != null ?
                    enrichSegments(segmentsService, updatedProject) : null);
            updatedProject.setFormats(updatedProject.getFormats() != null ?
                    enrichFormats(formatsService, updatedProject) : null);
            projectsRepository.save(updatedProject);
        }
    }

    @Transactional
    public void delete(Integer id) {
        projectsRepository.deleteById(id);
    }

    public List<Project> searchProject(SearchProject searchProject) {
        List<Project> projects = findAll().stream().sorted(Comparator.comparing(Project::getTitle)).collect(Collectors.toList());
        return search(projects, searchProject);
    }

    public List<Project> searchMethodology(SearchProject searchProject) {
        List<Project> projects = findAll().stream().filter(p -> p.getMethodology() != null && !p.getMethodology().trim().equals("")).sorted(Comparator.comparing(Project::getTitle)).collect(Collectors.toList());
        return search(projects, searchProject);
    }

    private List<Project> search(List<Project> projects, SearchProject searchProject) {
        if (searchProject.getYear() != 0) {
            projects = searchElement(projects, p -> p.getYear().equals(searchProject.getYear()));
        }
        if (searchProject.getRelevance() != null && !searchProject.getRelevance().trim().equals("")) {
            projects = searchElement(projects, p -> p.getRelevance() != null && !p.getRelevance().equals("") && p.getRelevance().toLowerCase().contains(searchProject.getRelevance().trim().toLowerCase()));
        }
        if (searchProject.getTitle() != null && !searchProject.getTitle().trim().equals("")) {
            projects = searchElement(projects, p -> p.getTitle().toLowerCase().contains(searchProject.getTitle().trim().toLowerCase()));
        }
        if (searchProject.getClient() != null && !searchProject.getClient().trim().equals("")) {
            projects = searchElement(projects, p -> p.getClient() != null && !p.getClient().equals("") && p.getClient().toLowerCase().contains(searchProject.getClient().trim().toLowerCase()));
        }
        if (searchProject.getLocation() != null && !searchProject.getLocation().trim().equals("")) {
            projects = searchElement(projects, p -> p.getLocation().toLowerCase().contains(searchProject.getLocation().trim().toLowerCase()));
        }
        if (searchProject.getChapter() != null && !searchProject.getChapter().getName().equals("")) {
            projects = searchElement(projects, p -> {
                List<String> chapterNames = p.getChapters().stream().map(AbstractParameterEntity::getName).toList();
                return chapterNames.stream().anyMatch(n -> n.equals(searchProject.getChapter().getName()));
            });
        }
        if (searchProject.getSegment() != null && !searchProject.getSegment().getName().equals("")) {
            projects = searchElement(projects, p -> {
                List<String> segmentNames = p.getSegments().stream().map(AbstractParameterEntity::getName).toList();
                return segmentNames.stream().anyMatch(n -> n.equals(searchProject.getSegment().getName()));
            });
        }
        if (searchProject.getFormat() != null && !searchProject.getFormat().getName().equals("")) {
            projects = searchElement(projects, p -> {
                List<String> formatNames = p.getFormats().stream().map(AbstractParameterEntity::getName).toList();
                return formatNames.stream().anyMatch(n -> n.equals(searchProject.getFormat().getName()));
            });
        }
        if (searchProject.getKeyWord() != null && !searchProject.getKeyWord().trim().equals("")) {
            projects = searchElement(projects, p -> p.getKeyWords() != null && !p.getKeyWords().equals("") && p.getKeyWords().toLowerCase().contains(searchProject.getKeyWord().trim().toLowerCase()));
        }
        if (searchProject.getMethodology() != null && !searchProject.getMethodology().trim().equals("")) {
            projects = searchElement(projects, p -> p.getMethodology() != null && !p.getMethodology().equals("") && p.getMethodology().toLowerCase().contains(searchProject.getMethodology().trim().toLowerCase()));
        }
        return projects;
    }

    private List<Project> searchElement(List<Project> source, Predicate<Project> predicate) {
        return source.stream().filter(predicate).collect(Collectors.toList());
    }

    private List<Chapter> enrichChapters(ChaptersService service, Project project) {
        List<Chapter> entities = new ArrayList<>();
        for (Chapter c : project.getChapters()) {
            entities.add(service.findByName(c.getName()).orElseThrow());
        }
        return entities;
    }

    private List<Segment> enrichSegments(SegmentsService service, Project project) {
        List<Segment> entities = new ArrayList<>();
        for (Segment s : project.getSegments()) {
            entities.add(service.findByName(s.getName()).orElseThrow());
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
