package ru.maconconsulting.library.models.content;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.maconconsulting.library.models.AbstractBasedEntity;
import ru.maconconsulting.library.models.parameters.*;

import java.util.List;

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
            name = "project_chapter",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "chapter_id"))
    private List<Chapter> chapters;

    @ManyToMany
    @JoinTable(
            name = "project_segment",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "segment_id"))
    private List<Segment> segments;

    @ManyToMany
    @JoinTable(
            name = "project_format",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "format_id")
    )
    private List<Format> formats;

    @Column(name = "key_words")
    private String keyWords;

    @Column(name = "methodology")
    private String methodology;

    public Project() {
    }

    public Project(Integer year, String relevance, String title, String client, String location, List<Chapter> chapters, List<Segment> segments, List<Format> formats, String keyWords, String methodology) {
        this.year = year;
        this.relevance = relevance;
        this.title = title;
        this.client = client;
        this.location = location;
        this.chapters = chapters;
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

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public List<Format> getFormats() {
        return formats;
    }

    public void setFormats(List<Format> formats) {
        this.formats = formats;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getMethodology() {
        return methodology;
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
                ", chapters=" + chapters +
                ", segments=" + segments +
                ", formats=" + formats +
                ", keyWords='" + keyWords + '\'' +
                ", methodology='" + methodology + '\'' +
                '}';
    }
}
