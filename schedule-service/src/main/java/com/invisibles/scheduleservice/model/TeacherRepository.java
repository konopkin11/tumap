package com.invisibles.scheduleservice.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
    List<TeacherEntity> findAllByUrl(String number);

    Optional<TeacherEntity> findByNameContaining(String name);
}
