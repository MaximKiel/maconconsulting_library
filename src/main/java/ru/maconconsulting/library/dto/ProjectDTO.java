package ru.maconconsulting.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProjectDTO {

    @NotNull
    private String number;

    @NotNull
    private Integer year;

    @NotBlank
    private String title;

    @NotBlank
    private String client;

    @NotNull
    private String countries;

    private String regions;

    private String towns;

    @NotNull
    private String segments;

    @NotNull
    private String type;

    @NotBlank
    private String formats;

    private String tags;

    public ProjectDTO() {
    }

    public ProjectDTO(String number, Integer year, String title, String client, String countries, String regions, String towns, String segments, String type, String formats, String tags) {
        this.number = number;
        this.year = year;
        this.title = title;
        this.client = client;
        this.countries = countries;
        this.regions = regions;
        this.towns = towns;
        this.segments = segments;
        this.type = type;
        this.formats=formats;
        this.tags = tags;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getFormats() {
        return formats;
    }

    public void setFormats(String formats) {
        this.formats = formats;
    }
}
