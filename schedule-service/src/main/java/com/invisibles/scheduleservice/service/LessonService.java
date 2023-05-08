package com.invisibles.scheduleservice.service;

import java.util.List;
import java.util.Optional;

import com.invisibles.scheduleservice.model.LessonEntity;


public interface LessonService {
    LessonEntity createLesson(LessonEntity lesson);

    Optional<LessonEntity> getLessonById(String Id);

    List<LessonEntity> getAllLessons();

    LessonEntity updateLesson(LessonEntity lesson);

    List<LessonEntity> getLessonByGroupUrlKey(String urlKey);
    List<LessonEntity> getLessonByAuditoriumNumber(String number);
    List<LessonEntity> getLessonByTeacherName(String name);


    List<LessonEntity> getLessonsBetweenDates(String date1, String date2, String group);
    List<LessonEntity> getLessonsForMonth(String date, String group);


    void deleteLesson(Long Id);
}
