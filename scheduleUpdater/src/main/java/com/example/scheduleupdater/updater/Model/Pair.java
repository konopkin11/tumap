package com.example.scheduleupdater.updater.Model;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class Pair {
    private String name;
    private String pairType;
    private List<Auditorium> auditorium;
    private List<Group> group;
    private String note;

    private List<Teacher> teacher;
    public void setDate(String date) {
        this.date = date;
    }

    private String date;
    private List<String> time;
    private List<CourseLink> links;
    private String lessonId;

    public Pair(String name) {
        this.name = name;
    }

    public Pair(String name, String pairType, List<Auditorium> auditorium, List<Group> group, String note, List<Teacher> teacher, String date, List<String> time, List<CourseLink> links, String lessonId) {
        this.name = name;
        this.pairType = pairType;
        this.auditorium = auditorium;
        this.group = group;
        this.note = note;
        this.teacher = teacher;
        this.date = date;
        this.time = time;
        this.links = links;
        this.lessonId = lessonId;
    }

    public Pair(String name, String pairType, List<Auditorium> auditorium, List<Group> group, String note, List<Teacher> teacher, List<CourseLink> links, String lessonId) {
        this.name = name;
        this.pairType = pairType;
        this.auditorium = auditorium;
        this.group = group;
        this.note = note;
        this.teacher = teacher;
        this.links = links;
        this.lessonId = lessonId;
    }

    // Getters and Setters

    public void setTime(List<String> time) {
        this.time = time;
    }
}
