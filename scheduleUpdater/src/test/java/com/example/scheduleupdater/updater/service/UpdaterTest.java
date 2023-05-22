package com.example.scheduleupdater.updater.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.scheduleupdater.updater.scheduleModels.GroupEntity;

import static org.junit.jupiter.api.Assertions.*;

class UpdaterTest {

    @Test
    void sendGroupsToService() {
        List<GroupEntity> groupEntities = new ArrayList<>();
        groupEntities.add(new GroupEntity());
        groupEntities.add(new GroupEntity());
        groupEntities.add(new GroupEntity());
        String gr = groupEntities.toString();
        assert (gr != null);
    }
}