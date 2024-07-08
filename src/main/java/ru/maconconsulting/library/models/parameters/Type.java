package ru.maconconsulting.library.models.parameters;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import ru.maconconsulting.library.models.content.Project;

import java.util.Set;

@Entity
@Table(name = "type")
public class Type extends AbstractParameterEntity {

    @ManyToMany(mappedBy = "types")
    private Set<Project> projects;

    public Type() {
    }

    public Type(String name) {
        super(name);
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
