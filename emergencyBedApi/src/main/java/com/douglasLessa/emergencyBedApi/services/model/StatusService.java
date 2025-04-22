package com.douglasLessa.emergencyBedApi.services.model;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglasLessa.emergencyBedApi.model.Status;
import com.douglasLessa.emergencyBedApi.repository.model.StatusRepository;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public Status save(Status status) {
        return statusRepository.save(status);
    }

    public Status findById(UUID id) {
        return statusRepository.findById(id).orElseThrow(() -> new RuntimeException("Status not found with id: " + id));
    }

    public void delete(UUID id) {
        Status status = findById(id);
        statusRepository.delete(status);
    }
}