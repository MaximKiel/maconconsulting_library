package ru.maconconsulting.library.utils;

import ru.maconconsulting.library.dto.ProjectTypeDTO;

public class SearchProject {

    private int year;

    private String relevance;

    private String title;

    private String client;

    private String country;

    private String region;

    private String town;

    private String segment;

    private ProjectTypeDTO type;

    private String format;
    private String tag;

    public SearchProject() {
    }

    public SearchProject(int year, String relevance, String title, String client, String country, String region, String town, String segment, ProjectTypeDTO type, String format, String tag) {
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
        this.tag = tag;
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

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public ProjectTypeDTO getType() {
        return type;
    }

    public void setType(ProjectTypeDTO type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
