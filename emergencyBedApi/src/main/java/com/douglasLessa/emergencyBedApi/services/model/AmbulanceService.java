package com.douglasLessa.emergencyBedApi.services.model;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglasLessa.emergencyBedApi.model.Ambulance;
import com.douglasLessa.emergencyBedApi.repository.model.AmbulanceRepository;

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