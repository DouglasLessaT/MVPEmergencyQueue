package com.DouglasLessa.EmergencyQueueApi.services.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DouglasLessa.EmergencyQueueApi.model.domain.Transfer;
import com.DouglasLessa.EmergencyQueueApi.repository.domain.TransferRepository;

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

    public List<Transfer> findAll() {
        return transferRepository.findAll();
    }

    public void delete(UUID id) {
        Transfer transfer = findById(id);
        transferRepository.delete(transfer);
    }

    public Transfer update(Transfer transfer) {
        Transfer existingTransfer = findById(transfer.getId());
        
        existingTransfer.setPatient(transfer.getPatient());
        existingTransfer.setOriginHospital(transfer.getOriginHospital());
        existingTransfer.setDestinationHospital(transfer.getDestinationHospital());
        existingTransfer.setAmbulance(transfer.getAmbulance());
        existingTransfer.setDate(transfer.getDate());
        existingTransfer.setStatus(transfer.getStatus());
        existingTransfer.setDestinationHospitalAddress(transfer.getDestinationHospitalAddress());
        existingTransfer.setOriginHospitalAddress(transfer.getOriginHospitalAddress());

        return transferRepository.save(existingTransfer);
    }
}