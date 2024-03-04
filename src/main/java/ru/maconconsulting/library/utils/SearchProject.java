package ru.maconconsulting.library.utils;

import ru.maconconsulting.library.dto.parameters.ProjectFormatDTO;
import ru.maconconsulting.library.dto.parameters.ProjectKeyWordDTO;
import ru.maconconsulting.library.dto.parameters.ProjectSegmentDTO;
import ru.maconconsulting.library.dto.parameters.ProjectTypeDTO;

public class SearchProject {

    private int year;

    private String relevance;

    private String title;

    private String client;

    private String country;

    private String region;

    private String town;

    private ProjectSegmentDTO segment;

    private ProjectTypeDTO type;

    private ProjectFormatDTO format;

    private ProjectKeyWordDTO keyWord;

    public SearchProject() {
    }

    public SearchProject(int year, String relevance, String title, String client, String country, String region, String town, ProjectSegmentDTO segment, ProjectTypeDTO type, ProjectFormatDTO format, ProjectKeyWordDTO keyWord) {
        this.year = year;
        this.relevance = relevance;
        this.title = title;
        this.client = client;
        this.country = country;
        this.region = region;
        this.town = town;
        this.segment = segment;
        this.type = type;
        this.format = format;
        this.keyWord = keyWord;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
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

    public ProjectSegmentDTO getSegment() {
        return segment;
    }

    public void setSegment(ProjectSegmentDTO segment) {
        this.segment = segment;
    }

    public ProjectTypeDTO getType() {
        return type;
    }

    public void setType(ProjectTypeDTO type) {
        this.type = type;
    }

    public ProjectFormatDTO getFormat() {
        return format;
    }

    public void setFormat(ProjectFormatDTO format) {
        this.format = format;
    }

    public ProjectKeyWordDTO getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(ProjectKeyWordDTO keyWord) {
        this.keyWord = keyWord;
    }
}
