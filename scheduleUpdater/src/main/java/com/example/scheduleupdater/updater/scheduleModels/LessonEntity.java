package com.example.scheduleupdater.updater.scheduleModels;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;


@Data

public class LessonEntity {

    private Long id;

    String timeStart;

    String timeEnd;
    String date;


    String lessonId;

    String type;

    String name;



    private Set<TeacherEntity> teachers;

    private Set<AuditoriumEntity> auditoriums;


    private Set<GroupEntity> groupEntities;


    String lessonNote;

    private Set<CourseLinkEntity> courseLinks = new HashSet<>();
    public void addCourseLink(CourseLinkEntity link) {
        courseLinks.add(link);
        link.setLesson(this);
    }
    public void removeCourseLink(CourseLinkEntity link) {
        courseLinks.remove(link);
        link.setLesson(null);
    }

}
