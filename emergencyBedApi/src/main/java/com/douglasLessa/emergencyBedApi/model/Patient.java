package com.douglasLessa.emergencyBedApi.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;
    private String gender;
    private Integer age;
    private String insurance;
    private String healthPlan;
    private String cpf;
    private String rg;
    private String motherName;
    private String fatherName;
    private String medicalRecordPDF;
}