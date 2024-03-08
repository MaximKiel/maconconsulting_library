package ru.maconconsulting.library.models.parameters;

import jakarta.persistence.*;
import ru.maconconsulting.library.models.content.Project;

import java.util.List;

@Entity
@Table(name = "type")
public class Type extends AbstractParameterEntity {

    @OneToMany(mappedBy = "type")
    private List<Project> projects;

    public Type() {
    }

    public Type(String name) {
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
