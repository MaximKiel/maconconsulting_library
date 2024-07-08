package ru.maconconsulting.library.utils.search;

import ru.maconconsulting.library.dto.parameters.*;

public class SearchPublication {

    private String title;

    private String relatedProjectTitle;

    private String annotation;

    private String source;

    private Integer year;

    private String relevance;

    private String location;

    private SegmentDTO segment;

    private FormatDTO format;

    private String keyWord;

    public SearchPublication() {
    }

    public SearchPublication(String title, String relatedProjectTitle, String annotation, String source, Integer year, String relevance, String location, SegmentDTO segment, FormatDTO format, String keyWord) {
        this.title = title;
        this.relatedProjectTitle = relatedProjectTitle;
        this.annotation = annotation;
        this.source = source;
        this.year = year;
        this.relevance = relevance;
        this.location = location;
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

    public String getRelatedProjectTitle() {
        return relatedProjectTitle;
    }

    public void setRelatedProjectTitle(String relatedProjectTitle) {
        this.relatedProjectTitle = relatedProjectTitle;
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

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
