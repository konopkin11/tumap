package com.example.scheduleupdater.updater.Model;

import java.util.Date;

import lombok.Data;

@Data
public class Group {
    private String key;
    private String name;
    private String faculty;

    public Group(String key, String name, String faculty) {
        this.key = key;
        this.name = name;
        this.faculty = faculty;
    }

    // Getters and Setters

    // toString method, if needed
}
