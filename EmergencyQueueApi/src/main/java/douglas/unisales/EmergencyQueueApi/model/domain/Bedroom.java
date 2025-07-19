package douglas.unisales.EmergencyQueueApi.model.domain;

import java.util.ArrayList;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "bedroom")
public class Bedroom {

    @Id
    private UUID id = UUID.randomUUID();

    private String code;
    private String floor;
    private String type;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @OneToMany(mappedBy = "bedroom")
    private ArrayList<Bed> beds;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    public Bedroom() {
    }

    public Bedroom(UUID id, String code, String floor, String type, Hospital hospital, ArrayList<Bed> beds, Status status) {
        this.id = id;
        this.code = code;
        this.floor = floor;
        this.type = type;
        this.hospital = hospital;
        this.beds = beds;
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

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public ArrayList<Bed> getBeds() {
        return beds;
    }

    public void setBeds(ArrayList<Bed> beds) {
        this.beds = beds;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    
}
