package ru.maconconsulting.library.models.parameters;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import ru.maconconsulting.library.models.content.Project;
import ru.maconconsulting.library.models.content.Publication;

import java.util.Set;

@Entity
@Table(name = "format")
public class Format extends AbstractParameterEntity {

    @ManyToMany(mappedBy = "formats")
    private Set<Project> projects;

    @ManyToMany(mappedBy = "formats")
    private Set<Publication> publications;

    public Format() {
    }

    public Format(String name) {
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
