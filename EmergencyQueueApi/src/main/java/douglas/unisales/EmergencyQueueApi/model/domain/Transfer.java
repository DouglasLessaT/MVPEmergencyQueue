package douglas.unisales.EmergencyQueueApi.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Embedded;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "transfers")
public class Transfer {

    @Id
    private UUID id = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "origin_hospital_id")
    private Hospital originHospital;

    @ManyToOne
    @JoinColumn(name = "destination_hospital_id")
    private Hospital destinationHospital;

    @ManyToOne
    @JoinColumn(name = "ambulance_id")
    private Ambulance ambulance;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @Embedded
    @JoinColumn(name = "address_destination_hospital_id")
    private Address destinationHospitalAddress;

    @ManyToOne
    @JoinColumn(name = "addressorigin_hospital_id")
    private Hospital originHospitalAddress;

    public Transfer() {
    }
    public Transfer(UUID id, Patient patient, Hospital originHospital, Hospital destinationHospital,
                    Ambulance ambulance, Date date, Status status, Address destinationHospitalAddress,
                    Hospital originHospitalAddress) {
        this.id = id;
        this.patient = patient;
        this.originHospital = originHospital;
        this.destinationHospital = destinationHospital;
        this.ambulance = ambulance;
        this.date = date;
        this.status = status;
        this.destinationHospitalAddress = destinationHospitalAddress;
        this.originHospitalAddress = originHospitalAddress;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public Hospital getOriginHospital() {
        return originHospital;
    }
    public void setOriginHospital(Hospital originHospital) {
        this.originHospital = originHospital;
    }
    public Hospital getDestinationHospital() {
        return destinationHospital;
    }
    public void setDestinationHospital(Hospital destinationHospital) {
        this.destinationHospital = destinationHospital;
    }
    public Ambulance getAmbulance() {
        return ambulance;
    }
    public void setAmbulance(Ambulance ambulance) {
        this.ambulance = ambulance;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Address getDestinationHospitalAddress() {
        return destinationHospitalAddress;
    }
    public void setDestinationHospitalAddress(Address destinationHospitalAddress) {
        this.destinationHospitalAddress = destinationHospitalAddress;
    }
    public Hospital getOriginHospitalAddress() {
        return originHospitalAddress;
    }
    public void setOriginHospitalAddress(Hospital originHospitalAddress) {
        this.originHospitalAddress = originHospitalAddress;
    }
    
}