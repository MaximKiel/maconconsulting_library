package ru.maconconsulting.library.utils.search;

import ru.maconconsulting.library.dto.parameters.FormatDTO;
import ru.maconconsulting.library.dto.parameters.KeyWordDTO;
import ru.maconconsulting.library.dto.parameters.SegmentDTO;
import ru.maconconsulting.library.dto.parameters.TypeDTO;

public class SearchProject {

    private int year;

    private String relevance;

    private String title;

    private String client;

    private String location;

    private SegmentDTO segment;

    private TypeDTO type;

    private FormatDTO format;

    private KeyWordDTO keyWord;

    public SearchProject() {
    }

    public SearchProject(int year, String relevance, String title, String client, String location, SegmentDTO segment, TypeDTO type, FormatDTO format, KeyWordDTO keyWord) {
        this.year = year;
        this.relevance = relevance;
        this.title = title;
        this.client = client;
        this.location = location;
        this.segment = segment;
        this.type = type;
        this.format = format;
        this.keyWord = keyWord;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public SegmentDTO getSegment() {
        return segment;
    }

    public void setSegment(SegmentDTO segment) {
        this.segment = segment;
    }

    public TypeDTO getType() {
        return type;
    }

    public void setType(TypeDTO type) {
        this.type = type;
    }

    public FormatDTO getFormat() {
        return format;
    }

    public void setFormat(FormatDTO format) {
        this.format = format;
    }

    public KeyWordDTO getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(KeyWordDTO keyWord) {
        this.keyWord = keyWord;
    }
}
