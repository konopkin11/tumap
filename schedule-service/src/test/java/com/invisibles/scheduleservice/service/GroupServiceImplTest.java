package com.invisibles.scheduleservice.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.invisibles.scheduleservice.model.GroupEntity;
import com.invisibles.scheduleservice.model.GroupRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class GroupServiceImplTest {

    @InjectMocks
    GroupServiceImpl groupService;

    @Mock
    GroupRepository groupRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateGroup() {
        GroupEntity group = new GroupEntity();
        when(groupRepository.save(any())).thenReturn(group);
        groupService.createGroup(group);
        verify(groupRepository).save(group);
    }

    @Test
    void testCreateAllGroups() {
        GroupEntity group = new GroupEntity();
        List<GroupEntity> groups = Arrays.asList(group);
        when(groupRepository.saveAll(anyList())).thenReturn(groups);
        groupService.createAllGroups(groups);
        verify(groupRepository).saveAll(groups);
    }

    @Test
    void testGetGroupById() {
        GroupEntity group = new GroupEntity();
        when(groupRepository.findById(anyLong())).thenReturn(Optional.of(group));
        groupService.getGroupById(1L);
        verify(groupRepository).findById(1L);
    }

    @Test
    void testGetGroupByNumber() {
        when(groupRepository.findByNumber(anyString())).thenReturn(Optional.of(new GroupEntity()));
        groupService.getGroupByNumber("number");
        verify(groupRepository).findByNumber("number");
    }

    @Test
    void testGetAllGroups() {
        when(groupRepository.findAll()).thenReturn(Arrays.asList(new GroupEntity()));
        groupService.getAllGroups();
        verify(groupRepository).findAll();
    }

    @Test
    void testUpdateGroup() {
        GroupEntity group = new GroupEntity();
        group.setId(1L);
        when(groupRepository.findById(anyLong())).thenReturn(Optional.of(group));
        when(groupRepository.save(any())).thenReturn(group);
        groupService.updateGroup(group);
        verify(groupRepository).save(group);
    }

    @Test
    void testDeleteGroup() {
        doNothing().when(groupRepository).deleteById(anyLong());
        groupService.deleteGroup(1L);
        verify(groupRepository).deleteById(1L);
    }
}
