package ru.maconconsulting.library.models.content;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import ru.maconconsulting.library.models.AbstractBasedEntity;
import ru.maconconsulting.library.models.parameters.Format;
import ru.maconconsulting.library.models.parameters.Segment;

import java.util.Set;

@Entity
@Table(name = "publication")
public class Publication extends AbstractBasedEntity {

    @Column(name = "title")
    @NotBlank(message = "Название не должно быть пустым!")
    private String title;

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

    @Column(name = "key_words")
    private String keyWords;

    public Publication() {
    }

    public Publication(String title, String annotation, String source, Integer year, String relevance, String path, String location, Set<Segment> segments, Set<Format> formats, String keyWords) {
        this.title = title;
        this.annotation = annotation;
        this.source = source;
        this.year = year;
        this.relevance = relevance;
        this.path = path;
        this.location = location;
        this.segments = segments;
        this.formats = formats;
        this.keyWords = keyWords;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnnotation() {
        return annotation;
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

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
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
                ", annotation='" + annotation + '\'' +
                ", source='" + source + '\'' +
                ", year=" + year +
                ", relevance='" + relevance + '\'' +
                ", location='" + location + '\'' +
                ", segments=" + segments +
                ", formats=" + formats +
                ", keyWords=" + keyWords +
                '}';
    }
}
