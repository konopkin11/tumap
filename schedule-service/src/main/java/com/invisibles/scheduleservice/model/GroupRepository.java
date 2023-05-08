package com.invisibles.scheduleservice.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {


    Optional<GroupEntity> findByUrlKeyContaining(String name);

    Optional<GroupEntity> findByUrlKey(String urlKey);

    Optional<GroupEntity> findByNumber(String urlKey);
}
