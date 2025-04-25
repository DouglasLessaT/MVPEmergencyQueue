package com.DouglasLessa.emergencyBedApi.services.domain;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DouglasLessa.emergencyBedApi.model.domain.MedicalCare;
import com.DouglasLessa.emergencyBedApi.repository.domain.MedicalCareRepository;

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