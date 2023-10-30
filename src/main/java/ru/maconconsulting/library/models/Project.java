package ru.maconconsulting.library.models;

import java.util.List;

public class Project extends AbstractBasedEntity {

    private Integer number;

    private String title;

    private List<String> countries;

    private List<String> regions;

    private List<String> towns;

    private List<String> segments;

    private String type;

    private Integer year;

    public Project() {
    }

    public Project(Integer number, String title, List<String> countries, List<String> regions, List<String> towns, List<String> segments, String type, Integer year) {
        this.number = number;
        this.title = title;
        this.countries = countries;
        this.regions = regions;
        this.towns = towns;
        this.segments = segments;
        this.type = type;
        this.year = year;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public List<String> getTowns() {
        return towns;
    }

    public void setTowns(List<String> towns) {
        this.towns = towns;
    }

    public List<String> getSegments() {
        return segments;
    }

    public void setSegments(List<String> segments) {
        this.segments = segments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Project{" +
                "number=" + number +
                ", title='" + title + '\'' +
                ", countries=" + countries +
                ", regions=" + regions +
                ", towns=" + towns +
                ", segments=" + segments +
                ", type='" + type + '\'' +
                ", year=" + year +
                '}';
    }
}
