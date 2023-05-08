package com.invisibles.scheduleservice.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriumRepository extends JpaRepository<AuditoriumEntity, Long> {
    List<AuditoriumEntity> findAllByNumberContaining(String number);


    Optional<AuditoriumEntity> findByUrlKey(String name);
    Optional<AuditoriumEntity> findByNumber(String name);

}
