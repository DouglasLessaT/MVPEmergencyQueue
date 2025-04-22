package com.douglasLessa.emergencyBedApi.services.model;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglasLessa.emergencyBedApi.model.Patient;
import com.douglasLessa.emergencyBedApi.repository.model.PatientRepository;

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