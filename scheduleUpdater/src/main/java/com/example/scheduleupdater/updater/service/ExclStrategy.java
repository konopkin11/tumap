package com.example.scheduleupdater.updater.service;

import com.example.scheduleupdater.updater.scheduleModels.AuditoriumEntity;
import com.example.scheduleupdater.updater.scheduleModels.GroupEntity;
import com.example.scheduleupdater.updater.scheduleModels.TeacherEntity;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class ExclStrategy implements ExclusionStrategy {

    public boolean shouldSkipClass(Class<?> arg0) {
        return false;
    }

    public boolean shouldSkipField(FieldAttributes f) {

        return (f.getDeclaringClass() == TeacherEntity.class && f.getName().equals("lessons"))||
                (f.getDeclaringClass() == GroupEntity.class && f.getName().equals("lessons"))||
                (f.getDeclaringClass() == AuditoriumEntity.class && f.getName().equals("lessons"));

    }

}