package ru.maconconsulting.library.utils;

import ru.maconconsulting.library.dto.parameters.FormatDTO;
import ru.maconconsulting.library.dto.parameters.KeyWordDTO;
import ru.maconconsulting.library.dto.parameters.SegmentDTO;

public class SearchPublication {

    private String title;

    private String annotation;

    private String source;

    private Integer year;

    private String relevance;

    private String country;

    private String region;

    private String town;

    private SegmentDTO segment;

    private FormatDTO format;

    private KeyWordDTO keyWord;

    public SearchPublication() {
    }

    public SearchPublication(String title, String annotation, String source, Integer year, String relevance, String country, String region, String town, SegmentDTO segment, FormatDTO format, KeyWordDTO keyWord) {
        this.title = title;
        this.annotation = annotation;
        this.source = source;
        this.year = year;
        this.relevance = relevance;
        this.country = country;
        this.region = region;
        this.town = town;
        this.segment = segment;
        this.format = format;
        this.keyWord = keyWord;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public SegmentDTO getSegment() {
        return segment;
    }

    public void setSegment(SegmentDTO segment) {
        this.segment = segment;
    }

    public FormatDTO getFormat() {
        return format;
    }

    public void setFormat(FormatDTO format) {
        this.format = format;
    }

    public KeyWordDTO getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(KeyWordDTO keyWord) {
        this.keyWord = keyWord;
    }
}
