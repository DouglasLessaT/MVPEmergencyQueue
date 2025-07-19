package douglas.unisales.EmergencyQueueApi.model.domain;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "medical_care")
public class MedicalCare {

    @Id
    private UUID id = UUID.randomUUID();

    private String floor;
    private String roomNumber;
    private Boolean isICU;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "service_phase_id")
    private ServicePhase servicePhase;

    public MedicalCare() {
    }

    public MedicalCare(UUID id, String floor, String roomNumber, Boolean isICU,
            Patient patient, Status status, ServicePhase servicePhase) {
        this.id = id;
        this.floor = floor;
        this.roomNumber = roomNumber;
        this.isICU = isICU;
        this.patient = patient;
        this.status = status;
        this.servicePhase = servicePhase;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Boolean getIsICU() {
        return isICU;
    }

    public void setIsICU(Boolean isICU) {
        this.isICU = isICU;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ServicePhase getServicePhase() {
        return servicePhase;
    }

    public void setServicePhase(ServicePhase servicePhase) {
        this.servicePhase = servicePhase;
    }
    
}