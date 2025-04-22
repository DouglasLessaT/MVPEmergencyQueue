package com.douglasLessa.emergencyBedApi.repository.model;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglasLessa.emergencyBedApi.model.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, UUID> {
    Transfer findByOriginHospitalIdAndDestinationHospitalIdAndPatientId(UUID originHospitalId, UUID destinationHospitalId, UUID patientId);

    Transfer findByOriginHospitalIdAndDestinationHospitalIdAndPatientIdAndIdNot(UUID originHospitalId, UUID destinationHospitalId, UUID patientId, UUID id);
    Transfer findByOriginHospitalIdAndPatientId(UUID originHospitalId, UUID patientId);
    Transfer findByOriginHospitalIdAndPatientIdAndIdNot(UUID originHospitalId, UUID patientId, UUID id);
    Transfer findByDestinationHospitalIdAndPatientId(UUID destinationHospitalId, UUID patientId);
    Transfer findByDestinationHospitalIdAndPatientIdAndIdNot(UUID destinationHospitalId, UUID patientId, UUID id);
    Transfer findByOriginHospitalIdAndDestinationHospitalIdAndPatientIdAndStatus(UUID originHospitalId, UUID destinationHospitalId, UUID patientId, String status);
    Transfer findByOriginHospitalIdAndDestinationHospitalIdAndPatientIdAndStatusAndIdNot(UUID originHospitalId, UUID destinationHospitalId, UUID patientId, String status, UUID id);
}