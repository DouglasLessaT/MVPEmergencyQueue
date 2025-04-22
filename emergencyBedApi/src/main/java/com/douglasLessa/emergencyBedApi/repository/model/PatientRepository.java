package com.douglasLessa.emergencyBedApi.repository.model;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglasLessa.emergencyBedApi.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID>{

    Patient findByName(String name);
    
    Patient findByNameAndIdNot(String name, UUID id);
    
    Patient findByCpf(String cpf);
    
    Patient findByCpfAndIdNot(String cpf, UUID id);
}  