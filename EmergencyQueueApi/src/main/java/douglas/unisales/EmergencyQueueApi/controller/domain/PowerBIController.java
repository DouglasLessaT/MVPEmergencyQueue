package douglas.unisales.EmergencyQueueApi.controller.domain;

import douglas.unisales.EmergencyQueueApi.model.domain.*;
import douglas.unisales.EmergencyQueueApi.services.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/powerbi")
public class PowerBIController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private BedService bedService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private MedicalCareService medicalCareService;

    @Autowired
    private TransferService transferService;

    @Autowired
    private StatusService statusService;

    @GetMapping("/hospital-occupancy")
    public ResponseEntity<Map<String, Object>> getHospitalOccupancy() {
        List<Hospital> hospitals = hospitalService.findAll();
        Map<String, Object> occupancyData = new HashMap<>();
        
        for (Hospital hospital : hospitals) {
            Map<String, Object> hospitalData = new HashMap<>();
            hospitalData.put("totalCapacity", hospital.getTotalCapacity());
            hospitalData.put("activeCapacity", hospital.getActiveCapacity());
            hospitalData.put("occupancyRate", calculateOccupancyRate(hospital));
            hospitalData.put("availableBeds", hospital.getTotalCapacity() - hospital.getActiveCapacity());
            occupancyData.put(hospital.getName(), hospitalData);
        }
        
        return ResponseEntity.ok(occupancyData);
    }

    @GetMapping("/patient-status")
    public ResponseEntity<Map<String, Object>> getPatientStatusDistribution() {
        List<MedicalCare> medicalCares = medicalCareService.findAll();
        Map<String, Object> statusData = new HashMap<>();
        
        // Group by status
        Map<Status, Long> statusCount = medicalCares.stream()
            .collect(Collectors.groupingBy(MedicalCare::getStatus, Collectors.counting()));
        
        for (Map.Entry<Status, Long> entry : statusCount.entrySet()) {
            Map<String, Object> statusInfo = new HashMap<>();
            statusInfo.put("count", entry.getValue());
            statusInfo.put("color", entry.getKey().getColor());
            statusData.put(entry.getKey().getName(), statusInfo);
        }
        
        return ResponseEntity.ok(statusData);
    }

    @GetMapping("/service-phase-metrics")
    public ResponseEntity<Map<String, Object>> getServicePhaseMetrics() {
        List<MedicalCare> medicalCares = medicalCareService.findAll();
        Map<String, Object> phaseData = new HashMap<>();
        
        // Group by service phase
        Map<ServicePhase, Long> phaseCount = medicalCares.stream()
            .collect(Collectors.groupingBy(MedicalCare::getServicePhase, Collectors.counting()));
        
        for (Map.Entry<ServicePhase, Long> entry : phaseCount.entrySet()) {
            Map<String, Object> phaseInfo = new HashMap<>();
            phaseInfo.put("count", entry.getValue());
            phaseInfo.put("color", entry.getKey().getColor());
            phaseData.put(entry.getKey().getName(), phaseInfo);
        }
        
        return ResponseEntity.ok(phaseData);
    }

    @GetMapping("/transfer-statistics")
    public ResponseEntity<Map<String, Object>> getTransferStatistics() {
        List<Transfer> transfers = transferService.findAll();
        Map<String, Object> transferData = new HashMap<>();
        
        // Group by status
        Map<Status, Long> statusCount = transfers.stream()
            .collect(Collectors.groupingBy(Transfer::getStatus, Collectors.counting()));
        
        // Calculate average transfer time
        double avgTransferTime = transfers.stream()
            .filter(t -> t.getDate() != null)
            .mapToLong(t -> t.getDate().getTime())
            .average()
            .orElse(0.0);
        
        transferData.put("statusDistribution", statusCount);
        transferData.put("averageTransferTime", avgTransferTime);
        transferData.put("totalTransfers", transfers.size());
        
        return ResponseEntity.ok(transferData);
    }

    @GetMapping("/bed-utilization")
    public ResponseEntity<Map<String, Object>> getBedUtilization() {
        List<Bed> beds = bedService.findAll();
        Map<String, Object> utilizationData = new HashMap<>();
        
        // Calculate utilization metrics
        long totalBeds = beds.size();
        long occupiedBeds = beds.stream()
            .filter(bed -> bed.getPatient() != null)
            .count();
        
        utilizationData.put("totalBeds", totalBeds);
        utilizationData.put("occupiedBeds", occupiedBeds);
        utilizationData.put("availableBeds", totalBeds - occupiedBeds);
        utilizationData.put("utilizationRate", (double) occupiedBeds / totalBeds);
        
        // Group by type
        Map<String, Long> typeDistribution = beds.stream()
            .collect(Collectors.groupingBy(Bed::getType, Collectors.counting()));
        utilizationData.put("typeDistribution", typeDistribution);
        
        return ResponseEntity.ok(utilizationData);
    }

    @GetMapping("/hospital-performance")
    public ResponseEntity<Map<String, Object>> getHospitalPerformance() {
        List<Hospital> hospitals = hospitalService.findAll();
        Map<String, Object> performanceData = new HashMap<>();
        
        for (Hospital hospital : hospitals) {
            Map<String, Object> hospitalMetrics = new HashMap<>();
            
            // Calculate key performance indicators
            double occupancyRate = calculateOccupancyRate(hospital);
            int availableBeds = hospital.getTotalCapacity() - hospital.getActiveCapacity();
            
            // Get transfer statistics for this hospital
            List<Transfer> hospitalTransfers = transferService.findAll().stream()
                .filter(t -> t.getOriginHospital().getId().equals(hospital.getId()) || 
                           t.getDestinationHospital().getId().equals(hospital.getId()))
                .collect(Collectors.toList());
            
            hospitalMetrics.put("occupancyRate", occupancyRate);
            hospitalMetrics.put("availableBeds", availableBeds);
            hospitalMetrics.put("totalTransfers", hospitalTransfers.size());
            hospitalMetrics.put("totalRooms", hospital.getNumberOfRooms());
            hospitalMetrics.put("totalBeds", hospital.getNumberOfBeds());
            
            performanceData.put(hospital.getName(), hospitalMetrics);
        }
        
        return ResponseEntity.ok(performanceData);
    }

    private double calculateOccupancyRate(Hospital hospital) {
        if (hospital.getTotalCapacity() == 0) return 0.0;
        return (double) hospital.getActiveCapacity() / hospital.getTotalCapacity();
    }
} 