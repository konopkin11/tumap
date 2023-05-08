package com.invisibles.scheduleservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.invisibles.scheduleservice.model.GroupEntity;
import com.invisibles.scheduleservice.model.GroupRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;

    @Override
    public GroupEntity createGroup(GroupEntity group) {
        return groupRepository.save(group);
    }

    @Override
    public GroupEntity getGroupById(Long Id) {
        Optional<GroupEntity> optionalGroup = groupRepository.findById(Id);
        return optionalGroup.get();
    }

    @Override
    public List<GroupEntity> getAllGroups() {
        return (List<GroupEntity>) groupRepository.findAll();
    }

    @Override
    public GroupEntity updateGroup(GroupEntity group) {
        GroupEntity existingGroup = groupRepository.findById(group.getId()).get();
        existingGroup.setNumber(group.getNumber());
        existingGroup.setFaculty(group.getFaculty());
        existingGroup.setUrlKey(group.getUrlKey());
        GroupEntity updatedGroup = groupRepository.save(existingGroup);
        return updatedGroup;
    }

    @Override
    public void deleteGroup(Long Id) {
        groupRepository.deleteById(Id);
    }
}
