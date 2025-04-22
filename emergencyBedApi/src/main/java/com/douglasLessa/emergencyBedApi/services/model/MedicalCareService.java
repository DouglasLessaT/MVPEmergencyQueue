package com.douglasLessa.emergencyBedApi.services.model;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglasLessa.emergencyBedApi.model.MedicalCare;
import com.douglasLessa.emergencyBedApi.repository.model.MedicalCareRepository;

@Service
public class MedicalCareService {

    @Autowired
    private MedicalCareRepository medicalCareRepository;

    public MedicalCare save(MedicalCare medicalCare) {
        return medicalCareRepository.save(medicalCare);
    }

    public MedicalCare findById(UUID id) {
        return medicalCareRepository.findById(id).orElseThrow(() -> new RuntimeException("MedicalCare not found with id: " + id));
    }

    public void delete(UUID id) {
        MedicalCare medicalCare = findById(id);
        medicalCareRepository.delete(medicalCare);
    }
}