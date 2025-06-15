package com.DouglasLessa.EmergencyQueueApi.services.domain;

public class QueuePatinentMedicalCareService {
    package com.DouglasLessa.EmergencyQueueApi.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DouglasLessa.EmergencyQueueApi.model.domain.QueuePatinentMedicalCare;
import com.DouglasLessa.EmergencyQueueApi.repository.QueuePatinentMedicalCareRepository;

@Service
public class QueuePatinentMedicalCareService {

    @Autowired
    private QueuePatinentMedicalCareRepository repository;

    public List<QueuePatinentMedicalCare> findAll() {
        return repository.findAll();
    }

    public QueuePatinentMedicalCare findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public QueuePatinentMedicalCare save(QueuePatinentMedicalCare queue) {
        return repository.save(queue);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
}
