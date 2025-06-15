package com.douglas.unisales.emegencyQueue.services.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglas.unisales.emegencyQueue.model.domain.Patient;
import com.douglas.unisales.emegencyQueue.repository.domain.PatientRepository;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient save(Patient patient) {
        validatePatient(patient);
        return patientRepository.save(patient);
    }

    public Patient findById(UUID id) {
        return patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }

    public void delete(UUID id) {
        Patient patient = findById(id);
        patientRepository.delete(patient);
    }
    private void validatePatient(Patient patient) {
        if (patient.getName() == null || patient.getName().isEmpty()) {
            throw new IllegalArgumentException("Patient name is required.");
        }
        if (patient.getCpf() == null || patient.getCpf().isEmpty()) {
            throw new IllegalArgumentException("Patient CPF is required.");
        }
        if (patient.getRg() == null || patient.getRg().isEmpty()) {
            throw new IllegalArgumentException("Patient RG is required.");
        }
        if (patient.getGender() == null || patient.getGender().isEmpty()) {
            throw new IllegalArgumentException("Patient gender is required.");
        }
    }

        public List<Patient> findAll() {
        return patientRepository.findAll();
    }
    
    public void updatePatient(Patient patient) {
        Patient existingPatient = findById(patient.getId());
        existingPatient.setName(patient.getName());
        existingPatient.setGender(patient.getGender());
        existingPatient.setAge(patient.getAge());
        existingPatient.setInsurance(patient.getInsurance());
        existingPatient.setHealthPlan(patient.getHealthPlan());
        existingPatient.setCpf(patient.getCpf());
        existingPatient.setRg(patient.getRg());
        existingPatient.setMotherName(patient.getMotherName());
        existingPatient.setFatherName(patient.getFatherName());
        existingPatient.setMedicalRecordPDF(patient.getMedicalRecordPDF());

        patientRepository.save(existingPatient);
    }
}