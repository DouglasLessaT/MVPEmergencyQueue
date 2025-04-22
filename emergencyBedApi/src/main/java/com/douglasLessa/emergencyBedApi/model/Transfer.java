package com.douglasLessa.emergencyBedApi.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.UUID;

@Data
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
}