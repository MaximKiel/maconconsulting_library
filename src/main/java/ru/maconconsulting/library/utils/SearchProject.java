package ru.maconconsulting.library.utils;

public class SearchProject {

    private int year;

    private String title;

    private String country;

    private String region;

    private String town;

    private String segment;

    private String type;

    private String format;
    private String tag;

    public SearchProject() {
    }

    public SearchProject(int year, String title, String country, String region, String town, String segment, String type, String format, String tag) {
        this.year = year;
        this.title = title;
        this.country = country;
        this.region = region;
        this.town = town;
        this.segment = segment;
        this.type = type;
        this.format=format;
        this.tag = tag;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
