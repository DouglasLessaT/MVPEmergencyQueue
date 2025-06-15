package com.douglas.unisales.emegencyQueue.model.domain;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Queue_patinent_medical_care")
public class QueuePatinentMedicalCare {
    
    @Id
    private UUID id;

    
    private Double currentQueueSize;
    private Double averageWaitTime;
    private Date lastUpdate;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private List<Patient> patients;
}
