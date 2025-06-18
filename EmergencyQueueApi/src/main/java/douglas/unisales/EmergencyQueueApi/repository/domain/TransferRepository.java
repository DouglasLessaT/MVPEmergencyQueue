package douglas.unisales.EmergencyQueueApi.repository.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import douglas.unisales.EmergencyQueueApi.model.domain.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, UUID> {
    Transfer findByOriginHospitalIdAndDestinationHospitalIdAndPatientIdAndStatusIdAndIdNot(UUID originHospitalId, UUID destinationHospitalId, UUID patientId, UUID statusId, UUID id);
}