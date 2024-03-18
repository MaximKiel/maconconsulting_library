package ru.maconconsulting.library.models.content;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ru.maconconsulting.library.models.AbstractBasedEntity;
import ru.maconconsulting.library.models.parameters.Format;
import ru.maconconsulting.library.models.parameters.KeyWord;
import ru.maconconsulting.library.models.parameters.Segment;
import ru.maconconsulting.library.models.parameters.Type;

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
            name = "project_segment",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "segment_id"))
    @NotEmpty(message = "Список сегментов не должен быть пустым!")
    private List<Segment> segments;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    @NotNull(message = "Тип проекта не должен быть пустым!")
    private Type type;

    @ManyToMany
    @JoinTable(
            name = "project_format",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "format_id")
    )
    @NotEmpty(message = "Список форматов не должен быть пустым!")
    private List<Format> formats;

    @ManyToMany
    @JoinTable(
            name = "project_key_word",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "key_word_id")
    )
    private List<KeyWord> keyWords;

    public Project() {
    }

    public Project(String number, Integer year, String relevance, String title, String client, String location, List<Segment> segments, Type type, List<Format> formats, List<KeyWord> keyWords) {
        this.number = number;
        this.year = year;
        this.relevance = relevance;
        this.title = title;
        this.client = client;
        this.location = location;
        this.segments = segments;
        this.type = type;
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

    public List<Segment> getSegments() {
        if (segments != null) {
            return segments.stream().sorted(Comparator.comparing(Segment::getName)).collect(Collectors.toList());
        }
        return null;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public Type getType() {
        return type;
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

    public void setType(Type type) {
        this.type = type;
    }

    public List<KeyWord> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<KeyWord> keyWords) {
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
                ", segments=" + segments +
                ", type=" + type +
                ", formats=" + formats +
                ", keyWords=" + keyWords +
                '}';
    }
}
