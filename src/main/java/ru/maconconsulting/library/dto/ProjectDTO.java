package ru.maconconsulting.library.dto;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ProjectDTO {

    @NotNull
    private Integer number;

    @NotNull
    private Integer year;

    @NotBlank
    private String title;

    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> countries;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> regions;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> towns;

    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> segments;

    @NotNull
    private String type;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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
}
