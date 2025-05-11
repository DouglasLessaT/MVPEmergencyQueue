package com.DouglasLessa.emergencyBedApi.services.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DouglasLessa.emergencyBedApi.model.domain.Status;
import com.DouglasLessa.emergencyBedApi.repository.domain.StatusRepository;


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

    public List<Status> findAll() {
        return statusRepository.findAll();
    }

    public void delete(UUID id) {
        Status status = findById(id);
        statusRepository.delete(status);
    }
}