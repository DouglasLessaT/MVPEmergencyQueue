package com.DouglasLessa.emergencyBedApi.services.domain;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DouglasLessa.emergencyBedApi.model.domain.Hospital;
import com.DouglasLessa.emergencyBedApi.repository.domain.HospitalRepository;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    public Hospital save(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    public Hospital findById(UUID id) {
        return hospitalRepository.findById(id).orElseThrow(() -> new RuntimeException("Hospital not found with id: " + id));
    }

    public void delete(UUID id) {
        Hospital hospital = findById(id);
        hospitalRepository.delete(hospital);
    }
}