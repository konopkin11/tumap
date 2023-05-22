package com.example.scheduleupdater.updater.scheduleModels;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data

public class TeacherEntity {


    private Long id;


    private String url;


    private String name;

    private Set<LessonEntity> lessons = new HashSet<>();

    public void addLesson(LessonEntity lesson) {
        //lesson.setTeacher(this);
        this.lessons.add(lesson);
    }
}


