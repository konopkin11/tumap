package com.invisibles.scheduleservice.service;

import java.util.List;
import java.util.Optional;

import com.invisibles.scheduleservice.model.AuditoriumEntity;

public interface AuditoriumService {
    AuditoriumEntity createAuditorium(AuditoriumEntity auditorium);

    AuditoriumEntity getAuditoriumById(Long Id);
    Optional<AuditoriumEntity> getAuditoriumByUrl(String Url);

    List<AuditoriumEntity> getAllAuditoriums();

    AuditoriumEntity updateAuditorium(AuditoriumEntity auditorium);

    void deleteAuditorium(Long Id);
}
