package douglas.unisales.EmergencyQueueApi.repository.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import douglas.unisales.EmergencyQueueApi.model.domain.Ambulance;

@Repository
public interface AmbulanceRepository extends JpaRepository<Ambulance, UUID> {
    Ambulance findByLicensePlate(String licensePlate);

    Ambulance findByLicensePlateAndIdNot(String licensePlate, UUID id);

    Ambulance findByRenavam(String renavam);

    Ambulance findByRenavamAndIdNot(String renavam, UUID id);
}