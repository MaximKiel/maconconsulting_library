package ru.maconconsulting.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import ru.maconconsulting.library.models.parameters.Format;
import ru.maconconsulting.library.models.parameters.KeyWord;
import ru.maconconsulting.library.models.parameters.Segment;

import java.util.List;

@Entity
@Table(name = "publication")
public class Publication extends AbstractBasedEntityWithId {

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

    @Column(name = "countries")
    private String countries;

    @Column(name = "regions")
    private String regions;

    @Column(name = "towns")
    private String towns;

    @ManyToMany
    @JoinTable(
            name = "publication_segment",
            joinColumns = @JoinColumn(name = "publication_id"),
            inverseJoinColumns = @JoinColumn(name = "segment_id"))
    private List<Segment> segments;

    @ManyToMany
    @JoinTable(
            name = "publication_format",
            joinColumns = @JoinColumn(name = "publication_id"),
            inverseJoinColumns = @JoinColumn(name = "format_id")
    )
    private List<Format> formats;

    @ManyToMany
    @JoinTable(
            name = "publication_kew_word",
            joinColumns = @JoinColumn(name = "publication_id"),
            inverseJoinColumns = @JoinColumn(name = "kew_word_id")
    )
    private List<KeyWord> keyWords;

    public Publication() {
    }

    public Publication(String title, String annotation, String source, Integer year, String relevance, String countries, String regions, String towns, List<Segment> segments, List<Format> formats, List<KeyWord> keyWords) {
        this.title = title;
        this.annotation = annotation;
        this.source = source;
        this.year = year;
        this.relevance = relevance;
        this.countries = countries;
        this.regions = regions;
        this.towns = towns;
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

    public List<KeyWord> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<KeyWord> keyWords) {
        this.keyWords = keyWords;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "title='" + title + '\'' +
                ", annotation='" + annotation + '\'' +
                ", source='" + source + '\'' +
                ", year=" + year +
                ", relevance='" + relevance + '\'' +
                ", countries='" + countries + '\'' +
                ", regions='" + regions + '\'' +
                ", towns='" + towns + '\'' +
                ", segments=" + segments +
                ", formats=" + formats +
                ", keyWords=" + keyWords +
                '}';
    }
}
