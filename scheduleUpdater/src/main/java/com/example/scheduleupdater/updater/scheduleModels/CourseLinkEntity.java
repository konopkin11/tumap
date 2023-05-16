package com.example.scheduleupdater.updater.scheduleModels;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Data
public class CourseLinkEntity {



    private Long id;


    private String url;


    private String anchor;


    private String groupname;

    private LessonEntity lesson;
}
