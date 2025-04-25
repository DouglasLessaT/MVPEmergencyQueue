package com.DouglasLessa.emergencyBedApi.model.domain;

import java.util.List;
import java.util.UUID;

import com.DouglasLessa.emergencyBedApi.model.security.User;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "hospitals")
public class Hospital {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;
    private String phone;
    private String escalation;

    @Embedded
    @JoinColumn(name = "address_hospital_id")
    private Address address;

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

    @OneToMany(mappedBy = "Users")
    private List<User> users;
}