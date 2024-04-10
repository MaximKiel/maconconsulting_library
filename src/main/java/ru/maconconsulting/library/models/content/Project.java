package ru.maconconsulting.library.models.content;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ru.maconconsulting.library.models.AbstractBasedEntity;
import ru.maconconsulting.library.models.parameters.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "project")
public class Project extends AbstractBasedEntity {

    @Id
    @Column(name = "number")
    @NotBlank(message = "Номер проекта не должен быть пустым!")
    private String number;

    @Column(name = "year")
    @NotNull(message = "Год проекта не должен быть пустым!")
    private Integer year;

    @Column(name = "relevance")
    private String relevance;

    @Column(name = "title")
    @NotBlank(message = "Название проекта не должно быть пустым!")
    private String title;

    @Column(name = "client")
    @NotBlank(message = "Наименование клиента  не должно быть пустым!")
    private String client;

    @Column(name = "location")
    @NotBlank(message = "Локация не должна быть пустой!")
    private String location;

    @ManyToMany
    @JoinTable(
            name = "project_chapter",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "chapter_id"))
    @NotEmpty(message = "Список разделов не должен быть пустым!")
    private List<Chapter> chapters;

    @ManyToMany
    @JoinTable(
            name = "project_segment",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "segment_id"))
    @NotEmpty(message = "Список сегментов не должен быть пустым!")
    private List<Segment> segments;

    @ManyToMany
    @JoinTable(
            name = "project_format",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "format_id")
    )
    @NotEmpty(message = "Список форматов не должен быть пустым!")
    private List<Format> formats;

    @Column(name = "key_words")
    private String keyWords;

    public Project() {
    }

    public Project(String number, Integer year, String relevance, String title, String client, String location, List<Chapter> chapters, List<Segment> segments, List<Format> formats, String keyWords) {
        this.number = number;
        this.year = year;
        this.relevance = relevance;
        this.title = title;
        this.client = client;
        this.location = location;
        this.chapters = chapters;
        this.segments = segments;
        this.formats = formats;
        this.keyWords = keyWords;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public List<Segment> getSegments() {
        if (segments != null) {
            return segments.stream().sorted(Comparator.comparing(Segment::getName)).collect(Collectors.toList());
        }
        return null;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public List<Format> getFormats() {
        if (formats != null) {
            return formats.stream().sorted(Comparator.comparing(Format::getName)).collect(Collectors.toList());
        }
        return null;
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

    @Override
    public String toString() {
        return "Project{" +
                "number='" + number + '\'' +
                ", year=" + year +
                ", relevance='" + relevance + '\'' +
                ", title='" + title + '\'' +
                ", client='" + client + '\'' +
                ", location='" + location + '\'' +
                ", chapters=" + chapters +
                ", segments=" + segments +
                ", formats=" + formats +
                ", keyWords='" + keyWords + '\'' +
                '}';
    }
}
