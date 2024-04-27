package ru.maconconsulting.library.dto.content;

import jakarta.validation.constraints.NotBlank;
import ru.maconconsulting.library.dto.parameters.FormatDTO;
import ru.maconconsulting.library.dto.parameters.SegmentDTO;

import java.util.Set;

public class PublicationDTO {

    private Integer id;

    @NotBlank(message = "Название не должно быть пустым!")
    private String title;

    private String annotation;

    private String source;

    private Integer year;

    private String relevance;

    private String path;

    private String location;

    private Set<SegmentDTO> segments;

    private Set<FormatDTO> formats;

    private String keyWords;

    public PublicationDTO() {
    }

    public PublicationDTO(Integer id, String title, String annotation, String source, Integer year, String relevance, String path, String location, Set<SegmentDTO> segments, Set<FormatDTO> formats, String keyWords) {
        this.id = id;
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

    public Set<SegmentDTO> getSegments() {
        return segments;
    }

    public void setSegments(Set<SegmentDTO> segments) {
        this.segments = segments;
    }

    public Set<FormatDTO> getFormats() {
        return formats;
    }

    public void setFormats(Set<FormatDTO> formats) {
        this.formats = formats;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
