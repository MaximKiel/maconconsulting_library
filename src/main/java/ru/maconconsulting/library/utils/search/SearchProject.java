package ru.maconconsulting.library.utils.search;

import ru.maconconsulting.library.dto.parameters.*;

public class SearchProject {

    private int year;

    private String relevance;

    private String title;

    private String client;

    private String location;

    private ChapterDTO chapter;

    private SegmentDTO segment;

    private FormatDTO format;

    private String keyWord;

    public SearchProject() {
    }

    public SearchProject(int year, String relevance, String title, String client, String location, ChapterDTO chapter, SegmentDTO segment, FormatDTO format, String keyWord) {
        this.year = year;
        this.relevance = relevance;
        this.title = title;
        this.client = client;
        this.location = location;
        this.chapter = chapter;
        this.segment = segment;
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

    public ChapterDTO getChapter() {
        return chapter;
    }

    public void setChapter(ChapterDTO chapter) {
        this.chapter = chapter;
    }

    public SegmentDTO getSegment() {
        return segment;
    }

    public void setSegment(SegmentDTO segment) {
        this.segment = segment;
    }

    public FormatDTO getFormat() {
        return format;
    }

    public void setFormat(FormatDTO format) {
        this.format = format;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
