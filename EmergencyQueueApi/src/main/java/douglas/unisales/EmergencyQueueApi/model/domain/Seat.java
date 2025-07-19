package douglas.unisales.EmergencyQueueApi.model.domain;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "seat")
public class Seat {

    @Id
    private UUID id = UUID.randomUUID();
    
    private String code;
    private String description;
    private boolean occupied;
    
    @ManyToOne
    @JoinColumn(name = "infermaria_id")
    private Infermaria infermaria;
    
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public Seat(){
    }
    public Seat(UUID id, String code, String description, boolean occupied, Infermaria infermaria, Patient patient, Status status) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.occupied = occupied;
        this.infermaria = infermaria;
        this.patient = patient;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Infermaria getInfermaria() {
        return infermaria;
    }

    public void setInfermaria(Infermaria infermaria) {
        this.infermaria = infermaria;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    
} 