package ru.maconconsulting.library.models.parameters;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import ru.maconconsulting.library.models.content.Project;

import java.util.List;

@Entity
@Table(name = "chapter")
public class Chapter extends AbstractParameterEntity {

    @ManyToMany(mappedBy = "chapters")
    private List<Project> projects;

    public Chapter() {
    }

    public Chapter(String name) {
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
        return "Chapter{" +
                "projects=" + projects +
                '}';
    }
}
