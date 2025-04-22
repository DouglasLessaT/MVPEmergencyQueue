package com.douglasLessa.emergencyBedApi.services.model;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglasLessa.emergencyBedApi.model.Bedroom;
import com.douglasLessa.emergencyBedApi.repository.model.BedroomRepository;

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

    public void delete(UUID id) {
        Bedroom bedroom = findById(id);
        bedroomRepository.delete(bedroom);
    }
}