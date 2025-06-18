package com.douglas.unisales.emegencyQueue.model.domain;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Queue_patinent")
public class QueuePatinent {
    
    @Id
    private UUID id;

    private String nameQueue;
    private String description;

    private Double currentQueueSize;
    private Double averageWaitTime;
    private Date lastUpdate;

    @OneToMany(mappedBy = "queuePatinent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Patient> patients;
    
    @ManyToOne
    private Hospital hospital;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
}