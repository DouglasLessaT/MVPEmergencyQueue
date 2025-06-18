package douglas.unisales.EmergencyQueueApi.repository.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import douglas.unisales.EmergencyQueueApi.model.domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {
}