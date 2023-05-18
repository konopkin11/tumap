package com.invisibles.scheduleservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invisibles.scheduleservice.model.AuditoriumEntity;
import com.invisibles.scheduleservice.service.AuditoriumService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("auditorium")
public class AuditoriumController {

    @Autowired
    private AuditoriumService auditoriumService;

//    @PostMapping
//    public ResponseEntity<AuditoriumEntity> createAuditorium(@RequestBody AuditoriumEntity auditorium){
//        AuditoriumEntity savedAuditorium = auditoriumService.createAuditorium(auditorium);
//        return new ResponseEntity<>(savedAuditorium, HttpStatus.CREATED);
//    }


    // build get user by id REST API
    // http://localhost:8080/api/users/1
    @GetMapping("{id}")
    public ResponseEntity<AuditoriumEntity> getAuditoriumById(@PathVariable("id") Long Id){
        AuditoriumEntity auditorium = auditoriumService.getAuditoriumById(Id);
        return new ResponseEntity<>(auditorium, HttpStatus.OK);
    }

    @GetMapping("{url}")
    public ResponseEntity<AuditoriumEntity> getAuditoriumByUrl(@PathVariable("id") String url){
        Optional<AuditoriumEntity> auditorium = auditoriumService. getAuditoriumByUrl(url);
        if(auditorium.isPresent()){
        return new ResponseEntity<>(auditorium.get(), HttpStatus.OK);}

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // Build Get All Users REST API
    // http://localhost:8080/api/users
    @GetMapping
    public ResponseEntity<List<AuditoriumEntity>> getAllAuditoriums(){
        List<AuditoriumEntity> auditoriums = auditoriumService.getAllAuditoriums();
        return new ResponseEntity<>(auditoriums, HttpStatus.OK);
    }

    // Build Update User REST API
//    @PutMapping("{id}")
//    // http://localhost:8080/api/users/1
//    public ResponseEntity<AuditoriumEntity> updateUser(@PathVariable("id") Long userId,
//                                           @RequestBody AuditoriumEntity auditorium){
//        auditorium.setId(userId);
//        AuditoriumEntity updatedAuditorium = auditoriumService.updateAuditorium(auditorium);
//        return new ResponseEntity<>(updatedAuditorium, HttpStatus.OK);
//    }
//
//    // Build Delete User REST API
//    @DeleteMapping("{id}")
//    public ResponseEntity<String> deleteAuditorium(@PathVariable("id") Long Id){
//        auditoriumService.deleteAuditorium(Id);
//        return new ResponseEntity<>("Auditorium successfully deleted!", HttpStatus.OK);
//    }
}
