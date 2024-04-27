package ru.maconconsulting.library.models.parameters;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import ru.maconconsulting.library.models.content.Project;
import ru.maconconsulting.library.models.content.Publication;

import java.util.Set;

@Entity
@Table(name = "segment")
public class Segment extends AbstractParameterEntity {

    @ManyToMany(mappedBy = "segments")
    private Set<Project> projects;

    @ManyToMany(mappedBy = "segments")
    private Set<Publication> publications;

    public Segment() {
    }

    public Segment(String name) {
        super(name);
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<Publication> getPublications() {
        return publications;
    }

    public void setPublications(Set<Publication> publications) {
        this.publications = publications;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
