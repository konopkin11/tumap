package com.invisibles.scheduleservice.service;

import java.util.List;

import com.invisibles.scheduleservice.model.GroupEntity;

public interface GroupService {

    GroupEntity createGroup(GroupEntity group);

    GroupEntity getGroupById(Long Id);

    List<GroupEntity> getAllGroups();


    GroupEntity updateGroup(GroupEntity group);

    void deleteGroup(Long Id);
}
