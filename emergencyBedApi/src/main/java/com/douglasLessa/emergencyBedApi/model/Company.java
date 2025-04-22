package com.douglasLessa.emergencyBedApi.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "companies")
public class Company {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;
    private String cnpj;
}