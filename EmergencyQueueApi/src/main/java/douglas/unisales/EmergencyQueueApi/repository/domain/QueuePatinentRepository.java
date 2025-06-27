package douglas.unisales.EmergencyQueueApi.repository.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import douglas.unisales.EmergencyQueueApi.model.domain.QueuePatinent;
import douglas.unisales.EmergencyQueueApi.model.domain.Hospital;

public interface QueuePatinentRepository extends JpaRepository<QueuePatinent, UUID> {

    List<QueuePatinent> findByStatusId(UUID statusId);
    List<QueuePatinent> findByStatusEntityType(String entityType);
    List<QueuePatinent> findByHospital(Hospital hospital);

}
