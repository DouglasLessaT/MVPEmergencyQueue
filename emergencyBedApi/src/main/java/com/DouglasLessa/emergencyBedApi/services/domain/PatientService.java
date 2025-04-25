package com.DouglasLessa.emergencyBedApi.services.domain;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DouglasLessa.emergencyBedApi.model.domain.Patient;
import com.DouglasLessa.emergencyBedApi.repository.domain.PatientRepository;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient findById(UUID id) {
        return patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }

    public void delete(UUID id) {
        Patient patient = findById(id);
        patientRepository.delete(patient);
    }
}