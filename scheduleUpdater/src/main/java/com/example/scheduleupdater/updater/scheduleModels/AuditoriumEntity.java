package com.example.scheduleupdater.updater.scheduleModels;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Data
public class AuditoriumEntity {

    private Long id;

    private String number;

    private String building;


    private Set<LessonEntity> lessons = new HashSet<>();


    private String urlKey;




}
