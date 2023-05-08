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

@Entity(name = "Auditorium")
@Table(name = "auditorium")
@Data
public class AuditoriumEntity {
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
            columnDefinition = "TEXT",
            unique = true
    )
    private String number;
    @Column(
            name = "building",
            columnDefinition = "TEXT"
    )
    private String building;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Getter(onMethod_ = {@JsonIgnore})
    @Setter
    @ManyToMany(mappedBy = "auditoriums", fetch = FetchType.LAZY)
    private Set<LessonEntity> lessons = new HashSet<>();

    @Column(
            name = "urlkey",

            columnDefinition = "TEXT"
    )
    private String urlKey;




}
