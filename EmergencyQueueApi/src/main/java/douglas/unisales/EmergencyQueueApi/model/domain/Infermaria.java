package douglas.unisales.EmergencyQueueApi.model.domain;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "infermaria")
public class Infermaria {

    @Id
    private UUID id = UUID.randomUUID();
    
    private String name;
    private String code;
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
    
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
    
    // Lista de pacientes na enfermaria
    @ManyToMany
    @JoinTable(
        name = "infermaria_patients",
        joinColumns = @JoinColumn(name = "infermaria_id"),
        inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    private List<Patient> patients;
    
    // Lista de todos os leitos da enfermaria
    @OneToMany(mappedBy = "infermaria")
    private List<Bed> beds;
    
    // Lista de leitos ocupados (calculado)
    @OneToMany(mappedBy = "infermaria")
    private List<Bed> occupiedBeds;
    
    // Lista de assentos (seats) da enfermaria
    @OneToMany(mappedBy = "infermaria")
    private List<Seat> seats;
    
    // Lista de assentos ocupados (calculado)
    @OneToMany(mappedBy = "infermaria")
    private List<Seat> occupiedSeats;

    public Infermaria() {
    }
    public Infermaria(UUID id, String name, String code, String description, Hospital hospital, Status status,
                     List<Patient> patients, List<Bed> beds, List<Bed> occupiedBeds, List<Seat> seats, List<Seat> occupiedSeats) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.hospital = hospital;
        this.status = status;
        this.patients = patients;
        this.beds = beds;
        this.occupiedBeds = occupiedBeds;
        this.seats = seats;
        this.occupiedSeats = occupiedSeats;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public List<Patient> getPatients() {
        return patients;
    }
    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
    public List<Bed> getBeds() {
        return beds;
    }
    public void setBeds(List<Bed> beds) {
        this.beds = beds;
    }
    public List<Bed> getOccupiedBeds() {
        return occupiedBeds;
    }
    public void setOccupiedBeds(List<Bed> occupiedBeds) {
        this.occupiedBeds = occupiedBeds;
    }
    public List<Seat> getSeats() {
        return seats;
    }
    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
    public List<Seat> getOccupiedSeats() {
        return occupiedSeats;
    }
    public void setOccupiedSeats(List<Seat> occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
    }

    
} 