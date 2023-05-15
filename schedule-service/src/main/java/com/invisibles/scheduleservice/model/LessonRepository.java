package com.invisibles.scheduleservice.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {


    List<LessonEntity> getLessonEntitiesByGroupEntitiesUrlKey(String urlKey);

    List<LessonEntity> getLessonEntitiesByTeachersName(String name);

    List<LessonEntity> getLessonEntitiesByAuditoriumsNumber(String number);

    List<LessonEntity> getLessonEntitiesByDateBetween(LocalDate date1, LocalDate date2);

    Optional<LessonEntity> getLessonEntityByLessonId(String lessonId);

    @Transactional
    @Query("SELECT DISTINCT l FROM Lesson l JOIN FETCH l.groupEntities ge JOIN FETCH l.auditoriums a JOIN FETCH l.teachers t JOIN l.courseLinks cl WHERE l.date BETWEEN :startDate AND :endDate AND ge.number = :groupName AND cl.groupname = :groupName")
    List<LessonEntity> findLessonsBetweenDatesAndGroupName(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("groupName") String groupName);

    @Transactional
    @Query("SELECT DISTINCT l FROM Lesson l JOIN FETCH l.groupEntities ge JOIN FETCH l.auditoriums a JOIN FETCH l.teachers t WHERE l.date BETWEEN :startDate AND :endDate AND t.name = :teacherName")
    List<LessonEntity> findLessonsBetweenDatesAndTeacherName(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("teacherName") String teacherName);
    @Transactional
    @Query("SELECT DISTINCT l FROM Lesson l JOIN FETCH l.groupEntities ge JOIN FETCH l.auditoriums a JOIN FETCH l.teachers t WHERE l.date BETWEEN :startDate AND :endDate AND a.number = :auditoriumNumber")
    List<LessonEntity> findLessonsBetweenDatesAndAuditoriumNumber(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("auditoriumNumber") String auditoriumNumber);
}
