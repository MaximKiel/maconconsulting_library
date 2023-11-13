package ru.maconconsulting.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "project")
public class Project extends AbstractBasedEntity {

    @Id
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
    private String countries;

    @Column(name = "regions")
    private String regions;

    @Column(name = "towns")
    private String towns;

    @Column(name = "segments")
    @NotNull
    private String segments;

    @Column(name = "type")
    @NotNull
    private String type;

    public Project() {
    }

    public Project(Integer number, String title, String countries, String regions, String towns, String segments, String type, Integer year) {
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

    public String getSegments() {
        return segments;
    }

    public void setSegments(String segments) {
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
