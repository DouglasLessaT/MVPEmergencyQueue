package douglas.unisales.EmergencyQueueApi.services.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import douglas.unisales.EmergencyQueueApi.model.domain.Patient;
import douglas.unisales.EmergencyQueueApi.model.domain.QueuePatinent;
import douglas.unisales.EmergencyQueueApi.repository.domain.PatientRepository;
import douglas.unisales.EmergencyQueueApi.repository.domain.QueuePatinentRepository;

@Service
public class QueuePatinentService {

    @Autowired
    private QueuePatinentRepository queueRepository;

    @Autowired
    private PatientRepository patientRepository;

    public List<QueuePatinent> findAll() {
        return queueRepository.findAll();
    }

    public QueuePatinent findById(UUID id) {
        return queueRepository.findById(id).orElse(null);
    }

    public QueuePatinent save(QueuePatinent queue) {
        return queueRepository.save(queue);
    }

    public void delete(UUID id) {
        queueRepository.deleteById(id);
    }

    public void addPatientToQueue(UUID patientId, UUID queueId) {
        Patient patient = patientRepository.findById(patientId)
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        if (patient.getQueuePatinent() != null) {
            throw new RuntimeException("O paciente já está associado a uma fila.");
        }

        QueuePatinent queue = queueRepository.findById(queueId)
            .orElseThrow(() -> new RuntimeException("Fila não encontrada"));

        patient.setQueuePatinent(queue);
        patientRepository.save(patient);
    }
}