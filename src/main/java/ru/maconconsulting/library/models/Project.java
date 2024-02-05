package ru.maconconsulting.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.maconconsulting.library.models.projectfieldetities.ProjectFormat;
import ru.maconconsulting.library.models.projectfieldetities.ProjectSegment;
import ru.maconconsulting.library.models.projectfieldetities.ProjectType;

import java.util.List;

@Entity
@Table(name = "project")
public class Project extends AbstractBasedEntity {

    @Id
    @Column(name = "number")
    @NotNull
    private String number;

    @Column(name = "year")
    @NotNull
    private Integer year;

    @Column(name = "relevance")
    private String relevance;

    @Column(name = "title")
    @NotBlank
    private String title;

    @Column(name = "client")
    @NotBlank
    private String client;

    @Column(name = "countries")
    @NotBlank
    private String countries;

    @Column(name = "regions")
    private String regions;

    @Column(name = "towns")
    private String towns;

    @ManyToMany
    @JoinTable(
            name = "project_segment",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "segment_id"))
    private List<ProjectSegment> segments;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private ProjectType type;

    @ManyToMany
    @JoinTable(
            name = "project_format",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "format_id")
    )
    private List<ProjectFormat> formats;

    @Column(name = "tags")
    private String tags;

    public Project() {
    }

    public Project(String number, Integer year, String relevance, String title, String client, String countries, String regions, String towns, List<ProjectSegment> segments, ProjectType type, List<ProjectFormat> formats, String tags) {
        this.number = number;
        this.year = year;
        this.relevance = relevance;
        this.title = title;
        this.client = client;
        this.countries = countries;
        this.regions = regions;
        this.towns = towns;
        this.segments = segments;
        this.type = type;
        this.formats = formats;
        this.tags = tags;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelevance() {
        return relevance;
    }

    public void setRelevance(String relevance) {
        this.relevance = relevance;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getRegions() {
        return regions;
    }

    public void setRegions(String regions) {
        this.regions = regions;
    }

    public String getTowns() {
        return towns;
    }

    public void setTowns(String towns) {
        this.towns = towns;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<ProjectSegment> getSegments() {
        return segments;
    }

    public void setSegments(List<ProjectSegment> segments) {
        this.segments = segments;
    }

    public ProjectType getType() {
        return type;
    }

    public List<ProjectFormat> getFormats() {
        return formats;
    }

    public void setFormats(List<ProjectFormat> formats) {
        this.formats = formats;
    }

    public void setType(ProjectType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Project{" +
                "number='" + number + '\'' +
                ", year=" + year +
                ", relevance='" + relevance + '\'' +
                ", title='" + title + '\'' +
                ", client='" + client + '\'' +
                ", countries='" + countries + '\'' +
                ", regions='" + regions + '\'' +
                ", towns='" + towns + '\'' +
                ", segments='" + segments + '\'' +
                ", type=" + type +
                ", formats='" + formats + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }
}
