package com.DouglasLessa.EmergencyQueueApi.repository.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DouglasLessa.EmergencyQueueApi.model.domain.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID>{

    Patient findByName(String name);
    
    Patient findByNameAndIdNot(String name, UUID id);
    
    Patient findByCpf(String cpf);
    
    Patient findByCpfAndIdNot(String cpf, UUID id);
}  