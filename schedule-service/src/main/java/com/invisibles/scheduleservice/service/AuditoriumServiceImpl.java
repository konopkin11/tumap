package com.invisibles.scheduleservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.invisibles.scheduleservice.model.AuditoriumEntity;
import com.invisibles.scheduleservice.model.AuditoriumRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuditoriumServiceImpl implements AuditoriumService{
    private AuditoriumRepository auditoriumRepository;


    @Override
    public AuditoriumEntity createAuditorium(AuditoriumEntity auditorium) {
        return auditoriumRepository.save(auditorium);
    }

    @Override
    public AuditoriumEntity getAuditoriumById(Long Id) {
        AuditoriumEntity auditorium = auditoriumRepository.findById(Id).get();
        return auditorium;
    }

    @Override
    public Optional<AuditoriumEntity> getAuditoriumByUrl(String Url) {
        return auditoriumRepository.findByUrlKey(Url);

    }

    @Override
    public List<AuditoriumEntity> getAllAuditoriums() {
        return  auditoriumRepository.findAll();
    }

    @Override
    public AuditoriumEntity updateAuditorium(AuditoriumEntity auditorium) {

        AuditoriumEntity existingAuditorium = auditoriumRepository.findById(auditorium.getId()).get();
        existingAuditorium.setNumber(auditorium.getNumber());
        existingAuditorium.setBuilding(auditorium.getBuilding());
        existingAuditorium.setUrlKey(auditorium.getUrlKey());
        AuditoriumEntity updatedAuditorium = auditoriumRepository.save(existingAuditorium);
        return updatedAuditorium;
    }

    @Override
    public void deleteAuditorium(Long Id) {
        auditoriumRepository.deleteById(Id);

    }
}
