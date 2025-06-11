package com.DouglasLessa.EmergencyQueueApi.services.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DouglasLessa.EmergencyQueueApi.model.domain.Ambulance;
import com.DouglasLessa.EmergencyQueueApi.repository.domain.AmbulanceRepository;

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

    public List<Ambulance> findAll() {
        return ambulanceRepository.findAll();
    }

    public void delete(UUID id) {
        Ambulance ambulance = findById(id);
        ambulanceRepository.delete(ambulance);
    }

    public Ambulance update(UUID id, Ambulance ambulance) {
        if (ambulance.getId() == null) {
            throw new RuntimeException("Ambulance ID cannot be null");
        }
        if (!ambulance.getId().equals(id)) {
            throw new RuntimeException("Ambulance ID in the request body does not match the ID in the URL");
        }
        if (ambulance.getId() == null) {
            throw new RuntimeException("Ambulance ID cannot be null");
        }
        Ambulance existingAmbulance = findById(id);
    
        existingAmbulance.setLicensePlate(ambulance.getLicensePlate());
        existingAmbulance.setModel(ambulance.getModel());
        existingAmbulance.setType(ambulance.getType());
        existingAmbulance.setRenavam(ambulance.getRenavam());
        existingAmbulance.setHospital(ambulance.getHospital());

        return ambulanceRepository.save(existingAmbulance);
    }
}