package douglas.unisales.EmergencyQueueApi.model.domain;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;


@Entity
@Table(name = "Queue_patinent")
public class QueuePatinent {
    
    @Id
    private UUID id;

    private String nameQueue;
    private String description;

    private Double currentQueueSize;
    private Double averageWaitTime;
    private Date lastUpdate;

    @OneToMany(mappedBy = "queuePatinent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Patient> patients;
    
    @ManyToOne
    private Hospital hospital;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    public QueuePatinent() {
    }
    public QueuePatinent(UUID id, String nameQueue, String description, Double currentQueueSize,
            Double averageWaitTime, Date lastUpdate, List<Patient> patients, Hospital hospital, Status status) {
        this.id = id;
        this.nameQueue = nameQueue;
        this.description = description;
        this.currentQueueSize = currentQueueSize;
        this.averageWaitTime = averageWaitTime;
        this.lastUpdate = lastUpdate;
        this.patients = patients;
        this.hospital = hospital;
        this.status = status;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getNameQueue() {
        return nameQueue;
    }
    public void setNameQueue(String nameQueue) {
        this.nameQueue = nameQueue;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Double getCurrentQueueSize() {
        return currentQueueSize;
    }
    public void setCurrentQueueSize(Double currentQueueSize) {
        this.currentQueueSize = currentQueueSize;
    }
    public Double getAverageWaitTime() {
        return averageWaitTime;
    }
    public void setAverageWaitTime(Double averageWaitTime) {
        this.averageWaitTime = averageWaitTime;
    }
    public Date getLastUpdate() {
        return lastUpdate;
    }
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    public List<Patient> getPatients() {
        return patients;
    }
    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
    public Hospital getHospital() {
        return hospital;
    }
    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    
}