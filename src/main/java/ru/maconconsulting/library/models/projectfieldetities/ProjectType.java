package ru.maconconsulting.library.models.projectfieldetities;

import jakarta.persistence.*;
import ru.maconconsulting.library.models.AbstractProjectFieldEntity;
import ru.maconconsulting.library.models.Project;

import java.util.List;

@Entity
@Table(name = "project_type")
public class ProjectType extends AbstractProjectFieldEntity {

    @OneToMany(mappedBy = "type")
    private List<Project> projects;

    public ProjectType() {
    }

    public ProjectType(String name) {
        super(name);
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
