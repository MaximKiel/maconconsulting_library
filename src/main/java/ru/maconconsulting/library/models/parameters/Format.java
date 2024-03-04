package ru.maconconsulting.library.models.parameters;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import ru.maconconsulting.library.models.Project;

import java.util.List;

@Entity
@Table(name = "format")
public class Format extends AbstractParameterEntity {

    @ManyToMany(mappedBy = "formats")
    private List<Project> projects;

    public Format() {
    }

    public Format(String name) {
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
