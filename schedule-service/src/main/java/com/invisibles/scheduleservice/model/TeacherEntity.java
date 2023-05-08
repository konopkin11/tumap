package com.invisibles.scheduleservice.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Teacher")
@Table(name = "teachers")
@Data

public class TeacherEntity {

    @Id
    @GeneratedValue
    @ToString.Exclude
    @Getter(onMethod_ = {@JsonIgnore})
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "url",
            columnDefinition = "TEXT"
    )
    private String url;

    @Column(
            name = "name",

            columnDefinition = "TEXT"
    )
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Getter(onMethod_ = {@JsonIgnore})
    @Setter
    @ManyToMany(mappedBy = "teachers", fetch = FetchType.LAZY)
    private Set<LessonEntity> lessons = new HashSet<>();

    public void addLesson(LessonEntity lesson) {
        //lesson.setTeacher(this);
        this.lessons.add(lesson);
    }
}


