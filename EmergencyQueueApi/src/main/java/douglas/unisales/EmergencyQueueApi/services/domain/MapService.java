package douglas.unisales.EmergencyQueueApi.services.domain;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import douglas.unisales.EmergencyQueueApi.model.domain.Hospital;
import douglas.unisales.EmergencyQueueApi.model.domain.Status;
import douglas.unisales.EmergencyQueueApi.model.domain.QueuePatinent;
import douglas.unisales.EmergencyQueueApi.model.domain.Seat;
import douglas.unisales.EmergencyQueueApi.model.domain.MedicalCare;
import douglas.unisales.EmergencyQueueApi.repository.domain.HospitalRepository;
import douglas.unisales.EmergencyQueueApi.repository.domain.StatusRepository;
import douglas.unisales.EmergencyQueueApi.repository.domain.QueuePatinentRepository;
import douglas.unisales.EmergencyQueueApi.repository.domain.SeatRepository;
import douglas.unisales.EmergencyQueueApi.repository.domain.MedicalCareRepository;

@Service
public class MapService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private QueuePatinentRepository queuePatinentRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private MedicalCareRepository medicalCareRepository;

    public List<Map<String, Object>> getHospitalsWithMapData() {
        List<Hospital> hospitals = hospitalRepository.findAll();
        
        return hospitals.stream()
            .filter(hospital -> hospital.getAddress() != null && 
                               hospital.getAddress().getLatitude() != null && 
                               hospital.getAddress().getLongitude() != null)
            .map(hospital -> {
                Map<String, Object> hospitalData = new HashMap<>();
                
                // Dados básicos do hospital
                hospitalData.put("id", hospital.getId());
                hospitalData.put("name", hospital.getName());
                hospitalData.put("phone", hospital.getPhone());
                hospitalData.put("escalation", hospital.getEscalation());
                
                // Dados de localização
                hospitalData.put("latitude", hospital.getAddress().getLatitude());
                hospitalData.put("longitude", hospital.getAddress().getLongitude());
                hospitalData.put("address", buildFullAddress(hospital.getAddress()));
                
                // Dados de capacidade
                hospitalData.put("totalCapacity", hospital.getTotalCapacity());
                hospitalData.put("activeCapacity", hospital.getActiveCapacity());
                hospitalData.put("numberOfBeds", hospital.getNumberOfBeds());
                hospitalData.put("numberOfRooms", hospital.getNumberOfRooms());
                
                // Status do hospital
                if (hospital.getStatus() != null) {
                    hospitalData.put("status", hospital.getStatus().getName());
                    hospitalData.put("statusCode", hospital.getStatus().getCode());
                    hospitalData.put("statusColor", hospital.getStatus().getColor());
                }
                
                // Dados da fila de emergência
                List<QueuePatinent> emergencyQueues = queuePatinentRepository.findByHospital(hospital);
                if (!emergencyQueues.isEmpty()) {
                    QueuePatinent emergencyQueue = emergencyQueues.get(0); // Assumindo que há uma fila principal de emergência
                    hospitalData.put("queueSize", emergencyQueue.getCurrentQueueSize());
                    hospitalData.put("averageWaitTime", emergencyQueue.getAverageWaitTime());
                    hospitalData.put("queueStatus", emergencyQueue.getStatus() != null ? emergencyQueue.getStatus().getName() : null);
                }
                
                // Dados de leitos ocupados (simplificado)
                List<Seat> occupiedSeats = seatRepository.findByOccupied(true);
                hospitalData.put("occupiedSeats", occupiedSeats.size());
                
                // Dados de cuidados médicos ativos (simplificado)
                List<MedicalCare> activeMedicalCares = medicalCareRepository.findByStatusIdIsNotNull();
                hospitalData.put("activeMedicalCares", activeMedicalCares.size());
                
                return hospitalData;
            })
            .collect(Collectors.toList());
    }

    public Map<String, Object> getHospitalDetailsById(String hospitalId) {
        Hospital hospital = hospitalRepository.findById(java.util.UUID.fromString(hospitalId))
            .orElseThrow(() -> new RuntimeException("Hospital not found"));
        
        Map<String, Object> details = new HashMap<>();
        
        // Dados básicos
        details.put("id", hospital.getId());
        details.put("name", hospital.getName());
        details.put("phone", hospital.getPhone());
        details.put("escalation", hospital.getEscalation());
        details.put("address", buildFullAddress(hospital.getAddress()));
        details.put("latitude", hospital.getAddress().getLatitude());
        details.put("longitude", hospital.getAddress().getLongitude());
        
        // Capacidade
        details.put("totalCapacity", hospital.getTotalCapacity());
        details.put("activeCapacity", hospital.getActiveCapacity());
        details.put("availableCapacity", hospital.getTotalCapacity() - hospital.getActiveCapacity());
        details.put("occupancyRate", calculateOccupancyRate(hospital));
        
        // Status
        if (hospital.getStatus() != null) {
            details.put("status", hospital.getStatus().getName());
            details.put("statusCode", hospital.getStatus().getCode());
            details.put("statusColor", hospital.getStatus().getColor());
            details.put("statusDescription", hospital.getStatus().getDescription());
        }
        
        // Filas
        List<QueuePatinent> queues = queuePatinentRepository.findByHospital(hospital);
        details.put("queues", queues.stream().map(queue -> {
            Map<String, Object> queueData = new HashMap<>();
            queueData.put("name", queue.getNameQueue());
            queueData.put("description", queue.getDescription());
            queueData.put("currentSize", queue.getCurrentQueueSize());
            queueData.put("averageWaitTime", queue.getAverageWaitTime());
            queueData.put("lastUpdate", queue.getLastUpdate());
            if (queue.getStatus() != null) {
                queueData.put("status", queue.getStatus().getName());
                queueData.put("statusColor", queue.getStatus().getColor());
            }
            return queueData;
        }).collect(Collectors.toList()));
        
        // Leitos e quartos
        details.put("numberOfBeds", hospital.getNumberOfBeds());
        details.put("numberOfRooms", hospital.getNumberOfRooms());
        details.put("numberOfBuildings", hospital.getNumberOfBuildings());
        details.put("numberOfFloors", hospital.getNumberOfFloors());
        
        return details;
    }

    public List<Map<String, Object>> getHospitalsByStatus(String statusCode) {
        Status status = statusRepository.findByCode(statusCode)
            .orElseThrow(() -> new RuntimeException("Status not found"));
        
        List<Hospital> hospitals = hospitalRepository.findByStatus(status);
        
        return hospitals.stream()
            .filter(hospital -> hospital.getAddress() != null && 
                               hospital.getAddress().getLatitude() != null && 
                               hospital.getAddress().getLongitude() != null)
            .map(hospital -> {
                Map<String, Object> hospitalData = new HashMap<>();
                hospitalData.put("id", hospital.getId());
                hospitalData.put("name", hospital.getName());
                hospitalData.put("latitude", hospital.getAddress().getLatitude());
                hospitalData.put("longitude", hospital.getAddress().getLongitude());
                hospitalData.put("status", hospital.getStatus().getName());
                hospitalData.put("statusColor", hospital.getStatus().getColor());
                
                // Dados da fila
                List<QueuePatinent> queues = queuePatinentRepository.findByHospital(hospital);
                if (!queues.isEmpty()) {
                    hospitalData.put("queueSize", queues.get(0).getCurrentQueueSize());
                    hospitalData.put("averageWaitTime", queues.get(0).getAverageWaitTime());
                }
                
                return hospitalData;
            })
            .collect(Collectors.toList());
    }

    private String buildFullAddress(douglas.unisales.EmergencyQueueApi.model.domain.Address address) {
        if (address == null) return "";
        
        StringBuilder fullAddress = new StringBuilder();
        if (address.getStreet() != null) fullAddress.append(address.getStreet());
        if (address.getNumber() != null) fullAddress.append(", ").append(address.getNumber());
        if (address.getNeighborhood() != null) fullAddress.append(", ").append(address.getNeighborhood());
        if (address.getCity() != null) fullAddress.append(", ").append(address.getCity());
        if (address.getState() != null) fullAddress.append(" - ").append(address.getState());
        if (address.getZipCode() != null) fullAddress.append(", ").append(address.getZipCode());
        
        return fullAddress.toString();
    }

    private double calculateOccupancyRate(Hospital hospital) {
        if (hospital.getTotalCapacity() == null || hospital.getTotalCapacity() == 0) {
            return 0.0;
        }
        return (double) hospital.getActiveCapacity() / hospital.getTotalCapacity() * 100;
    }
} 