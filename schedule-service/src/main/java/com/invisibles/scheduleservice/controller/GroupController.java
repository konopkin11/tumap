package com.invisibles.scheduleservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invisibles.scheduleservice.model.GroupEntity;
import com.invisibles.scheduleservice.service.GroupService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("group")
public class GroupController {

    @Autowired
    private GroupService groupService;

   /* @PostMapping
    public ResponseEntity<GroupEntity> createGroup(@RequestBody GroupEntity group){
        GroupEntity savedGroup = groupService.createGroup(group);
        return new ResponseEntity<>(savedGroup, HttpStatus.CREATED);
    }*/

    // build get user by id REST API
    // http://localhost:8080/api/users/1
//    @GetMapping("{id}")
//    public ResponseEntity<GroupEntity> getUserById(@PathVariable("id") Long Id){
//        GroupEntity group = groupService.getGroupById(Id);
//        return new ResponseEntity<>(group, HttpStatus.OK);
//    }
    @GetMapping("{number}")
    public ResponseEntity<GroupEntity> getGroupByNumber(@PathVariable("number") String number){
        GroupEntity group = groupService.getGroupByNumber(number);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }


    // Build Get All Users REST API
    // http://localhost:8080/api/users
    @GetMapping
    public ResponseEntity<List<GroupEntity>> getAllGroups(){
        List<GroupEntity> groups = groupService.getAllGroups();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    // Build Update User REST API
//    @PutMapping("{id}")
//    // http://localhost:8080/api/users/1
//    public ResponseEntity<GroupEntity> updateUser(@PathVariable("id") Long groupId,
//                                                   @RequestBody GroupEntity group){
//        group.setId(groupId);
//        GroupEntity updatedGroup = groupService.updateGroup(group);
//        return new ResponseEntity<>(updatedGroup, HttpStatus.OK);
//    }
//
//    // Build Delete User REST API
//    @DeleteMapping("{id}")
//    public ResponseEntity<String> deleteGroup(@PathVariable("id") Long Id){
//        groupService.deleteGroup(Id);
//        return new ResponseEntity<>("Group successfully deleted!", HttpStatus.OK);
//    }

    @PostMapping
    public ResponseEntity<Boolean> createListGroups(@RequestBody List<GroupEntity> group){
        groupService.createAllGroups(group);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }
}
