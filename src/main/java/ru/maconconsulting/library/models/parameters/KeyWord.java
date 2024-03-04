package ru.maconconsulting.library.models.parameters;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import ru.maconconsulting.library.models.Project;
import ru.maconconsulting.library.models.Publication;

import java.util.List;

@Entity
@Table(name = "kew_word")
public class KeyWord extends AbstractParameterEntity {

    @ManyToMany(mappedBy = "keyWords")
    private List<Project> projects;

    @ManyToMany(mappedBy = "keyWords")
    private List<Publication> publications;

    public KeyWord() {
    }

    public KeyWord(String name) {
        super(name);
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
