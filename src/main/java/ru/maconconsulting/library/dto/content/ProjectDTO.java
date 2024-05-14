package ru.maconconsulting.library.dto.content;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.maconconsulting.library.dto.parameters.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ProjectDTO {

    private Integer id;

    @NotNull(message = "Год проекта не должен быть пустым!")
    private Integer year;

    private String relevance;

    @NotBlank(message = "Название проекта не должно быть пустым!")
    private String title;

    private String client;

    @NotBlank(message = "Локация не должна быть пустой!")
    private String location;

    private Set<TypeDTO> types;

    private Set<SegmentDTO> segments;

    private Set<FormatDTO> formats;

    private String keyWords;

    private String methodology;

    public ProjectDTO() {
    }

    public ProjectDTO(Integer id, Integer year, String relevance, String title, String client, String location, Set<TypeDTO> types, Set<SegmentDTO> segments, Set<FormatDTO> formats, String keyWords, String methodology) {
        this.id = id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Set<TypeDTO> getTypes() {
        return types;
    }

    public void setTypes(Set<TypeDTO> types) {
        this.types = types;
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

    public List<String> getKeyWordsToList() {
        return keyWords != null ? Arrays.stream(keyWords.split("\n")).toList() : new ArrayList<>();
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getMethodology() {
        return methodology;
    }

    public List<String> getMethodologyToList() {
        return methodology != null ? Arrays.stream(methodology.split("\n")).toList() : new ArrayList<>();
    }

    public void setMethodology(String methodology) {
        this.methodology = methodology;
    }
}
