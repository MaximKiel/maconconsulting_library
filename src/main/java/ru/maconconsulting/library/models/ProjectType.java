package ru.maconconsulting.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "project_type")
public class ProjectType extends AbstractBasedEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotBlank
    private String name;

//    TODO: fix org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - save the transient instance before flushing
//    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "type")
    private List<Project> projects;

    public ProjectType() {
    }

    public ProjectType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "ProjectType{" +
                "name='" + name +
                '}';
    }
}
