package com.invisibles.scheduleservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Сourselink")
@Table(name = "courselink")
@Data
public class CourseLinkEntity {

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
            name = "url",
            columnDefinition = "TEXT"
    )
    private String url;

    @Column(
            name = "anchor",
            columnDefinition = "TEXT"
    )
    private String anchor;

    @Column(
            name = "groupname",
            columnDefinition = "TEXT"
    )
    private String groupname;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Getter(onMethod_ = {@JsonIgnore})
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private LessonEntity lesson;
}
