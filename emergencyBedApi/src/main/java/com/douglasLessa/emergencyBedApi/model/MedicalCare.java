package com.douglasLessa.emergencyBedApi.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Data
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
}