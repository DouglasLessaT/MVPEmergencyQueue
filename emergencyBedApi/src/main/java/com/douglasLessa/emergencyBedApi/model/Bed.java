package com.douglasLessa.emergencyBedApi.model;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bed")
public class Bed {
    @Id
    private UUID id = UUID.randomUUID();

    private String code;
    private String floor;
    private String type;
    
    @OneToMany
    private Status status;

    @OneToMany
    private Bedroom bedroom;

    private Date entryPacient;
    private Date exitPacient;

}
