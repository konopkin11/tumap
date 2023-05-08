package com.invisibles.scheduleservice.service;

import java.util.List;

import com.invisibles.scheduleservice.model.TeacherEntity;

public interface TeacherService {
    TeacherEntity createTeacher(TeacherEntity teacher);

    TeacherEntity getTeacherById(Long Id);


    List<TeacherEntity> getAllTeachers();

    TeacherEntity updateTeacher(TeacherEntity teacher);

    void deleteTeacher(Long Id);
}
