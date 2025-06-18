package douglas.unisales.EmergencyQueueApi.repository.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import douglas.unisales.EmergencyQueueApi.model.domain.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, UUID> {
    Hospital findByName(String name);

    Hospital findByNameAndIdNot(String name, UUID id);

    List<Hospital> findByStatusId(UUID statusId);
    List<Hospital> findByStatusEntityType(String entityType);
}