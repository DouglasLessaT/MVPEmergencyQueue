package com.douglas.unisales.emegencyQueue.model.domain;

import java.util.ArrayList;
import java.util.UUID;

import com.douglas.unisales.emegencyQueue.model.security.User;

import jakarta.persistence.CascadeType;
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
    private ArrayList<Bedroom> rooms;

    @OneToMany(mappedBy = "hospital")
    private ArrayList<Ambulance> ambulances;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<User> users;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<QueuePatinent> queuePatinents;

}