package com.invisibles.scheduleservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.invisibles.scheduleservice.model.TeacherEntity;
import com.invisibles.scheduleservice.model.TeacherRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService{

    private TeacherRepository teacherRepository;


    @Override
    public TeacherEntity createTeacher(TeacherEntity teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public TeacherEntity getTeacherById(Long Id) {
        Optional<TeacherEntity> optionalTeacher = teacherRepository.findById(Id);
        return optionalTeacher.get();
    }





    @Override
    public List<TeacherEntity> getAllTeachers() {
       return (List<TeacherEntity>) teacherRepository.findAll();
    }

    @Override
    public TeacherEntity updateTeacher(TeacherEntity teacher) {
        TeacherEntity existingTeacher = teacherRepository.findById(teacher.getId()).get();
        existingTeacher.setUrl(teacher.getUrl());
        existingTeacher.setName(teacher.getName());
        existingTeacher.setLessons(teacher.getLessons());
        TeacherEntity updatedTeacher = teacherRepository.save(existingTeacher);
        return updatedTeacher;
    }

    @Override
    public void deleteTeacher(Long Id) {
        teacherRepository.deleteById(Id);
    }
}
