package ru.maconconsulting.library.models.content;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import ru.maconconsulting.library.models.AbstractBasedEntity;
import ru.maconconsulting.library.models.parameters.Format;
import ru.maconconsulting.library.models.parameters.Segment;
import ru.maconconsulting.library.models.parameters.TypeOfPublication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "publication")
public class Publication extends AbstractBasedEntity {

    @Column(name = "title")
    @NotBlank(message = "Название не должно быть пустым!")
    private String title;

    @Column(name = "related_project_title")
    private String relatedProjectTitle;

    @Column(name = "annotation")
    private String annotation;

    @Column(name = "source")
    private String source;

    @Column(name = "year")
    private Integer year;

    @Column(name = "relevance")
    private String relevance;

    @Column(name = "path")
    private String path;

    @Column(name = "location")
    private String location;

    @ManyToMany
    @JoinTable(
            name = "publication_type",
            joinColumns = @JoinColumn(name = "publication_id"),
            inverseJoinColumns = @JoinColumn(name = "type_of_publication_id"))
    private Set<TypeOfPublication> typesOfPublication;

    @ManyToMany
    @JoinTable(
            name = "publication_segment",
            joinColumns = @JoinColumn(name = "publication_id"),
            inverseJoinColumns = @JoinColumn(name = "segment_id"))
    private Set<Segment> segments;

    @ManyToMany
    @JoinTable(
            name = "publication_format",
            joinColumns = @JoinColumn(name = "publication_id"),
            inverseJoinColumns = @JoinColumn(name = "format_id")
    )
    private Set<Format> formats;

    public Publication() {
    }

    public Publication(String title, String relatedProjectTitle, String annotation, String source, Integer year, String relevance, String path, String location, Set<TypeOfPublication> typesOfPublication, Set<Segment> segments, Set<Format> formats) {
        this.title = title;
        this.relatedProjectTitle = relatedProjectTitle;
        this.annotation = annotation;
        this.source = source;
        this.year = year;
        this.relevance = relevance;
        this.path = path;
        this.location = location;
        this.typesOfPublication = typesOfPublication;
        this.segments = segments;
        this.formats = formats;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelatedProjectTitle() {
        return relatedProjectTitle;
    }

    public void setRelatedProjectTitle(String relatedProjectTitle) {
        this.relatedProjectTitle = relatedProjectTitle;
    }

    public String getAnnotation() {
        return annotation;
    }

    public List<String> getAnnotationToList() {
        return annotation != null ? Arrays.stream(annotation.split("\n")).toList() : new ArrayList<>();
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getRelevance() {
        return relevance;
    }

    public void setRelevance(String relevance) {
        this.relevance = relevance;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<TypeOfPublication> getTypesOfPublication() {
        return typesOfPublication;
    }

    public void setTypesOfPublication(Set<TypeOfPublication> typesOfPublication) {
        this.typesOfPublication = typesOfPublication;
    }

    public Set<Segment> getSegments() {
        return segments;
    }

    public void setSegments(Set<Segment> segments) {
        this.segments = segments;
    }

    public Set<Format> getFormats() {
        return formats;
    }

    public void setFormats(Set<Format> formats) {
        this.formats = formats;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "title='" + title + '\'' +
                ", relatedProjectTitle='" + relatedProjectTitle + '\'' +
                ", annotation='" + annotation + '\'' +
                ", source='" + source + '\'' +
                ", year=" + year +
                ", relevance='" + relevance + '\'' +
                ", path='" + path + '\'' +
                ", location='" + location + '\'' +
                ", typesOfPublication=" + typesOfPublication +
                ", segments=" + segments +
                ", formats=" + formats +
                '}';
    }
}
