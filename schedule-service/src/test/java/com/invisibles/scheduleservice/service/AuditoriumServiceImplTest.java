package com.invisibles.scheduleservice.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import com.invisibles.scheduleservice.model.AuditoriumEntity;
import com.invisibles.scheduleservice.model.AuditoriumRepository;

import static org.mockito.Mockito.*;
class AuditoriumServiceImplTest {
    @InjectMocks
    AuditoriumServiceImpl auditoriumService;

    @Mock
    AuditoriumRepository auditoriumRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateAuditorium() {
        AuditoriumEntity auditorium = new AuditoriumEntity();
        when(auditoriumRepository.save(any())).thenReturn(auditorium);
        auditoriumService.createAuditorium(auditorium);
        verify(auditoriumRepository).save(auditorium);
    }

    @Test
    void testGetAuditoriumById() {
        AuditoriumEntity auditorium = new AuditoriumEntity();
        when(auditoriumRepository.findById(anyLong())).thenReturn(Optional.of(auditorium));
        auditoriumService.getAuditoriumById(1L);
        verify(auditoriumRepository).findById(1L);
    }

    @Test
    void testGetAuditoriumByUrl() {
        when(auditoriumRepository.findByUrlKey(anyString())).thenReturn(Optional.of(new AuditoriumEntity()));
        auditoriumService.getAuditoriumByUrl("url");
        verify(auditoriumRepository).findByUrlKey("url");
    }

    @Test
    void testGetAllAuditoriums() {
        when(auditoriumRepository.findAll()).thenReturn(Arrays.asList(new AuditoriumEntity()));
        auditoriumService.getAllAuditoriums();
        verify(auditoriumRepository).findAll();
    }

    @Test
    void testUpdateAuditorium() {
        AuditoriumEntity auditorium = new AuditoriumEntity();
        auditorium.setId(1L);
        when(auditoriumRepository.findById(anyLong())).thenReturn(Optional.of(auditorium));
        when(auditoriumRepository.save(any())).thenReturn(auditorium);
        auditoriumService.updateAuditorium(auditorium);
        verify(auditoriumRepository).save(auditorium);
    }

    @Test
    void testDeleteAuditorium() {
        doNothing().when(auditoriumRepository).deleteById(anyLong());
        auditoriumService.deleteAuditorium(1L);
        verify(auditoriumRepository).deleteById(1L);
    }
}