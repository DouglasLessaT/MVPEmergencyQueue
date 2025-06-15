package com.DouglasLessa.EmergencyQueueApi.model.domain;

public class QueuePatinentMedicalCare {
    
    private Double currentQueueSize;
    private Double averageWaitTime;
    private Date lastUpdate;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private List<Patient> patients;
}
