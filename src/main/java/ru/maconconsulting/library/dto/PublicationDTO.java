package ru.maconconsulting.library.dto;

import jakarta.validation.constraints.NotBlank;
import ru.maconconsulting.library.dto.parameters.FormatDTO;
import ru.maconconsulting.library.dto.parameters.KeyWordDTO;
import ru.maconconsulting.library.dto.parameters.SegmentDTO;

import java.util.List;

public class PublicationDTO {

    @NotBlank(message = "Название не должно быть пустым!")
    private String title;

    private String annotation;

    private String source;

    private Integer year;

    private String relevance;

    private String countries;

    private String regions;

    private String towns;

    private List<SegmentDTO> segments;

    private List<FormatDTO> formats;

    private List<KeyWordDTO> keyWords;

    public PublicationDTO() {
    }

    public PublicationDTO(String title, String annotation, String source, Integer year, String relevance, String countries, String regions, String towns, List<SegmentDTO> segments, List<FormatDTO> formats, List<KeyWordDTO> keyWords) {
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

    public List<KeyWordDTO> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<KeyWordDTO> keyWords) {
        this.keyWords = keyWords;
    }
}
