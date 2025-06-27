package  douglas.unisales.EmergencyQueueApi.repository.domain;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import douglas.unisales.EmergencyQueueApi.model.domain.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, UUID> {
    Status findByDescription(String description);

    Status findByDescriptionAndIdNot(String description, UUID id);

    Status findByName(String name);

    Status findByNameAndIdNot(String name, UUID id);

    List<Status> findByEntityType(String entityType);
    
    Optional<Status> findByCode(String code);
}