package douglas.unisales.EmergencyQueueApi.services.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import douglas.unisales.EmergencyQueueApi.model.domain.Status;
import douglas.unisales.EmergencyQueueApi.repository.domain.StatusRepository;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public Status save(Status status) {
        return statusRepository.save(status);
    }

    public Status findById(UUID id) {
        return statusRepository.findById(id).orElseThrow(() -> new RuntimeException("Status not found with id: " + id));
    }

    public List<Status> findAll() {
        return statusRepository.findAll();
    }

    public List<Status> findByEntityType(String entityType) {
        return statusRepository.findByEntityType(entityType);
    }

    public void delete(UUID id) {
        Status status = findById(id);
        statusRepository.delete(status);
    }

    // Métodos auxiliares para criar status específicos
    public Status createPatientStatus(String name, String code, String color, String description) {
        return createStatus(name, code, color, description, "PATIENT");
    }

    public Status createHospitalStatus(String name, String code, String color, String description) {
        return createStatus(name, code, color, description, "HOSPITAL");
    }

    public Status createQueueStatus(String name, String code, String color, String description) {
        return createStatus(name, code, color, description, "QUEUE");
    }

    public Status createMedicalCareStatus(String name, String code, String color, String description) {
        return createStatus(name, code, color, description, "MEDICAL_CARE");
    }

    public Status createCompanyStatus(String name, String code, String color, String description) {
        return createStatus(name, code, color, description, "COMPANY");
    }

    public Status createBedroomStatus(String name, String code, String color, String description) {
        return createStatus(name, code, color, description, "BEDROOM");
    }

    public Status createBedStatus(String name, String code, String color, String description) {
        return createStatus(name, code, color, description, "BED");
    }

    public Status createAmbulanceStatus(String name, String code, String color, String description) {
        return createStatus(name, code, color, description, "AMBULANCE");
    }

    public Status createTransferStatus(String name, String code, String color, String description) {
        return createStatus(name, code, color, description, "TRANSFER");
    }
    
    public Status createStatus(String name, String code, String color, String description, String entityType) {
        Status status = new Status();
        status.setName(name);
        status.setCode(code);
        status.setColor(color);
        status.setDescription(description);
        status.setEntityType(entityType);
        return save(status);
    }
}