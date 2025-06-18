package douglas.unisales.EmergencyQueueApi.services.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import douglas.unisales.EmergencyQueueApi.model.domain.ServicePhase;
import douglas.unisales.EmergencyQueueApi.repository.domain.ServicePhaseRepository;

@Service
public class ServicePhaseService {

    @Autowired
    private ServicePhaseRepository servicePhaseRepository;

    public ServicePhase save(ServicePhase servicePhase) {
        return servicePhaseRepository.save(servicePhase);
    }

    public ServicePhase findById(UUID id) {
        return servicePhaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ServicePhase not found with id: " + id));
    }

    public List<ServicePhase> findAll() {
        return servicePhaseRepository.findAll();
    }

    public ServicePhase update(UUID id, ServicePhase updatedServicePhase) {
        ServicePhase existingServicePhase = findById(id);
        existingServicePhase.setName(updatedServicePhase.getName());
        existingServicePhase.setCode(updatedServicePhase.getCode());
        existingServicePhase.setColor(updatedServicePhase.getColor());
        existingServicePhase.setDescription(updatedServicePhase.getDescription());
        return servicePhaseRepository.save(existingServicePhase);
    }

    public void delete(UUID id) {
        ServicePhase servicePhase = findById(id);
        servicePhaseRepository.delete(servicePhase);
    }
}