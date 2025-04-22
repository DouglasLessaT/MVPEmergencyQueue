package com.douglasLessa.emergencyBedApi.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {

    @Id
    private UUID id = UUID.randomUUID();
    private String street;
    private String country;
    private String postalCode;
    private String state;
    private String city;
}
