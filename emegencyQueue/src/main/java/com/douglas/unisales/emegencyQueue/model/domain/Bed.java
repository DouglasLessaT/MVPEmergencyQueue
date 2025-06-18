package com.douglas.unisales.emegencyQueue.model.domain;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "bedroom_id")
    private Bedroom bedroom;

    @ManyToOne
    @JoinColumn(name = "infermaria_id")
    private Infermaria infermaria;

    private Date entryPacient;
    private Date exitPacient;

    private boolean occupied;

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
