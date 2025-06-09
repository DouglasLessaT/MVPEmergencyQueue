package com.DouglasLessa.emergencyBedApi.services.domain;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DouglasLessa.emergencyBedApi.model.domain.Transfer;
import com.DouglasLessa.emergencyBedApi.repository.domain.TransferRepository;

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