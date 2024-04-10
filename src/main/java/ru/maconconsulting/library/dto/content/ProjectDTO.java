package ru.maconconsulting.library.dto.content;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ru.maconconsulting.library.dto.parameters.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectDTO {

    @NotBlank(message = "Номер проекта не должен быть пустым!")
    private String number;

    @NotNull(message = "Год проекта не должен быть пустым!")
    private Integer year;

    private String relevance;

    @NotBlank(message = "Название проекта не должно быть пустым!")
    private String title;

    @NotBlank(message = "Наименование клиента  не должно быть пустым!")
    private String client;

    @NotBlank(message = "Локация не должна быть пустой!")
    private String location;

    @NotEmpty(message = "Список разделов не должен быть пустым!")
    private List<ChapterDTO> chapters;

    @NotEmpty(message = "Список сегментов не должен быть пустым!")
    private List<SegmentDTO> segments;

    @NotEmpty(message = "Список форматов не должен быть пустым!")
    private List<FormatDTO> formats;

    private String keyWords;

    public ProjectDTO() {
    }

    public ProjectDTO(String number, Integer year, String relevance, String title, String client, String location, List<ChapterDTO> chapters, List<SegmentDTO> segments, List<FormatDTO> formats, String keyWords) {
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
        if (segments != null) {
            return segments.stream().sorted(Comparator.comparing(SegmentDTO::getName)).collect(Collectors.toList());
        }
        return null;
    }

    public void setSegments(List<SegmentDTO> segments) {
        this.segments = segments;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public List<FormatDTO> getFormats() {
        if (formats != null) {
            return formats.stream().sorted(Comparator.comparing(FormatDTO::getName)).collect(Collectors.toList());
        }
        return null;
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
}
