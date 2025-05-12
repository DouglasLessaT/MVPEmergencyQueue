package com.DouglasLessa.emergencyBedApi.services.domain;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DouglasLessa.emergencyBedApi.model.domain.Bed;
import com.DouglasLessa.emergencyBedApi.repository.domain.BedRepository;

@Service
public class BedService {

    @Autowired
    private BedRepository bedRepository;

    public Bed save(Bed bed) {
        return bedRepository.save(bed);
    }

    public Bed findById(UUID id) {
        return bedRepository.findById(id).orElseThrow(() -> new RuntimeException("Bed not found with id: " + id));
    }

    public void delete(UUID id) {
        Bed bed = findById(id);
        bedRepository.delete(bed);
    }
    
}