package ru.maconconsulting.library.models.parameters;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import ru.maconconsulting.library.models.Project;

import java.util.List;

@Entity
@Table(name = "segment")
public class Segment extends AbstractParameterEntity {

    @ManyToMany(mappedBy = "segments")
    private List<Project> projects;

    public Segment() {
    }

    public Segment(String name) {
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
