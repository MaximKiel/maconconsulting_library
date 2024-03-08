package ru.maconconsulting.library.dto.content;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ru.maconconsulting.library.dto.parameters.FormatDTO;
import ru.maconconsulting.library.dto.parameters.KeyWordDTO;
import ru.maconconsulting.library.dto.parameters.SegmentDTO;
import ru.maconconsulting.library.dto.parameters.TypeDTO;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectDTO {

    @NotBlank(message = "Номер проекта не должен быть пустым!")
    private String number;

    @NotNull(message = "Год проекта не должен быть пустым!")
    private Integer year;

    private String relevance;

    @NotBlank(message = "Название проекта не должно быть пустым!")
    private String title;

    @NotBlank(message = "Наименование клиента  не должно быть пустым!")
    private String client;

    @NotBlank(message = "Название страны не должно быть пустым!")
    private String countries;

    private String regions;

    private String towns;

    @NotEmpty(message = "Список сегментов не должен быть пустым!")
    private List<SegmentDTO> segments;

    @NotNull(message = "Тип проекта не должен быть пустым!")
    private TypeDTO type;

    @NotEmpty(message = "Список форматов не должен быть пустым!")
    private List<FormatDTO> formats;

    private List<KeyWordDTO> keyWords;

    public ProjectDTO() {
    }

    public ProjectDTO(String number, Integer year, String relevance, String title, String client, String countries, String regions, String towns, List<SegmentDTO> segments, TypeDTO type, List<FormatDTO> formats, List<KeyWordDTO> keyWords) {
        this.number = number;
        this.year = year;
        this.relevance = relevance;
        this.title = title;
        this.client = client;
        this.countries = countries;
        this.regions = regions;
        this.towns = towns;
        this.segments = segments;
        this.type = type;
        this.formats = formats;
        this.keyWords = keyWords;
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

    public List<SegmentDTO> getSegments() {
        if (segments != null) {
            return segments.stream().sorted(Comparator.comparing(SegmentDTO::getName)).collect(Collectors.toList());
        }
        return null;
    }

    public void setSegments(List<SegmentDTO> segments) {
        this.segments = segments;
    }

    public TypeDTO getType() {
        return type;
    }

    public void setType(TypeDTO type) {
        this.type = type;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public List<FormatDTO> getFormats() {
        if (formats != null) {
            return formats.stream().sorted(Comparator.comparing(FormatDTO::getName)).collect(Collectors.toList());
        }
        return null;
    }

    public void setFormats(List<FormatDTO> formats) {
        this.formats = formats;
    }

    public List<KeyWordDTO> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<KeyWordDTO> keyWords) {
        this.keyWords = keyWords;
    }
}
