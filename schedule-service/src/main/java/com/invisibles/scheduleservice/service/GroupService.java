package com.invisibles.scheduleservice.service;

import java.util.List;

import com.invisibles.scheduleservice.model.GroupEntity;

public interface GroupService {

    GroupEntity createGroup(GroupEntity group);
    List<GroupEntity> createAllGroups(List<GroupEntity> group);

    GroupEntity getGroupById(Long Id);
    GroupEntity getGroupByNumber(String Number);

    List<GroupEntity> getAllGroups();


    GroupEntity updateGroup(GroupEntity group);

    void deleteGroup(Long Id);
}
