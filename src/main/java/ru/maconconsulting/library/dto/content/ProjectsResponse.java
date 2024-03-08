package ru.maconconsulting.library.dto.content;

import java.util.List;

public class ProjectsResponse {

    private List<ProjectDTO> projects;

    public ProjectsResponse(List<ProjectDTO> projects) {
        this.projects = projects;
    }

    public List<ProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDTO> projects) {
        this.projects = projects;
    }
}
