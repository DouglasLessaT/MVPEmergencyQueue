package douglas.unisales.EmergencyQueueApi.repository.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import douglas.unisales.EmergencyQueueApi.model.domain.Seat;
import douglas.unisales.EmergencyQueueApi.model.domain.Hospital;

@Repository
public interface SeatRepository extends JpaRepository<Seat, UUID> {
    
    List<Seat> findByInfermariaId(UUID infermariaId);
    
    List<Seat> findByInfermariaIdAndOccupied(UUID infermariaId, boolean occupied);
    
    List<Seat> findByStatusId(UUID statusId);
    
    Seat findByCodeAndInfermariaId(String code, UUID infermariaId);
    
    Seat findByCodeAndInfermariaIdAndIdNot(String code, UUID infermariaId, UUID id);
    
    List<Seat> findByPatientId(UUID patientId);
    
    List<Seat> findByOccupied(boolean occupied);
} 