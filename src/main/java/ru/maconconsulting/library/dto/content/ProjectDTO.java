package ru.maconconsulting.library.dto.content;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.maconconsulting.library.dto.parameters.*;

import java.util.List;

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

    private List<ChapterDTO> chapters;

    private List<SegmentDTO> segments;

    private List<FormatDTO> formats;

    private String keyWords;

    private String methodology;

    public ProjectDTO() {
    }

    public ProjectDTO(Integer id, Integer year, String relevance, String title, String client, String location, List<ChapterDTO> chapters, List<SegmentDTO> segments, List<FormatDTO> formats, String keyWords, String methodology) {
        this.id = id;
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

    public List<ChapterDTO> getChapters() {
        return chapters;
    }

    public void setChapters(List<ChapterDTO> chapters) {
        this.chapters = chapters;
    }

    public List<SegmentDTO> getSegments() {
        return segments;
    }

    public void setSegments(List<SegmentDTO> segments) {
        this.segments = segments;
    }

    public List<FormatDTO> getFormats() {
        return formats;
    }

    public void setFormats(List<FormatDTO> formats) {
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
}
