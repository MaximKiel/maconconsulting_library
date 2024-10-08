package ru.maconconsulting.library.utils.search;

import ru.maconconsulting.library.dto.parameters.*;

public class SearchPublication {

    private String generalSearch;

    private String title;

    private String relatedProjectTitle;

    private String annotation;

    private String source;

    private Integer year;

    private String relevance;

    private String location;

    private TypeOfPublicationDTO typeOfPublication;

    private SegmentDTO segment;

    private FormatDTO format;

    public SearchPublication() {
    }

    public SearchPublication(String generalSearch, String title, String relatedProjectTitle, String annotation, String source, Integer year, String relevance, String location, TypeOfPublicationDTO typeOfPublication, SegmentDTO segment, FormatDTO format) {
        this.generalSearch = generalSearch;
        this.title = title;
        this.relatedProjectTitle = relatedProjectTitle;
        this.annotation = annotation;
        this.source = source;
        this.year = year;
        this.relevance = relevance;
        this.location = location;
        this.typeOfPublication = typeOfPublication;
        this.segment = segment;
        this.format = format;
    }

    public String getGeneralSearch() {
        return generalSearch;
    }

    public void setGeneralSearch(String generalSearch) {
        this.generalSearch = generalSearch;
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

    public TypeOfPublicationDTO getTypeOfPublication() {
        return typeOfPublication;
    }

    public void setTypeOfPublication(TypeOfPublicationDTO typeOfPublication) {
        this.typeOfPublication = typeOfPublication;
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
}