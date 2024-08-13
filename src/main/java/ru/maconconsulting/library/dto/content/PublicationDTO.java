package ru.maconconsulting.library.dto.content;

import jakarta.validation.constraints.NotBlank;
import ru.maconconsulting.library.dto.parameters.FormatDTO;
import ru.maconconsulting.library.dto.parameters.SegmentDTO;
import ru.maconconsulting.library.dto.parameters.TypeOfPublicationDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class PublicationDTO {

    private Integer id;

    @NotBlank(message = "Название не должно быть пустым!")
    private String title;

    private String relatedProjectTitle;

    private String annotation;

    private String source;

    private Integer year;

    private String relevance;

    @NotBlank(message = "Путь не должен быть пустым!")
    private String path;

    private String location;

    private Set<TypeOfPublicationDTO> typesOfPublication;

    private Set<SegmentDTO> segments;

    private Set<FormatDTO> formats;

    public PublicationDTO() {
    }

    public PublicationDTO(Integer id, String title, String relatedProjectTitle, String annotation, String source, Integer year, String relevance, String path, String location, Set<TypeOfPublicationDTO> typesOfPublication, Set<SegmentDTO> segments, Set<FormatDTO> formats) {
        this.id = id;
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

    public Set<TypeOfPublicationDTO> getTypesOfPublication() {
        return typesOfPublication;
    }

    public void setTypesOfPublication(Set<TypeOfPublicationDTO> typesOfPublication) {
        this.typesOfPublication = typesOfPublication;
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
