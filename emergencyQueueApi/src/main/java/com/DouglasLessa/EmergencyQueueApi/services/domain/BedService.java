package com.DouglasLessa.EmergencyQueueApi.services.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DouglasLessa.EmergencyQueueApi.model.domain.Bed;
import com.DouglasLessa.EmergencyQueueApi.repository.domain.BedRepository;

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

    public List<Bed> findAll() {
        return bedRepository.findAll();
    }

    public void delete(UUID id) {
        Bed bed = findById(id);
        bedRepository.delete(bed);
    }

    public Bed update(Bed bed) {
        Bed existingBed = findById(bed.getId());
        
        existingBed.setCode(bed.getCode());
        existingBed.setFloor(bed.getFloor());
        existingBed.setType(bed.getType());
        existingBed.setStatus(bed.getStatus());
        existingBed.setPatient(bed.getPatient());
        existingBed.setBedroom(bed.getBedroom());
        existingBed.setEntryPacient(bed.getEntryPacient());
        existingBed.setExitPacient(bed.getExitPacient());

        return bedRepository.save(existingBed);
    }
}