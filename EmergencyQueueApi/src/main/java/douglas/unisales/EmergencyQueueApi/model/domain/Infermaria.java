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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
} 