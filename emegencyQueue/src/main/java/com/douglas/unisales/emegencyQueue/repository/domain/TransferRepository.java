package com.douglas.unisales.emegencyQueue.repository.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglas.unisales.emegencyQueue.model.domain.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, UUID> {
    Transfer findByOriginHospitalIdAndDestinationHospitalIdAndPatientIdAndStatusIdAndIdNot(UUID originHospitalId, UUID destinationHospitalId, UUID patientId, UUID statusId, UUID id);
}