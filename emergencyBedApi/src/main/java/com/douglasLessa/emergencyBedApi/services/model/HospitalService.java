package com.douglasLessa.emergencyBedApi.services.model;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglasLessa.emergencyBedApi.model.Hospital;
import com.douglasLessa.emergencyBedApi.repository.model.HospitalRepository;

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