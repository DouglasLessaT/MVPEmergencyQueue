package com.douglasLessa.emergencyBedApi.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "hospitals")
public class Hospital {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;
    private String phone;
    private String escalation;
    private String street;
    private String country;
    private String postalCode;
    private String state;
    private String city;

    private Integer totalCapacity;
    private Integer activeCapacity;
    private Integer numberOfBeds;
    private Integer numberOfRooms;
    private Integer numberOfBuildings;
    private Integer numberOfFloors;

    @OneToMany(mappedBy = "hospital")
    private List<Bedroom> rooms;

    @OneToMany(mappedBy = "hospital")
    private List<Ambulance> ambulances;
}