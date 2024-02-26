package ru.maconconsulting.library.models.projectfields;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import ru.maconconsulting.library.models.Project;

import java.util.List;

@Entity
@Table(name = "kew_word")
public class ProjectKeyWord extends AbstractProjectFieldEntity {

    @ManyToMany(mappedBy = "kew_words")
    private List<Project> projects;

    public ProjectKeyWord() {
    }

    public ProjectKeyWord(String name) {
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
