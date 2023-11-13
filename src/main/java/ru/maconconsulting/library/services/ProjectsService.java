package ru.maconconsulting.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maconconsulting.library.models.Project;
import ru.maconconsulting.library.repositories.ProjectsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProjectsService {

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
        updatedProject.setCreatedAt(findByNumber(number).get().getCreatedAt());
        projectsRepository.save(updatedProject);
    }

    @Transactional
    public void delete(int number) {
        projectsRepository.deleteById(number);
    }

    private void enrichProject(Project project) {
        project.setCreatedAt(LocalDateTime.now());
    }
}
