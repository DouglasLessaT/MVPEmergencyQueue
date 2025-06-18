package com.douglas.unisales.emegencyQueue.repository.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglas.unisales.emegencyQueue.model.domain.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID>{

    Patient findByName(String name);
    
    Patient findByNameAndIdNot(String name, UUID id);
    
    Patient findByCpf(String cpf);
    
    Patient findByCpfAndIdNot(String cpf, UUID id);
    
    List<Patient> findByStatusId(UUID statusId);
    List<Patient> findByStatusEntityType(String entityType);
}  