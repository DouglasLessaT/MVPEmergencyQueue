package com.douglasLessa.emergencyBedApi.services.model;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglasLessa.emergencyBedApi.model.Transfer;
import com.douglasLessa.emergencyBedApi.repository.model.TransferRepository;

@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;

    public Transfer save(Transfer transfer) {
        return transferRepository.save(transfer);
    }

    public Transfer findById(UUID id) {
        return transferRepository.findById(id).orElseThrow(() -> new RuntimeException("Transfer not found with id: " + id));
    }

    public void delete(UUID id) {
        Transfer transfer = findById(id);
        transferRepository.delete(transfer);
    }
}