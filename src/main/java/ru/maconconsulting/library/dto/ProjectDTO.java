package ru.maconconsulting.library.dto;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProjectDTO {

    @NotNull
    private Integer number;

    @NotNull
    private Integer year;

    @NotBlank
    private String title;

    @NotBlank
    private String client;

    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    private String countries;

    @ElementCollection(fetch = FetchType.EAGER)
    private String regions;

    @ElementCollection(fetch = FetchType.EAGER)
    private String towns;

    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    private String segments;

    @NotNull
    private String type;

    public ProjectDTO() {
    }

    public ProjectDTO(Integer number, Integer year, String title, String client, String countries, String regions, String towns, String segments, String type) {
        this.number = number;
        this.year = year;
        this.title = title;
        this.client = client;
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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
