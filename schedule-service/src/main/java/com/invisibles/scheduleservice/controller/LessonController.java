package com.invisibles.scheduleservice.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.invisibles.scheduleservice.model.LessonEntity;
import com.invisibles.scheduleservice.service.LessonService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("lesson")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @PostMapping
    public ResponseEntity<LessonEntity> createLesson(@RequestBody LessonEntity lesson){
        LessonEntity savedLesson = lessonService.createLesson(lesson);
        return new ResponseEntity<>(savedLesson, HttpStatus.CREATED);
    }

    // build get user by id REST API
    // http://localhost:8080/api/users/1
    @GetMapping("{id}")
    public ResponseEntity<LessonEntity> getLessonById(@PathVariable("id") String Id){
        LessonEntity lesson = lessonService.getLessonById(Id).get();
       return new ResponseEntity<>(lesson, HttpStatus.OK);
    }
    @GetMapping("{id}/date/{date}")
    public ResponseEntity<LessonEntity> getLessonByIdAndDate(@PathVariable("id") String Id, @PathVariable("date") String date){
        LessonEntity lesson = lessonService.getLessonByIdAndDate(Id, date).get();
        return new ResponseEntity<>(lesson, HttpStatus.OK);
    }
    @GetMapping("/{number}/from/{date1}/to/{date2}")
    public ResponseEntity<List<LessonEntity>> getLessonsBetweenDates(@PathVariable("number") String groupNumber,@PathVariable("date1") String date1, @PathVariable("date2") String date2){
       List<LessonEntity> optionalLessonEntities = lessonService.getLessonsBetweenDates(date1, date2, groupNumber);
//        if(optionalLessonEntities.isEmpty()){
//            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
//        }
        return new ResponseEntity<>(optionalLessonEntities, HttpStatus.OK);
    }

    @GetMapping("/{number}/month/{randomDateInMonth}")
    public ResponseEntity<List<LessonEntity>> getLessonsBetweenDates(@PathVariable("randomDateInMonth") String date1, @PathVariable("number") String groupDumber){
        List<LessonEntity> optionalLessonEntities = lessonService.getLessonsForMonth("01."+date1, groupDumber);

        return new ResponseEntity<>(optionalLessonEntities, HttpStatus.OK);
    }
    // Build Get All Users REST API
    // http://localhost:8080/api/users
//    @GetMapping
//    public ResponseEntity<List<LessonEntity>> getAllLesson(){
//        List<LessonEntity> lessons = lessonService.getAllLessons();
//        return new ResponseEntity<>(lessons, HttpStatus.OK);
//    }

    // Build Update User REST API
//    @PutMapping("{id}")
//    // http://localhost:8080/api/users/1
//    public ResponseEntity<LessonEntity> updateUser(@PathVariable("id") Long lessonId,
//                                                       @RequestBody LessonEntity lesson){
//        lesson.setId(lessonId);
//        LessonEntity updatedLesson = lessonService.updateLesson(lesson);
//
//        return new ResponseEntity<>(updatedLesson, HttpStatus.OK);
//    }

    @GetMapping("/group/{urlKey}")
    public ResponseEntity<List<LessonEntity>> getAllLessonByGroupUrlKey(@PathVariable("urlKey") String urlKey){
        List<LessonEntity> lessons = lessonService.getLessonByGroupUrlKey(urlKey);
        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }
    @GetMapping("/teacher/{name}")
    public ResponseEntity<List<LessonEntity>> getAllLessonByTeacherName(@PathVariable("name") String name){
        List<LessonEntity> lessons = lessonService.getLessonByTeacherName(name);
        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }
    @GetMapping("/teacher/{name}/month/{randomDateInMonth}")
    public ResponseEntity<List<LessonEntity>> getAllLessonByTeacherNameBeetweenDates(@PathVariable("randomDateInMonth") String date1 ,@PathVariable("name") String name){
        List<LessonEntity> lessons = lessonService.getLessonsForMonthTeacher(date1, name);
        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }
    @GetMapping("/teacher/{name}/from/{date1}/to/{date2}")
    public ResponseEntity<List<LessonEntity>> getAllLessonByTeacherNameBetweenDates(@PathVariable("date1") String date1, @PathVariable("date2") String date2 ,@PathVariable("name") String name){
        List<LessonEntity> lessons = lessonService.getLessonsBetweenDatesTeacher(date1, date2, name);
        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }
    @GetMapping("/auditorium/{number}")
    public ResponseEntity<List<LessonEntity>> getAllLessonByAuditoriumNumber(@PathVariable("number") String number){
        List<LessonEntity> lessons = lessonService.getLessonByGroupUrlKey(number);
        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }

    @GetMapping("/auditorium/{name}/month/{randomDateInMonth}")
    public ResponseEntity<List<LessonEntity>> getAllLessonByAuditoriumNumberBetweenDates(@PathVariable("randomDateInMonth") String date1 ,@PathVariable("name") String name){
        List<LessonEntity> lessons = lessonService.getLessonsForMonthAuditorium(date1, name);
        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }
    @GetMapping("/auditorium/{name}/from/{date1}/to/{date2}")
    public ResponseEntity<List<LessonEntity>> getAllLessonByAuditoriumNumberBetweenDates(@PathVariable("date1") String date1, @PathVariable("date2") String date2 ,@PathVariable("name") String name){
        List<LessonEntity> lessons = lessonService.getLessonsBetweenAuditorium(date1, date2, name);
        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }


//    // Build Delete User REST API
//    @DeleteMapping("{id}")
//    public ResponseEntity<String> deleteLesson(@PathVariable("id") Long Id){
//        lessonService.deleteLesson(Id);
//        return new ResponseEntity<>("Lesson successfully deleted!", HttpStatus.OK);
//    }
}
