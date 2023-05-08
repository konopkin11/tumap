package com.invisibles.scheduleservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invisibles.scheduleservice.model.TeacherEntity;
import com.invisibles.scheduleservice.service.TeacherService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

//    @PostMapping
//    public ResponseEntity<TeacherEntity> createTeacher(@RequestBody TeacherEntity teacher){
//        TeacherEntity savedTeacher = teacherService.createTeacher(teacher);
//        return new ResponseEntity<>(savedTeacher, HttpStatus.CREATED);
//    }

    // build get user by id REST API
    // http://localhost:8080/api/users/1
    @GetMapping("{id}")
    public ResponseEntity<TeacherEntity> getUserById(@PathVariable("id") Long Id){
        TeacherEntity teacher = teacherService.getTeacherById(Id);
        return new ResponseEntity<>(teacher, HttpStatus.OK);
    }




    // Build Get All Users REST API
    // http://localhost:8080/api/users
    @GetMapping
    public ResponseEntity<List<TeacherEntity>> getAllUsers(){
        List<TeacherEntity> teachers = teacherService.getAllTeachers();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    // Build Update User REST API
//    @PutMapping("{id}")
//    // http://localhost:8080/api/users/1
//    public ResponseEntity<TeacherEntity> updateUser(@PathVariable("id") Long teacherId,
//                                                   @RequestBody TeacherEntity teacher){
//        teacher.setId(teacherId);
//        TeacherEntity updatedTeacher = teacherService.updateTeacher(teacher);
//        return new ResponseEntity<>(updatedTeacher, HttpStatus.OK);
//    }
//
//    // Build Delete User REST API
//    @DeleteMapping("{id}")
//    public ResponseEntity<String> deleteTeacher(@PathVariable("id") Long Id){
//        teacherService.deleteTeacher(Id);
//        return new ResponseEntity<>("Teacher successfully deleted!", HttpStatus.OK);
//    }
}
