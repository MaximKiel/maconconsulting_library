package ru.maconconsulting.library.dto;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Arrays;
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

    public static final String STRING_SPLIT = "; ";

    public ProjectDTO() {
    }

    public ProjectDTO(Integer number, Integer year, String title, List<String> countries, List<String> regions, List<String> towns, List<String> segments, String type) {
        this.number = number;
        this.year = year;
        this.title = title;
        this.countries = countries;
        this.regions = regions;
        this.towns = towns;
        this.segments = segments;
        this.type = type;
    }

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

    public void setCountries(String countries) {
        this.countries = Arrays.stream(countries.split(STRING_SPLIT)).toList();
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(String regions) {
        this.regions = Arrays.stream(regions.split(STRING_SPLIT)).toList();
    }

    public List<String> getTowns() {
        return towns;
    }

    public void setTowns(String towns) {
        this.towns = Arrays.stream(towns.split(STRING_SPLIT)).toList();
    }

    public List<String> getSegments() {
        return segments;
    }

    public void setSegments(String segments) {
        this.segments = Arrays.stream(segments.split(STRING_SPLIT)).toList();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
