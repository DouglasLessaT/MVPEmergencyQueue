package douglas.unisales.EmergencyQueueApi.model.domain;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "bed")
public class Bed {
    @Id
    private UUID id = UUID.randomUUID();

    private String code;
    private String floor;
    private String type;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "bedroom_id")
    private Bedroom bedroom;

    @ManyToOne
    @JoinColumn(name = "infermaria_id")
    private Infermaria infermaria;

    private Date entryPacient;
    private Date exitPacient;

    private boolean occupied;

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Bed() {
    }

    public Bed(UUID id, String code, String floor, String type, Status status, Patient patient, Bedroom bedroom, Infermaria infermaria, Date entryPacient, Date exitPacient) {
        this.id = id;
        this.code = code;
        this.floor = floor;
        this.type = type;
        this.status = status;
        this.patient = patient;
        this.bedroom = bedroom;
        this.infermaria = infermaria;
        this.entryPacient = entryPacient;
        this.exitPacient = exitPacient;
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

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Bedroom getBedroom() {
        return bedroom;
    }

    public void setBedroom(Bedroom bedroom) {
        this.bedroom = bedroom;
    }

    public Infermaria getInfermaria() {
        return infermaria;
    }

    public void setInfermaria(Infermaria infermaria) {
        this.infermaria = infermaria;
    }

    public Date getEntryPacient() {
        return entryPacient;
    }

    public void setEntryPacient(Date entryPacient) {
        this.entryPacient = entryPacient;
    }

    public Date getExitPacient() {
        return exitPacient;
    }

    public void setExitPacient(Date exitPacient) {
        this.exitPacient = exitPacient;
    }

    

}
