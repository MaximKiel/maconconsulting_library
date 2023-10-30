package ru.maconconsulting.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "project")
public class Project extends AbstractBasedEntity {

    @Column(name = "number")
    @NotNull
    private Integer number;

    @Column(name = "year")
    @NotNull
    private Integer year;

    @Column(name = "title")
    @NotBlank
    private String title;

    @Column(name = "countries")
    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> countries;

    @Column(name = "regions")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> regions;

    @Column(name = "towns")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> towns;

    @Column(name = "segments")
    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> segments;

    @Column(name = "type")
    @NotNull
    private String type;

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
