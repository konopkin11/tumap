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

@Entity(name = "Group")
@Table(name = "groups")
@Data
public class GroupEntity {
    @Id
    @ToString.Exclude
    @Getter(onMethod_ = {@JsonIgnore})
    @GeneratedValue
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;


    @Column(
            name = "number",
            nullable = false,
            columnDefinition = "TEXT",
            unique = true
    )
    private String number;


    @Column(
            name = "faculty",
            columnDefinition = "TEXT"
    )
    private String faculty;


    @Column(
            name = "urlkey",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String urlKey;


    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Getter(onMethod_ = {@JsonIgnore})
    @Setter
    @ManyToMany(mappedBy = "groupEntities", fetch = FetchType.LAZY)
    private Set<LessonEntity> lessons = new HashSet<>();
}
