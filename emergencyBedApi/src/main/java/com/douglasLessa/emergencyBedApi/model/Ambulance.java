package com.douglasLessa.emergencyBedApi.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ambulances")
public class Ambulance {

    @Id
    private UUID id = UUID.randomUUID();

    private String licensePlate;
    private String model;
    private String type;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}