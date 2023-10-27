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
}
