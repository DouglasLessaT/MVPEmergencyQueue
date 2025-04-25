package com.DouglasLessa.emergencyBedApi.services.domain;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DouglasLessa.emergencyBedApi.model.domain.Ambulance;
import com.DouglasLessa.emergencyBedApi.repository.domain.AmbulanceRepository;

@Service
public class AmbulanceService {

    @Autowired
    private AmbulanceRepository ambulanceRepository;

    public Ambulance save(Ambulance ambulance) {
        return ambulanceRepository.save(ambulance);
    }

    public Ambulance findById(UUID id) {
        return ambulanceRepository.findById(id).orElseThrow(() -> new RuntimeException("Ambulance not found with id: " + id));
    }

    public void delete(UUID id) {
        Ambulance ambulance = findById(id);
        ambulanceRepository.delete(ambulance);
    }
}