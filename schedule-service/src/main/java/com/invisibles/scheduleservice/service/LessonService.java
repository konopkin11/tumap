package com.invisibles.scheduleservice.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.invisibles.scheduleservice.model.LessonEntity;


public interface LessonService {
    LessonEntity createLesson(LessonEntity lesson);

    Optional<LessonEntity> getLessonById(String Id);

    List<LessonEntity> getAllLessons();

    LessonEntity updateLesson(LessonEntity lesson);
    public Optional<LessonEntity> getLessonByIdAndDate(String Id, String date);
    List<LessonEntity> getLessonByGroupUrlKey(String urlKey);
    List<LessonEntity> getLessonByAuditoriumNumber(String number);
    List<LessonEntity> getLessonByTeacherName(String name);


    List<LessonEntity> getLessonsBetweenDates(String date1, String date2, String group);
    List<LessonEntity> getLessonsForMonth(String date, String group);

    List<LessonEntity> getLessonsBetweenDatesTeacher(String date1, String date2, String teacherName);
    List<LessonEntity> getLessonsForMonthTeacher(String date, String teacherName);
    List<LessonEntity> getLessonsBetweenAuditorium(String date1, String date2, String auditoriumNumber);
    List<LessonEntity> getLessonsForMonthAuditorium(String date, String auditoriumNumber);
    void deleteLesson(Long Id);
}

