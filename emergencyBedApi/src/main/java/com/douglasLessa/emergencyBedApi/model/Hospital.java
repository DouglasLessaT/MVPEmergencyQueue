package com.douglasLessa.emergencyBedApi.model;

import java.io.ObjectInputFilter.Status;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Hospital")
public class Hospital {

    @Id
    private UUID id = UUID.randomUUID();

    private String nome;
    private String telefone;
    private String escalonamento;
    @ManyToOne
    private Status status;

    private Integer capacidadeTotal;
    private Integer capacidadeAtiva;
    private Integer numeroLeitos;
    private Integer numeroQuartos;
    private Integer numeroPredios;
    private Integer numeroAndares;

    private String street;
    private String country;
    private String postalCode;
    private String state;
    private String city;
}