package ru.maconconsulting.library.models.content;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.maconconsulting.library.models.AbstractBasedEntity;
import ru.maconconsulting.library.models.parameters.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "project")
public class Project extends AbstractBasedEntity {

    @Column(name = "year")
    @NotNull(message = "Год проекта не должен быть пустым!")
    private Integer year;

    @Column(name = "relevance")
    private String relevance;

    @Column(name = "title")
    @NotBlank(message = "Название проекта не должно быть пустым!")
    private String title;

    @Column(name = "client")
    private String client;

    @Column(name = "location")
    @NotBlank(message = "Локация не должна быть пустой!")
    private String location;

    @ManyToMany
    @JoinTable(
            name = "project_type",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id"))
    private Set<Type> types;

    @ManyToMany
    @JoinTable(
            name = "project_segment",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "segment_id"))
    private Set<Segment> segments;

    @ManyToMany
    @JoinTable(
            name = "project_format",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "format_id")
    )
    private Set<Format> formats;

    @Column(name = "key_words")
    private String keyWords;

    @Column(name = "methodology")
    private String methodology;

    public Project() {
    }

    public Project(Integer year, String relevance, String title, String client, String location, Set<Type> types, Set<Segment> segments, Set<Format> formats, String keyWords, String methodology) {
        this.year = year;
        this.relevance = relevance;
        this.title = title;
        this.client = client;
        this.location = location;
        this.types = types;
        this.segments = segments;
        this.formats = formats;
        this.keyWords = keyWords;
        this.methodology = methodology;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Type> getTypes() {
        return types;
    }

    public void setTypes(Set<Type> types) {
        this.types = types;
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

    public List<String> getKeyWordsInList() {
        return Arrays.stream(keyWords.split("\n")).toList();
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getMethodology() {
        return methodology;
    }

    public List<String> getMethodologyInList() {
        return Arrays.stream(methodology.split("\n")).toList();
    }

    public void setMethodology(String methodology) {
        this.methodology = methodology;
    }

    @Override
    public String toString() {
        return "Project{" +
                "year=" + year +
                ", relevance='" + relevance + '\'' +
                ", title='" + title + '\'' +
                ", client='" + client + '\'' +
                ", location='" + location + '\'' +
                ", types=" + types +
                ", segments=" + segments +
                ", formats=" + formats +
                ", keyWords='" + keyWords + '\'' +
                ", methodology='" + methodology + '\'' +
                '}';
    }
}
