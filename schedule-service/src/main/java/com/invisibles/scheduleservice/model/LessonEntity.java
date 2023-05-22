package com.invisibles.scheduleservice.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Lesson")
@Table(name = "lesson")
@Data

public class LessonEntity {
    @Id
    @GeneratedValue
    @ToString.Exclude
    @Getter(onMethod_ = {@JsonIgnore})
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    LocalDate date;

    @Column(
            name = "timeStart",
            columnDefinition = "TEXT")
    String timeStart;
    @Column(
            name = "timeend",
            columnDefinition = "TEXT")
    String timeEnd;

    @Column(
            name = "lessonId",
            unique = true)
    String lessonId;
    @Column(
            name = "lessontype",
            columnDefinition = "TEXT")
    String type;
    @Column(
            name = "lessonname",
            columnDefinition = "TEXT")
    String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "teachers_lessons",
            joinColumns = {
                    @JoinColumn(name = "lesson_id", referencedColumnName = "id",
                            nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "teacher_id", referencedColumnName = "id",
                            nullable = false)})

    private Set<TeacherEntity> teachers;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "auditoriums_lessons",
            joinColumns = {
                    @JoinColumn(name = "lesson_id", referencedColumnName = "id",
                            nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "auditorium_id", referencedColumnName = "id",
                            nullable = false)})
    private Set<AuditoriumEntity> auditoriums;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "groups_lessons",
            joinColumns = {
                    @JoinColumn(name = "lesson_id", referencedColumnName = "id",
                            nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "group_id", referencedColumnName = "id",
                            nullable = false)})

    private Set<GroupEntity> groupEntities;

    @Column(
            name = "lessonNote",
            columnDefinition = "TEXT")
    String lessonNote;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
   // @ToString.Exclude
    //@EqualsAndHashCode.Exclude
    //@Getter(onMethod_ = {@JsonIgnore})
   // @Setter
    private Set<CourseLinkEntity> courseLinks = new HashSet<>();
    public void addCourseLink(CourseLinkEntity link) {
        courseLinks.add(link);
        link.setLesson(this);
    }
    public void removeCourseLink(CourseLinkEntity link) {
        courseLinks.remove(link);
        link.setLesson(null);
    }

}
