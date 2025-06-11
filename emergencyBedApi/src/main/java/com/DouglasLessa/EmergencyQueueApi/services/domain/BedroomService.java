package com.DouglasLessa.EmergencyQueueApi.services.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DouglasLessa.EmergencyQueueApi.model.domain.Bedroom;
import com.DouglasLessa.EmergencyQueueApi.repository.domain.BedroomRepository;

@Service
public class BedroomService {

    @Autowired
    private BedroomRepository bedroomRepository;

    public Bedroom save(Bedroom bedroom) {
        return bedroomRepository.save(bedroom);
    }

    public Bedroom findById(UUID id) {
        return bedroomRepository.findById(id).orElseThrow(() -> new RuntimeException("Bedroom not found with id: " + id));
    }

    public List<Bedroom> findAll() {
        return bedroomRepository.findAll();
    }

    public void delete(UUID id) {
        Bedroom bedroom = findById(id);
        bedroomRepository.delete(bedroom);
    }

    public void updateBedroom(Bedroom bedroom) {
        Bedroom existingBedroom = findById(bedroom.getId());
        existingBedroom.setCode(bedroom.getCode());
        existingBedroom.setType(bedroom.getType());
        existingBedroom.setFloor(bedroom.getFloor());
        existingBedroom.setBeds(bedroom.getBeds());
        existingBedroom.setHospital(bedroom.getHospital());
        existingBedroom.setStatus(bedroom.getStatus());

        bedroomRepository.save(existingBedroom);
    }
}