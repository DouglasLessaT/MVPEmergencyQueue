package com.DouglasLessa.EmergencyQueueApi.services.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DouglasLessa.EmergencyQueueApi.model.domain.MedicalCare;
import com.DouglasLessa.EmergencyQueueApi.model.domain.Patient;
import com.DouglasLessa.EmergencyQueueApi.model.domain.Status;
import com.DouglasLessa.EmergencyQueueApi.repository.domain.MedicalCareRepository;

@Service
public class MedicalCareService {

    @Autowired
    private MedicalCareRepository medicalCareRepository;

    @Autowired
    private PatientService patientService;

    public MedicalCare save(MedicalCare medicalCare) {
        return medicalCareRepository.save(medicalCare);
    }

    public MedicalCare findById(UUID id) {
        return medicalCareRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MedicalCare not found with id: " + id));
    }

    public List<MedicalCare> findAll() {
        return medicalCareRepository.findAll();
    }

    public void delete(UUID id) {
        MedicalCare medicalCare = findById(id);
        medicalCareRepository.delete(medicalCare);
    }

    public MedicalCare update(MedicalCare medicalCare) {
        MedicalCare existingMedicalCare = findById(medicalCare.getId());

        if (medicalCare.getPatient() == null || medicalCare.getPatient().getId() == null) {
            throw new IllegalArgumentException("A valid Patient is required for MedicalCare.");
        }
        Patient patient = patientService.findById(medicalCare.getPatient().getId());
        if (patient == null) {
            throw new RuntimeException("Patient not found with id: " + medicalCare.getPatient().getId());
        }

        existingMedicalCare.setFloor(medicalCare.getFloor());
        existingMedicalCare.setRoomNumber(medicalCare.getRoomNumber());
        existingMedicalCare.setIsICU(medicalCare.getIsICU());
        existingMedicalCare.setPatient(patient);
        existingMedicalCare.setStatus(medicalCare.getStatus());
        existingMedicalCare.setServicePhase(medicalCare.getServicePhase());

        return medicalCareRepository.save(existingMedicalCare);
    }
}